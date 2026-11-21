package com.geographiequiz.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA-Entität für einen Highscore-Eintrag.
 * Repräsentiert die Tabelle `highscore` in der H2-Datenbank.
 * Speichert die Top-Ergebnisse der Spieler.
 */
@Entity
@Table(name = "highscore")
public class Highscore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "game_date", nullable = false)
    private LocalDateTime gameDate;

    @Column(name = "total_questions", nullable = false)
    private int totalQuestions;

    public Highscore() {
    }

    public Highscore(Player player, int score, int totalQuestions) {
        this.player = player;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.gameDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
