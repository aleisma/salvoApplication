package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
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
    ShipRepository shipRepository;
    @Autowired
    ScoreRepository scoreRepository;


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
