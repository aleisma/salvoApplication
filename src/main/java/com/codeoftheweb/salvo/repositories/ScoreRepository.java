package com.codeoftheweb.salvo.repositories;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Player findScorePlayer( GamePlayer gamePlayer, Player player);
}
