package com.geographiequiz.service;

import com.geographiequiz.entity.Highscore;
import com.geographiequiz.entity.Player;
import com.geographiequiz.repository.HighscoreRepository;
import com.geographiequiz.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service für Spieler-Management.
 * Erstellt und aktualisiert Spieler, verwaltet Highscores.
 */
@Service
@Transactional
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HighscoreRepository highscoreRepository;

    /**
     * Findet alle Spieler.
     */
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    /**
     * Findet einen Spieler nach ID.
     */
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    /**
     * Findet einen Spieler nach E-Mail.
     */
    public Optional<Player> getPlayerByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    /**
     * Erstellt einen neuen Spieler.
     * Wenn der Spieler bereits existiert (nach E-Mail), wird er aktualisiert.
     */
    public Player createOrUpdatePlayer(String name, String email) {
        return playerRepository.findByEmail(email)
                .map(existing -> {
                    existing.setName(name);
                    return playerRepository.save(existing);
                })
                .orElseGet(() -> {
                    Player player = new Player(name, email);
                    return playerRepository.save(player);
                });
    }

    /**
     * Aktualisiert den Highscore nach Spielende.
     */
    public Highscore updateHighscore(Long playerId, int score, int totalQuestions) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Spieler nicht gefunden: " + playerId));
        Highscore highscore = new Highscore(player, score, totalQuestions);
        return highscoreRepository.save(highscore);
    }

    /**
     * Findet die Top 10 Highscores.
     */
    public List<Highscore> getTop10Highscores() {
        return highscoreRepository.findTop10ByOrderByScoreDesc();
    }

    /**
     * Findet alle Highscores eines Spielers.
     */
    public List<Highscore> getHighscoresByPlayer(Long playerId) {
        return highscoreRepository.findByPlayerIdOrderByGameDateDesc(playerId);
    }
}
