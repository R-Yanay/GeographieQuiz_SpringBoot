package com.geographiequiz.repository;

import com.geographiequiz.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA-Repository für Spieler.
 * Bietet CRUD-Operationen und Queries für Spieler-Daten.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Findet einen Spieler nach E-Mail-Adresse.
     */
    Optional<Player> findByEmail(String email);

    /**
     * Findet alle Spieler, sortiert nach Erstellungsdatum (neueste zuerst).
     */
    List<Player> findAllByOrderByCreatedAtDesc();

    /**
     * Findet einen Spieler nach Name.
     */
    Optional<Player> findByName(String name);
}
