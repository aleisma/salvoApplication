package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.repositories.SalvoRepository;
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
    SalvoRepository salvoRepository;


    //=================== GAME VIEW ===================================================
    @RequestMapping("/game_view/{nn}")
    public ResponseEntity<Map<String, Object>> getViewGP(@PathVariable Long nn,
                                                         Authentication  authentication){

        if(isGuest(authentication)){
            return new  ResponseEntity<>(makeMap("ERROR!!","NO PUEDE ACCEDER A ESTA VISTA"),HttpStatus.UNAUTHORIZED);
        }

        Player  player  = playerRepository.findByUserName(authentication.getName());

        GamePlayer gamePlayer = gamePlayerRepository.findById(nn).orElse(null);
        Game game = gamePlayer.getGames();

        if(player == null){
            return new  ResponseEntity<>(makeMap("ERROR!","PLAYER NO EXISTE"),HttpStatus.UNAUTHORIZED);
        }

        if(gamePlayer == null ){
            return  new ResponseEntity<>(makeMap("ERROR!"," GP NO EXISTE"), HttpStatus.UNAUTHORIZED);

        }

        if(gamePlayer.getPlayer().getId() !=  player.getId()){
            return new  ResponseEntity<>(makeMap("ERROR!!","NO PUEDE ACCEDER A ESTA VISTA"),HttpStatus.CONFLICT);
        }

        Map<String, Object>dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());

        Map<String, Object> hits = new LinkedHashMap<>();
        hits.put("self", new ArrayList<>());


        hits.put("opponent", new ArrayList<>());

        dto.put("gameState", "PLACESHIPS");

        dto.put("gamePlayers", game.getGamePlayers()
                .stream()
                .map(gam->gam.makeGamePlayerDTO() )
                .collect((Collectors.toList())));

        dto.put("ship", gamePlayer.getShips()
                .stream()
                .map(ship -> ship.getShipDTO())
                .collect((Collectors.toList())));

        dto.put("salvoes",gamePlayer.getGame().getGamePlayers()
                .stream()
                .flatMap(gamePlayer1 -> gamePlayer1.getSalvoes()
                        .stream()
                        .map(salvo -> salvo.makeSalvoDTO()))
                .collect((Collectors.toList())));

        Collections.emptyMap();

        dto.put("hits", hits);


        return  new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //=================== GAMES ===================================================
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

    //======================== GAME METHOD POST ===========================================
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


    private List<Map<String, Object>> hitsDTO (GamePlayer self, GamePlayer opponent ){

       List<Salvo> = self.getSalvoes();

        List<Map<String, Object>> hitSelfs = new ArrayList<>();



        return hitSelfs;
    }



}