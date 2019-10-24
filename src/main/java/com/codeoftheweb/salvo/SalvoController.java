package com.codeoftheweb.salvo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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


    @RequestMapping("/games")
    public Map<String, Object> getGameAll() {
       Map<String, Object> dto = new LinkedHashMap<String, Object>();



       dto.put("player", "Guest");
        dto.put("games",    gameRepository.findAll()
                .stream()
                .map(games->games.makeGameDTO()));

        return dto;
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





}