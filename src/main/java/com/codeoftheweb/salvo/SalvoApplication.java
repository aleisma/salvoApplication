package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository , GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository) {
        return (args) -> {
            //*===== SAVE PLAYERS ========================
            Player bauer = new Player ("j.bauer@ctu.gov");
            Player obrian = new Player ("c.obrian@ctu.gov");
            Player kim_bauer = new Player ("kim_bauer@gmail.com");
            Player almeida = new Player ("t.almeida@ctu.gov");

            playerRepository.save(bauer);
            playerRepository.save(obrian);
            playerRepository.save(kim_bauer);
            playerRepository.save(almeida);

            /* =================== GAMES =================== */
            Game game1 = (new Game((long) 0));
            Game game2 = (new Game((long) 1));
            Game game3 = (new Game((long) 2));
            Game game4 = (new Game((long) 3));
            Game game5 = (new Game((long) 0));
            Game game6 = (new Game((long) 1));
            Game game7 = (new Game((long) 2));
            Game game8 = (new Game((long) 3));

            gameRepository.save(game1);
            gameRepository.save(game2);
            gameRepository.save(game3);
            gameRepository.save(game4);
            gameRepository.save(game5);
            gameRepository.save(game6);
            gameRepository.save(game7);
            gameRepository.save(game8);

            /* =================== GAMEPLAYER =================== */
            GamePlayer gamePlayer1 = new GamePlayer(bauer,game1);
            GamePlayer gamePlayer2 = new GamePlayer(obrian,game1);

            GamePlayer gamePlayer3 = new GamePlayer(bauer, game2);
            GamePlayer gamePlayer4 = new GamePlayer(obrian, game2);

            GamePlayer gamePlayer5 = new GamePlayer(obrian, game3);
            GamePlayer gamePlayer6 = new GamePlayer(almeida, game3);

            GamePlayer gamePlayer7 = new GamePlayer(obrian, game4);
            GamePlayer gamePlayer8 = new GamePlayer(bauer, game4);

            GamePlayer gamePlayer9 = new GamePlayer(almeida, game5);
            GamePlayer gamePlayer10 = new GamePlayer(bauer, game5);

            GamePlayer gamePlayer11 = new GamePlayer(kim_bauer, game6);
            GamePlayer gamePlayer12 = new GamePlayer(null, game6);

            GamePlayer gamePlayer13 = new GamePlayer(almeida, game7);
            GamePlayer gamePlayer14 = new GamePlayer(null, game7);

            GamePlayer gamePlayer15 = new GamePlayer(kim_bauer, game8);
            GamePlayer gamePlayer16 = new GamePlayer(almeida, game8);


            gamePlayerRepository.save(gamePlayer1);
            gamePlayerRepository.save(gamePlayer2);
            gamePlayerRepository.save(gamePlayer3);
            gamePlayerRepository.save(gamePlayer4);
            gamePlayerRepository.save(gamePlayer5);
            gamePlayerRepository.save(gamePlayer6);
            gamePlayerRepository.save(gamePlayer7);
            gamePlayerRepository.save(gamePlayer8);
            gamePlayerRepository.save(gamePlayer9);
            gamePlayerRepository.save(gamePlayer10);
            gamePlayerRepository.save(gamePlayer11);
            gamePlayerRepository.save(gamePlayer12);
            gamePlayerRepository.save(gamePlayer13);
            gamePlayerRepository.save(gamePlayer14);
            gamePlayerRepository.save(gamePlayer15);
            gamePlayerRepository.save(gamePlayer16);


            /* =================== SHIPS ===================
            String[] shipsLocation = new String[]{"H2","H3","H4"};
            List<String> arrayLocations = new ArrayList<>(Arrays.asList(shipsLocation));
            shipRepository.save(new Ship (new GamePlayer(bauer,game1),"SUBMARINE", arrayLocations));
            shipRepository.save(new Ship (new GamePlayer(obrian,game1),"DESTROYER", arrayLocations));*/

            //----------------------------------------------------------------------------------------------

            List<Ship> shipList = new ArrayList<>();
            //creating ships
            Ship ship1 = new Ship();
            Ship ship2 = new Ship();
            Ship ship3 = new Ship();
            Ship ship4 = new Ship();
            Ship ship5 = new Ship();
            Ship ship6 = new Ship();
            Ship ship7 = new Ship();
            Ship ship8 = new Ship();
            Ship ship9 = new Ship();
            Ship ship10 = new Ship();
            Ship ship11 = new Ship();
            Ship ship12 = new Ship();
            Ship ship13 = new Ship();
            Ship ship14 = new Ship();
            Ship ship15 = new Ship();
            Ship ship16 = new Ship();
            Ship ship17 = new Ship();
            Ship ship18 = new Ship();
            Ship ship19 = new Ship();
            Ship ship20 = new Ship();
            Ship ship21 = new Ship();
            Ship ship22 = new Ship();
            Ship ship23 = new Ship();
            Ship ship24 = new Ship();
            Ship ship25 = new Ship();
            Ship ship26 = new Ship();
            Ship ship27 = new Ship();
            //setting ship stats
            String[] locations1 = {"H2","H3","H4"};
            ship1.setType("destroyer");
            ship1.setShipsLocation(Arrays.asList(locations1));
            String[] locations2 = {"E1","F1","G1"};
            ship2.setType("submarine");
            ship2.setShipsLocation(Arrays.asList(locations2));
            String[] locations3 = {"B4","B5"};
            ship3.setType("patrolboat");
            ship3.setShipsLocation(Arrays.asList(locations3));
            String[] locations4 = {"B5","C5","D5"};
            ship4.setType("destroyer");
            ship4.setShipsLocation(Arrays.asList(locations4));
            String[] locations5 = {"F1","F2"};
            ship5.setType("patrolboat");
            ship5.setShipsLocation(Arrays.asList(locations5));
            String[] locations6 = {"B5","C5","D5"};
            ship6.setType("destroyer");
            ship6.setShipsLocation(Arrays.asList(locations6));
            String[] locations7 = {"C6","C7"};
            ship7.setType("patrolboat");
            ship7.setShipsLocation(Arrays.asList(locations7));
            String[] locations8 = {"A2","A3","A4"};
            ship8.setType("submarine");
            ship8.setShipsLocation(Arrays.asList(locations8));
            String[] locations9 = {"G6","H6"};
            ship9.setType("patrolboat");
            ship9.setShipsLocation(Arrays.asList(locations9));
            String[] locations10 = {"B5","C5","D5"};
            ship10.setType("destroyer");
            ship10.setShipsLocation(Arrays.asList(locations10));
            String[] locations11 = {"C6","C7"};
            ship11.setType("patrolboat");
            ship11.setShipsLocation(Arrays.asList(locations11));
            String[] locations12 = {"A2","A3","A4"};
            ship12.setType("submarine");
            ship12.setShipsLocation(Arrays.asList(locations12));
            String[] locations13 = {"G6","H6"};
            ship13.setType("patrolboat");
            ship13.setShipsLocation(Arrays.asList(locations13));
            String[] locations14 = {"B5","C5","D5"};
            ship14.setType("destroyer");
            ship14.setShipsLocation(Arrays.asList(locations14));
            String[] locations15 = {"C6","C7"};
            ship15.setType("patrolboat");
            ship15.setShipsLocation(Arrays.asList(locations15));
            String[] locations16 = {"A2","A3","A4"};
            ship16.setType("submarine");
            ship16.setShipsLocation(Arrays.asList(locations16));
            String[] locations17 = {"G6","H6"};
            ship17.setType("patrolboat");
            ship17.setShipsLocation(Arrays.asList(locations17));
            String[] locations18 = {"B5","C5","D5"};
            ship18.setType("destroyer");
            ship18.setShipsLocation(Arrays.asList(locations18));
            String[] locations19 = {"C6","C7"};
            ship19.setType("patrolboat");
            ship19.setShipsLocation(Arrays.asList(locations19));
            String[] locations20 = {"A2","A3","A4"};
            ship20.setType("submarine");
            ship20.setShipsLocation(Arrays.asList(locations20));
            String[] locations21 = {"G6","H6"};
            ship21.setType("patrolboat");
            ship21.setShipsLocation(Arrays.asList(locations21));
            String[] locations22 = {"B5","C5","D5"};
            ship22.setType("destroyer");
            ship22.setShipsLocation(Arrays.asList(locations22));
            String[] locations23 = {"C6","C7"};
            ship23.setType("patrolboat");
            ship23.setShipsLocation(Arrays.asList(locations23));
            String[] locations24 = {"B5","C5","D5"};
            ship24.setType("destroyer");
            ship24.setShipsLocation(Arrays.asList(locations24));
            String[] locations25 = {"C6","C7"};
            ship25.setType("patrolboat");
            ship25.setShipsLocation(Arrays.asList(locations25));
            String[] locations26 = {"A2","A3","A4"};
            ship26.setType("submarine");
            ship26.setShipsLocation(Arrays.asList(locations26));
            String[] locations27 = {"G6","H6"};
            ship27.setType("patrolboat");
            ship27.setShipsLocation(Arrays.asList(locations27));

            ship1.setGamePlayer(gamePlayer1);
            ship2.setGamePlayer(gamePlayer1);
            ship3.setGamePlayer(gamePlayer1);
            ship4.setGamePlayer(gamePlayer2);
            ship5.setGamePlayer(gamePlayer2);
            ship6.setGamePlayer(gamePlayer3);
            ship7.setGamePlayer(gamePlayer3	);
            ship8.setGamePlayer(gamePlayer4	);
            ship9.setGamePlayer(gamePlayer4);
            ship10.setGamePlayer(gamePlayer5);
            ship11.setGamePlayer(gamePlayer5);
            ship12.setGamePlayer(gamePlayer6);
            ship13.setGamePlayer(gamePlayer6);
            ship14.setGamePlayer(gamePlayer7);
            ship15.setGamePlayer(gamePlayer7);
            ship16.setGamePlayer(gamePlayer8);
            ship17.setGamePlayer(gamePlayer8);
            ship18.setGamePlayer(gamePlayer9);
            ship19.setGamePlayer(gamePlayer9);
            ship20.setGamePlayer(gamePlayer10);
            ship21.setGamePlayer(gamePlayer10);
            ship22.setGamePlayer(gamePlayer11);
            ship23.setGamePlayer(gamePlayer11);
            ship24.setGamePlayer(gamePlayer13);
            ship25.setGamePlayer(gamePlayer13);
            ship26.setGamePlayer(gamePlayer14);
            ship27.setGamePlayer(gamePlayer14);

            shipRepository.save(ship1);
            shipRepository.save(ship2);
            shipRepository.save(ship3);
            shipRepository.save(ship4);
            shipRepository.save(ship5);
            shipRepository.save(ship6);
            shipRepository.save(ship7);
            shipRepository.save(ship8);
            shipRepository.save(ship9);
            shipRepository.save(ship10);
            shipRepository.save(ship11);
            shipRepository.save(ship12);
            shipRepository.save(ship13);
            shipRepository.save(ship14);
            shipRepository.save(ship15);
            shipRepository.save(ship16);
            shipRepository.save(ship17);
            shipRepository.save(ship18);
            shipRepository.save(ship19);
            shipRepository.save(ship20);
            shipRepository.save(ship21);
            shipRepository.save(ship22);
            shipRepository.save(ship23);
            shipRepository.save(ship24);
            shipRepository.save(ship25);
            shipRepository.save(ship26);
            shipRepository.save(ship27);
        };

    }
}

