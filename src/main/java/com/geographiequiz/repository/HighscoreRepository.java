package com.geographiequiz.repository;

import com.geographiequiz.entity.Highscore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA-Repository für Highscore-Einträge.
 * Bietet CRUD-Operationen und Queries für Top-Ergebnisse.
 */
@Repository
public interface HighscoreRepository extends JpaRepository<Highscore, Long> {

    /**
     * Findet die Top 10 Highscores, sortiert nach Score (höchster zuerst).
     */
    @Query("SELECT h FROM Highscore h ORDER BY h.score DESC")
    List<Highscore> findTop10ByOrderByScoreDesc();

    /**
     * Findet alle Highscores eines Spielers, sortiert nach Datum (neueste zuerst).
     */
    List<Highscore> findByPlayerIdOrderByGameDateDesc(Long playerId);

    /**
     * Findet den höchsten Score eines Spielers.
     */
    @Query("SELECT MAX(h.score) FROM Highscore h WHERE h.player.id = :playerId")
    Integer findMaxScoreByPlayerId(Long playerId);
}
