package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository , GameRepository gameRepository, GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			//*===== SAVE PLAYERS ========================
			Player player1 = new Player ("j.bauer@ctu.gov");
			Player player2 = new Player ("c.obrian@ctu.gov");
			Player player3 = new Player ("kim_bauer@gmail.com");
			Player player4 = new Player ("t.almeida@ctu.gov");

			playerRepository.save(player1);
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			//Agregamos tiempo adicional a tres juegos como lo pide el enunciado
			Game games1 = (new Game((long) 0));
			Game games2 = (new Game((long) 1));
			Game games3 = (new Game((long) 2));

			gameRepository.save(games1);
			gameRepository.save(games2);
			gameRepository.save(games3);

			// imprimimos segun relacion GamePlayer

			gamePlayerRepository.save(new GamePlayer(games1,player1));
			gamePlayerRepository.save(new GamePlayer(games2,player2));
			gamePlayerRepository.save(new GamePlayer(games3,player3));

		};



		};
	}

