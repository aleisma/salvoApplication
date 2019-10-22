package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private long id;

    private String  userName;
    private String  email;
    //*===== RELATION 1-N BETWEEN Player-GamePlayer ==========
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    public Set<GamePlayer> gamePlayers;

    //*===== RELATION 1-N BETWEEN Player-Score ==========
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    public Set<Score> scores;

    public Player() { }

    public Player(String userName) {
        this.userName = userName;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Set<Score> getScores() {
        return scores;
    }
    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    //===== MAP Player-GamePlayer ==========
    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

}








