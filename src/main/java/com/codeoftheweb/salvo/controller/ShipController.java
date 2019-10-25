package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.repositories.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShipController {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ShipRepository shipRepository;

    //=================== GAME/PLAYERS/{gamePlayerId}/ships ===================================================
    @RequestMapping(path = "/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Object> getShips(@PathVariable String gamePlayerID, @RequestBody List<Ship> ships, Authentication authentication ) {




        Player player = playerRepository.findByUserName(gamePlayerID);
        playerRepository.save(player);







        if (isGuest(authentication)){
            return new ResponseEntity<>(makeMap("error", "You can't join a Game if You're Not Logged In!"), HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> dto = new LinkedHashMap<String, Object>();


        return  new ResponseEntity<>(dto, HttpStatus.OK);
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





