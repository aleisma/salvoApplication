package com.codeoftheweb.salvo.model;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Ship {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private long id;
    private String type;
    //*===== LIST CREATED SHIP LOCATIONS ==========
    @ElementCollection
    @Column(name="locations")

    private List<String> locations;

    //*===== RELATION 1-N BETWEEN Ship-GamePlayer ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    public Ship(){}

    public Ship(GamePlayer gamePlayer, String type, List<String> locations) {
        this.gamePlayer = gamePlayer;
        this.type = type;
        this.locations  = locations;
    }

    //===== MAP Ship-GamePlayer ==========
    public Map<String, Object> getShipDTO() {
        Map<String,Object>  dto= new LinkedHashMap<>();
        dto.put("type", this.type);
        dto.put("locations", this.locations);
        return dto;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }








}
