package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class GamePlayer {

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;
    private LocalDateTime joinDate;
    private GameState gameState;

    //*===== RELATION 1-N BETWEEN Game-GamePlayer ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    //*===== RELATION 1-N BETWEEN Player-GamePlayer ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    //*===== RELATION 1-N BETWEEN GamePlayer-Ship ==========
    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ships;

    //*===== RELATION 1-N BETWEEN GamePlayer-Salvo ==========
    @OneToMany(mappedBy="gamePlayer",fetch=FetchType.EAGER)
    Set<Salvo> salvoes;

    public GamePlayer(){
        
        // instancias para que no se rompa el gameview cuando no hay 2 GamePlayers.
        ships = new HashSet<>();
        salvoes = new HashSet<>();

    }

    public GamePlayer(Player player,Game game ){
        this.game = game;
        this.player = player;
        this.joinDate = LocalDateTime.now();
    }

    //===== MAP Player-GamePlayer ==========
    public Map<String, Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", player.makePlayerDTO());  // Tira un error cuando se crea un juego y nose agregan ambos jugadores
                                                       // Por ese motivo se considero crear solo 10 GamePlayers ya que estaban completos.
        return dto;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public void setGames(Game game) {
        this.game = game;
    }

    public Player getPlayers() {
        return player;
    }

    public void setPlayers(Player player) {
        this.player = player;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public Game getGames() {
        return game;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
