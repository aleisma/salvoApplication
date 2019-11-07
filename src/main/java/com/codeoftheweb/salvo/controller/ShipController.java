package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    ShipRepository shipRepository;

    //=================== GAME/PLAYERS/{gamePlayerId}/ships ===================================================
    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Object> getShips(@PathVariable long gamePlayerId,
                                           @RequestBody List<Ship> ships,
                                           Authentication authentication ) {

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

        if (gamePlayer.getShips().size() != 0) {
            return new ResponseEntity<>(makeMap("error", "\n" + "El jugador tiene Ships colocados"),
                    HttpStatus.FORBIDDEN);
        }

        ships.forEach( ship -> { ship.setGamePlayer(gamePlayer);

            shipRepository.save(ship);
        });

        gamePlayerRepository.save(gamePlayer); //PARA QUE SE ACTUALIZE REPO

        return new ResponseEntity<>(makeMap("OK", "Ships added"), HttpStatus.CREATED);
    }

    //AUTH ============================================================================================================/
    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    //AUX DTO =========================================================================================================/
    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        return map;
    }

}




