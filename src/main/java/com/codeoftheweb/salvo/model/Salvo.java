package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Salvo {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    private int turn;

    //*===== LIST CREATED SALVO LOCATIONS ==========
    @ElementCollection
    @Column(name = "locations ")
    private List<String> location = new ArrayList<>();

    //*===== RELATION 1-N BETWEEN Salvo-GamePlayer ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;

    public Salvo(int turn, List<String> location, GamePlayer gp){
        this.turn = turn;
        this.location = location;
        this.gamePlayer = gp;
    }

    public Salvo(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }


    //*===== MAP SALVO-GamePlayer ==================
    public Map<String, Object> makeSalvoDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getId());
        dto.put("locations", this.getLocation() );

        return dto;









    }
}
