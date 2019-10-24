package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    private LocalDateTime creationDate = LocalDateTime.now();
    //*===== RELATION 1-N BETWEEN Game-GamePlayer ==========
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    //*===== RELATION 1-N BETWEEN Player-Score ==========
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    public Set<Score> scores;

    public Game() {
    }
    public Game(Long horas){
        this.creationDate = LocalDateTime.now().plusHours(horas);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }


    //===== MAP Game ==========
   public Map<String, Object> makeGameDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate());
       dto.put("gamePlayers", this.getGamePlayers()
               .stream()
               .map(gam->gam.makeGamePlayerDTO() )
               .collect((Collectors.toList())));
       dto.put("scores",    this.getScores().stream().map(score -> score.makeScoreDTO()).collect(Collectors.toList()));

       return dto;
    }




}
