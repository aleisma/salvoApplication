package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class GamePlayer {

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;
    private LocalDateTime joinDate;
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

    public GamePlayer(){

    }

    public GamePlayer(Player player,Game game ){
        this.game = game;
        this.player = player;
        this.joinDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Game getGames() {
        return game;
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

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    //===== MAP Player-GamePlayer ==========
    public Map<String, Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("player", this.player.makePlayerDTO());
        return dto;
    }










}
