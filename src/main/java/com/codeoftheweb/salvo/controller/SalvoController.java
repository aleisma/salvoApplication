package com.codeoftheweb.salvo.controller;


import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repositories.SalvoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    SalvoRepository salvoRepository;

    //================== GAMES/PLAYERS/nn/SALVOES ========================================
    @RequestMapping("/games/players/{gamePlayerId}/salvos")
    public ResponseEntity<Map<String, Object>> getSalvoes(@PathVariable long gamePlayerId,
                                                          Authentication  authentication,
                                                          @RequestBody Salvo salvo) {

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(makeMap("error", "No user logged in"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayerRepository.findById(gamePlayerId) == null) {
            return new ResponseEntity<>(makeMap("error", "There is no GamePlayer with given ID"),
                    HttpStatus.UNAUTHORIZED);
        }

        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).get();

        if (!authentication.getName().equals(gamePlayer.getPlayer().getUserName())) {
            return new ResponseEntity<>(makeMap("error", "The current user is not the GamePlayer the ID" +
                    "references"),
                    HttpStatus.UNAUTHORIZED);
        }

      if(gamePlayer.getSalvoes().isEmpty()){
          salvo.setTurn(1);
          salvo.setGamePlayer(gamePlayer);
          salvoRepository.save(salvo);
          return new ResponseEntity<>(makeMap("OK", "Turn Set"), HttpStatus.CREATED);
      }

      //======

        // TIRA ERROR CUANDO INTENTO AGREGAR SALVOES EN EL TURNO 2, PARA EL PLAYER 2, FORBIDDEN; ADEMAS SE DESORDENA EL JSON ========================

      GamePlayer opponent = getOpponent(gamePlayer).orElse(null);

      if(opponent != null){
          if(gamePlayer.getSalvoes().size() == opponent.getSalvoes().size()){
              salvo.setTurn(gamePlayer.getSalvoes().size()+1);
              salvo.setGamePlayer(gamePlayer);
          } else {
              return new ResponseEntity<>(makeMap("ERROR", "Ya tienes Salvoes en este turno"), HttpStatus.FORBIDDEN); }
          }
        else{

              return new ResponseEntity<>(makeMap("ERROR", "NO hay oponente no se puede guardar el salvo"), HttpStatus.FORBIDDEN);

      }
        salvo.setGamePlayer(gamePlayer);
          salvoRepository.save(salvo);

        return new ResponseEntity<>(makeMap("OK", "Salvoes added"), HttpStatus.CREATED);

        }

        // Obtengo el player opponent
    private Optional <GamePlayer> getOpponent(GamePlayer self){

        return self.getGame().getGamePlayers().stream().filter(gamePlayer -> gamePlayer.getId() != self.getId()).findFirst();
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        return map;
    }

}