package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Score {
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    private Double score;

    private LocalDateTime finishDate;

    //*===== RELATION 1-N BETWEEN Game-Score ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    //*===== RELATION 1-N BETWEEN Player-Score ==========
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

   //*===== RELATION 1-N BETWEEN GamePlayer-Scores ==========
    @OneToMany(mappedBy = "score", fetch = FetchType.EAGER)
   Set<Score> scores;


    public Score(){}

    public Score( Double score, Player player,Game game){
        this.finishDate = LocalDateTime.now();
        this.game = game;
        this.player = player;
        this.score = score;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

     //===== MAP Score==========
    public Map<String, Object> makeScoreDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", this.getPlayer().getId());
        dto.put("score", this.getScore());
        dto.put("finishDate",this.getFinishDate());
        return dto;
    }







}
