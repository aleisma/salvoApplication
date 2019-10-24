package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

   private Player isPlayer(Authentication authentication) {
        return playerRepository.findByUserName(authentication.getName());
    }

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


    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String email, @RequestParam String password) {

        if ( email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player( email, passwordEncoder.encode(password)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }






















    @RequestMapping("/game_view/{nn}")
    public Map<String, Object> a(@PathVariable long nn){
        Map<String, Object>dto = new LinkedHashMap<String, Object>();

        GamePlayer gamePlayer = gamePlayerRepository.findById(nn).orElse(null);
        Game game = gamePlayer.getGames();

        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate());

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

        return dto;

    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }









}