package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository , GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
        return (args) -> {
            //*===== SAVE PLAYERS ========================
            Player bauer = new Player ("j.bauer@ctu.gov", passwordEncoder().encode("24"));
            Player obrian = new Player ("c.obrian@ctu.gov", passwordEncoder().encode("42"));
            Player kim_bauer = new Player ("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
            Player almeida = new Player ("t.almeida@ctu.gov", passwordEncoder().encode("mole"));

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
           // Game game6 = (new Game((long) 1));
           // Game game7 = (new Game((long) 2));
            Game game8 = (new Game((long) 3));

            gameRepository.save(game1);
            gameRepository.save(game2);
            gameRepository.save(game3);
            gameRepository.save(game4);
            gameRepository.save(game5);
          //  gameRepository.save(game6);
          //  gameRepository.save(game7);
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

          /*  GamePlayer gamePlayer11 = new GamePlayer(kim_bauer, game6);
            GamePlayer gamePlayer12 = new GamePlayer(null, game6); */

           /* GamePlayer gamePlayer13 = new GamePlayer(almeida, game7);
            GamePlayer gamePlayer14 = new GamePlayer(null, game7); */

            GamePlayer gamePlayer11 = new GamePlayer(kim_bauer, game8);
            GamePlayer gamePlayer12 = new GamePlayer(almeida, game8);


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
          /*  gamePlayerRepository.save(gamePlayer11);
            gamePlayerRepository.save(gamePlayer12);
            gamePlayerRepository.save(gamePlayer13);
            gamePlayerRepository.save(gamePlayer14); */
            gamePlayerRepository.save(gamePlayer11);
            gamePlayerRepository.save(gamePlayer12);

            //* =================== SHIPS ===================
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

          /*  ship22.setGamePlayer(gamePlayer11);
            ship23.setGamePlayer(gamePlayer11);
            ship24.setGamePlayer(gamePlayer13);
            ship25.setGamePlayer(gamePlayer13);
            ship26.setGamePlayer(gamePlayer14);
            ship27.setGamePlayer(gamePlayer14); */

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

            //* =================== SALVOES ===================
            Salvo salvo1 = new Salvo(1,new ArrayList<String>(Arrays.asList("B5", "C5", "F1")),gamePlayer1);
            Salvo salvo2 = new Salvo(1,new ArrayList<String>(Arrays.asList("B4", "B5", "B6")),gamePlayer2);
            Salvo salvo3 = new Salvo(2,new ArrayList<String>(Arrays.asList("F2", "D5")),gamePlayer1);
            Salvo salvo4 = new Salvo(2,new ArrayList<String>(Arrays.asList("E1", "H3", "A2")),gamePlayer2);

            Salvo salvo5 = new Salvo(1,new ArrayList<String>(Arrays.asList("A2", "A4", "G6")),gamePlayer3);
            Salvo salvo6 = new Salvo(1,new ArrayList<String>(Arrays.asList("B5", "D5", "C7")),gamePlayer4);
            Salvo salvo7 = new Salvo(2,new ArrayList<String>(Arrays.asList("A3", "H6")),gamePlayer3);
            Salvo salvo8 = new Salvo(2,new ArrayList<String>(Arrays.asList("C5", "C6")),gamePlayer4);

            Salvo salvo9 = new Salvo(1,new ArrayList<String>(Arrays.asList("G6", "H6", "A4")),gamePlayer5);
            Salvo salvo10 = new Salvo(1,new ArrayList<String>(Arrays.asList("H1", "H2", "H3")),gamePlayer6);
            Salvo salvo11 = new Salvo(2,new ArrayList<String>(Arrays.asList("A2", "A3", "D8")),gamePlayer5);
            Salvo salvo12 = new Salvo(2,new ArrayList<String>(Arrays.asList("E1", "F2", "G3")),gamePlayer6);

            Salvo salvo13 = new Salvo(1,new ArrayList<String>(Arrays.asList("A3", "A4", "F7")),gamePlayer7);
            Salvo salvo14 = new Salvo(2,new ArrayList<String>(Arrays.asList("B5", "C6", "H1")),gamePlayer8);
            Salvo salvo15 = new Salvo(2,new ArrayList<String>(Arrays.asList("A2", "G6", "H6")),gamePlayer7);
            Salvo salvo16 = new Salvo(2,new ArrayList<String>(Arrays.asList("C5", "C7", "D5")),gamePlayer8);

            Salvo salvo17 = new Salvo(1,new ArrayList<String>(Arrays.asList("A1", "A2", "A3")),gamePlayer9);
            Salvo salvo18 = new Salvo(1,new ArrayList<String>(Arrays.asList("B5", "B6", "C7")),gamePlayer10);
            Salvo salvo19 = new Salvo(2,new ArrayList<String>(Arrays.asList("G6", "G7", "G8")),gamePlayer9);
            Salvo salvo20 = new Salvo(2,new ArrayList<String>(Arrays.asList("C6", "D6", "E6")),gamePlayer10);
            Salvo salvo21 = new Salvo(3,new ArrayList<String>(Arrays.asList("H1", "H8")),gamePlayer9);

            salvoRepository.save(salvo1);
            salvoRepository.save(salvo2);
            salvoRepository.save(salvo3);
            salvoRepository.save(salvo4);
            salvoRepository.save(salvo5);
            salvoRepository.save(salvo6);
            salvoRepository.save(salvo7);
            salvoRepository.save(salvo8);
            salvoRepository.save(salvo9);
            salvoRepository.save(salvo10);
            salvoRepository.save(salvo11);
            salvoRepository.save(salvo12);
            salvoRepository.save(salvo13);
            salvoRepository.save(salvo14);
            salvoRepository.save(salvo15);
            salvoRepository.save(salvo16);
            salvoRepository.save(salvo17);
            salvoRepository.save(salvo18);
            salvoRepository.save(salvo19);
            salvoRepository.save(salvo20);
            salvoRepository.save(salvo21);

            //* =================== SCORES ===================
           Score scoreBauer1 = new Score(1.0,bauer, game1 );
           Score scoreBauer2 = new Score(0.5,bauer, game2 );
          Score scoreBauer3 = new Score(0.5,bauer, game4 );
          Score scoreBauer4 = new Score(0.0 ,bauer, game5 );

            Score scoreObrian1 = new Score(1.0,obrian, game1 );
          Score scoreObrian2 = new Score(0.0,obrian, game2 );
          Score scoreObrian3 = new Score(0.5,obrian, game3 );
            Score scoreObrian4 = new Score(0.5,obrian, game4 );

          Score scoreAlmeida1 = new Score(0.0,almeida, game3 );
           //Score scoreAlmeida2 = new Score(0.0,almeida, game6 );
          Score scoreAlmeida3 = new Score(0.0,almeida, game3 );

            scoreRepository.save(scoreBauer1);
            scoreRepository.save(scoreBauer2);
            scoreRepository.save(scoreBauer3);
            scoreRepository.save(scoreBauer4);
            scoreRepository.save(scoreObrian1);
            scoreRepository.save(scoreObrian2);
            scoreRepository.save(scoreObrian3);
            scoreRepository.save(scoreObrian4);
            scoreRepository.save(scoreAlmeida1);
           // scoreRepository.save(scoreAlmeida2);
            scoreRepository.save(scoreAlmeida3);
        };
    }
}
//============ VALIDATION =============================================
@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            Player player = playerRepository.findByUserName(inputName);
            if (player != null) {
                return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

//=================== Security Configuration ================
@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //*=============== AUTHORIZATION =========================
        http.authorizeRequests()
                .antMatchers("/web/**").permitAll()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("api/game_view/*").hasAuthority("USER")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/games").permitAll();

        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login");

  //==============Render Console========
        http.headers().frameOptions().disable();

        http.logout().logoutUrl("/api/logout");
     // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}






