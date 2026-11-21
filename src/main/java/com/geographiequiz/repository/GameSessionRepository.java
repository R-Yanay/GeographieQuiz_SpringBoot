package com.geographiequiz.repository;

import com.geographiequiz.entity.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA-Repository für Spiel-Sessions.
 * Bietet CRUD-Operationen und Queries für Spiel-Verläufe.
 */
@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {

    /**
     * Findet alle Sessions eines Spielers.
     */
    List<GameSession> findByPlayerId(Long playerId);

    /**
     * Findet die letzten 10 Sessions eines Spielers, sortiert nach Startzeit (neueste zuerst).
     */
    List<GameSession> findTop10ByPlayerIdOrderByStartTimeDesc(Long playerId);

    /**
     * Findet alle beendeten Sessions (mit endTime gesetzt).
     */
    List<GameSession> findByEndTimeIsNotNull();
}
