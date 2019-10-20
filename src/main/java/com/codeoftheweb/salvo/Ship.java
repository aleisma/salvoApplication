package com.codeoftheweb.salvo;
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
    @Column(name="shipsLocation")
    private List<String> shipsLocation;

    //*===== RELATION 1-N BETWEEN Ship-GamePlayer ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    public Ship(){}

    public Ship(GamePlayer gamePlayer, String type, List<String> shipsLocation) {
        this.gamePlayer = gamePlayer;
        this.type = type;
        this.shipsLocation  = shipsLocation;
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

    public List<String> getShipsLocation() {
        return shipsLocation;
    }

    public void setShipsLocation(List<String> shipsLocation) {
        this.shipsLocation = shipsLocation;
    }
    //===== MAP Ship-GamePlayer ==========


    public Map<String, Object> getShipDTO() {
        Map<String,Object>  shipDTO = new LinkedHashMap<>();
        shipDTO.put("type", this.type);
        shipDTO.put("locations", this.shipsLocation);
        return shipDTO;
    }





}
