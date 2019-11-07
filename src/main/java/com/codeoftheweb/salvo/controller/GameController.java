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
    @Autowired
    ScoreRepository scoreRepository;

    //===================== /GAME VIEW/GP =============================================================================/
    @RequestMapping("/game_view/{gp}")
    public ResponseEntity<Map<String, Object>> findGamePlayer(@PathVariable long gp, Authentication authentication) {

        GamePlayer gamePlayer = gamePlayerRepository.findById(gp).orElse(null);

        if (gamePlayer == null) {
            return new ResponseEntity<>(makeMap("error", "EL GAMEPLAYER NO EXISTE"), HttpStatus.UNAUTHORIZED);
        }

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "NO PUEDE ACCEDER A ESTA VISTA"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByUserName(authentication.getName());

        Game game = gamePlayer.getGames();

        if (player == null) {
            return new ResponseEntity<>(makeMap("error", "PLAYER NO EXISTE"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer == null) {
            return new ResponseEntity<>(makeMap("error", " GP NO EXISTE"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getPlayer().getId() != player.getId()) {
            return new ResponseEntity<>(makeMap("error", "NO PUEDE ACCEDER A ESTA VISTA"), HttpStatus.CONFLICT);
        }

        if (gamePlayer.getPlayer() == player) {

            return new ResponseEntity<>(gameViewDTO(gamePlayer), HttpStatus.OK);
        }

        return new ResponseEntity<>(makeMap("ERROR!!", "NO PUEDE ACCEDER A ESTA VISTA"), HttpStatus.UNAUTHORIZED);
    }

    //====================== DTO GAMEVIEW =============================================================================/
    private Map<String, Object> gameViewDTO(GamePlayer gp) {
        Map<String, Object> dto = new HashMap<>();

        dto.put("id", gp.getGame().getId());
        dto.put("created", gp.getGame().getCreationDate());
        dto.put("gamePlayers", makeGamePlayer(gp.getGame().getGamePlayers()));
        dto.put("ships", makeShips(gp.getShips()));
        dto.put("salvoes", makeSalvos(gp.getGame().getGamePlayers()));//le pase el stream completo de salvos del game*/
        dto.put("hits", hitsDTO(gp));
        dto.put("gameState", checkGameState(gp));
        return dto;
    }

    //====================== HITS DTO =================================================================================/
    private Map<String, Object> hitsDTO(GamePlayer gamePlayer1) {
        Map<String, Object> mapa = new HashMap<>();
        // CHECK si hay GamePlayer2
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

        if (gamePlayer2 == null) {
            List<String> lista = new ArrayList<>();
        } else {
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
                                    case "carrier":
                                        carrierHits++;
                                        carrier++;
                                        break;
                                    case "battleship":
                                        battleship++;
                                        battleshipHits++;
                                        break;
                                    case "submarine":
                                        submarine++;
                                        submarineHits++;
                                        break;
                                    case "destroyer":
                                        destroyer++;
                                        destroyerHits++;
                                        break;
                                    case "patrolboat":
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
                damagesMap.put("carrier", carrier);
                damagesMap.put("battleship", battleship);
                damagesMap.put("submarine", submarine);
                damagesMap.put("destroyer", destroyer);
                damagesMap.put("patrolboat", patrolboat);

                mapa.put("hitLocations", hitLocations);
                mapa.put("damages", damagesMap);
                mapa.put("missed", missed);

                mapList.add(mapa);
            }
        }


        return mapList;
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

        Stream<Salvo> ss = gp.stream().flatMap(g -> g.getSalvoes().stream());

        return ss.map(salvo -> salvo.makeSalvoDTO()).collect((Collectors.toList()));
    }

    //======================== GET OPPONENT ===========================================================================/
    private Optional<GamePlayer> getOpponent(GamePlayer self) {

        return self.getGame().getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() != self.getId()).findFirst();
    }

    //=================== CHECKING GAME STATE =========================================================================/
    private GameState checkGameState(GamePlayer gamePlayer1) {

        boolean opponentSunkShips;
        boolean selfSunkShips;
        Game game = gamePlayer1.getGame();

        if (gamePlayer1.getShips().size() == 0) {
            return GameState.PLACESHIPS;
        }

        if (gamePlayer1.getGame().getGamePlayers().size() == 1) {
            return GameState.WAITINGFOROPP;
        }

        if (gamePlayer1.getGame().getGamePlayers().size() == 2) {

            GamePlayer opponent = getOpponent(gamePlayer1).orElse(new GamePlayer());

            opponentSunkShips = hits(opponent, gamePlayer1);

            selfSunkShips = hits(gamePlayer1, opponent);

            if((gamePlayer1.getSalvoes().size() != 0 ) && (opponent.getSalvoes().size() != 0 )){

                if ((gamePlayer1.getSalvoes().size() == opponent.getSalvoes().size()) && (opponentSunkShips) && (!selfSunkShips)) {


                    Score score = (new Score(1.0, gamePlayer1.getPlayer(), game));

                    Score scoreFind = scoreRepository.findAll().stream()
                                                               .filter(score1 -> score1.getGame() == score.getGame() && score.getPlayer() && score1.getPlayer() )
                                                               .findAny().orElse(null);

                    if(scoreFind == null){
                        scoreRepository.save(score);
                    }




                    return GameState.WON;
                }
            }

            if ((gamePlayer1.getSalvoes().size() == opponent.getSalvoes().size()) && (opponentSunkShips) && (selfSunkShips)) {

                scoreRepository.save(new Score( 0.5, gamePlayer1.getPlayer(), game));

                return GameState.TIE;
            }
            if ((gamePlayer1.getSalvoes().size() == opponent.getSalvoes().size()) && (!opponentSunkShips) && (selfSunkShips)) {

                scoreRepository.save(new Score(0.0, gamePlayer1.getPlayer(), game));

                return GameState.LOST;
            }

            if ((gamePlayer1.getSalvoes().size() == opponent.getSalvoes().size()) && (gamePlayer1.getId() < opponent.getId())) {
                return GameState.PLAY;
            }
            if (gamePlayer1.getSalvoes().size() < opponent.getSalvoes().size()){
                return GameState.PLAY;
            }
            if ((gamePlayer1.getSalvoes().size() == opponent.getSalvoes().size()) && (gamePlayer1.getId() > opponent.getId())) {
                return GameState.WAIT;
            }
            if (gamePlayer1.getSalvoes().size() > opponent.getSalvoes().size()){
                return GameState.WAIT;
            }

        }

        return GameState.UNDEFINED;
    }

    //============================ SUMATORIA DE HITS ==================================================================/
    private boolean hits(GamePlayer gamePlayer2, GamePlayer gamePlayer1) {

        List<String>    allShipLocations    =   gamePlayer2.getShips().stream().flatMap(ship -> ship.getLocations().stream()).collect(Collectors.toList());
        List<String>    allSalvoLocations   =   gamePlayer1.getSalvoes().stream().flatMap(salvo -> salvo.getLocations().stream()).collect(Collectors.toList());
        System.out.println("ships"+allShipLocations);
        System.out.println("salvos"+allSalvoLocations);
        boolean allMacth    =   allSalvoLocations.containsAll(allShipLocations);
        return allMacth;

    }

    //====================== ALL GAMES ================================================================================/
    @RequestMapping("/games")
    public Map<String, Object> getGameAll(Authentication authentication) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }

        dto.put("games", gameRepository.findAll()
                .stream()
                .map(games -> games.makeGameDTO()));

        return dto;
    }

    //======================== GAME METHOD POST =======================================================================/
    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }

        Player player = playerRepository.findByUserName(authentication.getName());

        if (player == null) {
            return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
        }

        Game game = gameRepository.save(new Game());

        GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, game));

        return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);
    }

    //================= MAP AUX =======================================================================================/
    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    //=============================== AUTH ============================================================================/
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;

    }

}