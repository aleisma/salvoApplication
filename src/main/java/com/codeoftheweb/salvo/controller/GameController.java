package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/api")
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    PlayerRepository playerRepository;

    //===================== /GAME VIEW/GP =============================================================================/
    @RequestMapping("/game_view/{gp}")
    public ResponseEntity<Map<String, Object>> findGamePlayer(@PathVariable long gp, Authentication authentication) {

        GamePlayer gamePlayer = gamePlayerRepository.findById(gp).orElse(null);

        if(gamePlayer == null ){
            return new  ResponseEntity<>(makeMap("ERROR!","EL GAMEPLAYER NO EXISTE"),HttpStatus.UNAUTHORIZED);
        }

        if(isGuest(authentication)){
            return new  ResponseEntity<>(makeMap("ERROR!!","NO PUEDE ACCEDER A ESTA VISTA"),HttpStatus.UNAUTHORIZED);
        }
        Player  player  = playerRepository.findByUserName(authentication.getName());

        Game game = gamePlayer.getGames();

        if(player == null){
            return new  ResponseEntity<>(makeMap("ERROR!","PLAYER NO EXISTE"),HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer == null ){
            return  new ResponseEntity<>(makeMap("ERROR!"," GP NO EXISTE"), HttpStatus.UNAUTHORIZED);
        }

    /*   if( gamePlayer.getPlayer().gamePlayers.size() == 1){
            return new ResponseEntity<>(makeMap(), HttpStatus.OK);
        } */

        if(gamePlayer.getPlayer().getId() !=  player.getId()){
            return new  ResponseEntity<>(makeMap("ERROR!!","NO PUEDE ACCEDER A ESTA VISTA"),HttpStatus.CONFLICT);
        }

      if (gamePlayer.getPlayer() == player) {

          return new ResponseEntity<>(gameViewDTO(gamePlayer), HttpStatus.OK);
        }

        return new  ResponseEntity<>(makeMap("ERROR!!","NO PUEDE ACCEDER A ESTA VISTA"),HttpStatus.UNAUTHORIZED);
    }

    //====================== DTO GAMEVIEW =============================================================================/
    private Map<String, Object> gameViewDTO(GamePlayer gp) {
        Map<String, Object> dto = new HashMap<>();

        dto.put("id", gp.getGame().getId());
        dto.put("created", gp.getGame().getCreationDate());
        dto.put("gamePlayers", makeGamePlayer(gp.getGame().getGamePlayers()));
        dto.put("ships", makeShips(gp.getShips()));
        dto.put("salvoes", makeSalvos(gp.getGame().getGamePlayers()));//ya le paso el stream completed de salvos del game*/
        dto.put("hits", hitsDTO(gp));
        dto.put("gameState", GameState.PLACESHIPS);
        return dto;
    }

    //====================== MAKE GP ==================================================================================/
    private List<Map> makeGamePlayer(Set<GamePlayer> set) {

        return set.stream().map(gamePlayer -> gamePlayer.makeGamePlayerDTO()).collect((Collectors.toList()));
    }

    //====================== MAKE SHIPS ===============================================================================/
    private List<Map> makeShips(Set<Ship> ships) {

        return ships.stream().map(ship -> ship.getShipDTO()).collect((Collectors.toList()));
    }

    //====================== MAKE SALVOS ==============================================================================/
    private List<Map> makeSalvos(Set<GamePlayer> gp) {

        Stream<Salvo> ss = gp.stream().flatMap(g -> g.getSalvoes() .stream());

        return ss.map(salvo -> salvo.makeSalvoDTO()).collect((Collectors.toList()));
    }

    //====================== HITS DTO =================================================================================/
    private Map<String, Object> hitsDTO(GamePlayer gamePlayer1) {
        Map<String, Object> mapa = new HashMap<>();
        //Chequear si hay GamePlayer2
        if (gamePlayer1.getGame().getGamePlayers().size() == 2) {
            GamePlayer gamePlayer2 = new GamePlayer();
            for (GamePlayer gamePlayer : gamePlayer1.getGame().getGamePlayers()) {
                if (gamePlayer != gamePlayer1) {
                    gamePlayer2 = gamePlayer;
                }
            }
                mapa.put("self", selfopponentDTO(gamePlayer1, gamePlayer2));
                mapa.put("opponent", selfopponentDTO(gamePlayer2, gamePlayer1));
                return mapa;
        }
        mapa.put("self", selfopponentDTO(gamePlayer1, new GamePlayer()));
        mapa.put("opponent", selfopponentDTO(new GamePlayer(), gamePlayer1));
        return mapa;
    }

    //====================== DTO SELF-OPPONENT ========================================================================/
    private List<Map> selfopponentDTO(GamePlayer gamePlayer1, GamePlayer gamePlayer2) {
        List<Map> mapList = new ArrayList<>();

        Set<Salvo> gp2salvos = gamePlayer2.getSalvoes();

            if( gamePlayer2 == null)
            {
                Map<String, Object> mapa = new LinkedHashMap<>();
                mapa.put("self", "" );
                mapa.put("opponent", "");
                mapa.put("id", gamePlayer1.getGame().getId());

                mapa.put("created", gamePlayer1.getGame().getCreationDate());
                mapa.put("gamePlayers", makeGamePlayer(gamePlayer1.getGame().getGamePlayers()));
                mapa.put("ships", makeShips(gamePlayer1.getShips()));
                mapa.put("salvoes", makeSalvos(gamePlayer1.getGame().getGamePlayers()));
                mapa.put("gameState", GameState.WAITING_FOR_OPPONENT);

               Collections.emptyMap();
            }

      else  {
            // Ordeno para que se muestren bien los turnos
            List<Salvo> OrderedSalvoes = gp2salvos.stream().sorted(Comparator
                    .comparing(salvo -> salvo.getTurn()))
                    .collect((Collectors.toList()));
            int carrier = 0;
            int battleship = 0;
            int submarine = 0;
            int destroyer = 0;
            int patrolboat = 0;

            for (Salvo salvo : OrderedSalvoes) { // Cada salvo
                Map<String, Object> damagesMap = new HashMap<>();
                int carrierHits = 0;
                int battleshipHits = 0;
                int submarineHits = 0;
                int destroyerHits = 0;
                int patrolboatHits = 0;
                Map<String, Object> mapa = new HashMap<>();
                List<String> hitLocations = new ArrayList<>();
                mapa.put("turn", salvo.getTurn());
                int missed = 0;

                for (String salvoLocation : salvo.getLocations()) { // Cada salvo location
                    boolean hit = false;
                    for (Ship ship : gamePlayer1.getShips()) { // Cada ship
                        for (String shipLocation : ship.getLocations()) { // Cada ship location
                            if (salvoLocation == shipLocation) {
                                hit = true;
                                hitLocations.add(salvoLocation);
                                switch (ship.getType()) {
                                    case "CARRIER":
                                        carrierHits++;
                                        carrier++;
                                        break;
                                    case "BATTLESHIP":
                                        battleship++;
                                        battleshipHits++;
                                        break;
                                    case "SUBMARINE":
                                        submarine++;
                                        submarineHits++;
                                        break;
                                    case "DESTROYER":
                                        destroyer++;
                                        destroyerHits++;
                                        break;
                                    case "PATROLBOAT":
                                        patrolboat++;
                                        patrolboatHits++;
                                        break;
                                }
                            }
                            if (hit) break;
                        }
                        if (hit) break;
                    }
                    if (!hit) missed++;
                }
                damagesMap.put("carrierHits", carrierHits);
                damagesMap.put("battleshipHits", battleshipHits);
                damagesMap.put("submarineHits", submarineHits);
                damagesMap.put("destroyerHits", destroyerHits);
                damagesMap.put("patrolboatHits", patrolboatHits);
                damagesMap.put("CARRIER", carrier);
                damagesMap.put("BATTLESHIP", battleship);
                damagesMap.put("SUBMARINE", submarine);
                damagesMap.put("DESTROYER", destroyer);
                damagesMap.put("PATROLBOAT", patrolboat);

                mapa.put("hitLocations", hitLocations);
                mapa.put("damages", damagesMap);
                mapa.put("missed", missed);

                mapList.add(mapa);
            }
        }


        return mapList;
    }

    //====================== ALL GAMES =====================================================//////
    @RequestMapping("/games")
    public Map<String, Object> getGameAll(Authentication authentication) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if(isGuest(authentication)){
            dto.put("player", "Guest");
        }
        else{
            Player player  = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }

        dto.put("games", gameRepository.findAll()
                .stream()
                .map(games->games.makeGameDTO()));

        return dto;
    }

    //======================== GAME METHOD POST ===========================================================
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }

        Player player  = playerRepository.findByUserName(authentication.getName());

        if(player ==  null){
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }

        Game game  = gameRepository.save(new Game());

        GamePlayer gamePlayer  = gamePlayerRepository.save(new GamePlayer(player,game));

        return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED);
    }

    //======================== GAME METHOD GAME/NN/PLAYERS POST ===========================================
    @RequestMapping(path = "/game/{gameID}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameID, Authentication authentication) {
        if (isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error", "You can't join a Game if You're Not Logged In!"), HttpStatus.UNAUTHORIZED);
        }

        Player  player  = playerRepository.findByUserName(authentication.getName());
        Game gameToJoin = gameRepository.getOne(gameID);

        if (gameRepository.getOne(gameID) == null) {
            return new ResponseEntity<>(makeMap("error", "No such game."), HttpStatus.FORBIDDEN);
        }

        if(player ==  null){
            return new ResponseEntity<>(makeMap("error", "No such game."), HttpStatus.FORBIDDEN);
        }
        long gamePlayersCount = gameToJoin.getGamePlayers().size();

        if (gamePlayersCount == 1) {
            GamePlayer gameplayer = gamePlayerRepository.save(new GamePlayer( player, gameToJoin));
            return new ResponseEntity<>(makeMap("gpid", gameplayer.getId()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(makeMap("error", "Game is full!"), HttpStatus.FORBIDDEN);
        }
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        return map;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;

    }


} // class GameController








