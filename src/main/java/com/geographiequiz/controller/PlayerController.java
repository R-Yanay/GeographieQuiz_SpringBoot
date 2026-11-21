package com.geographiequiz.controller;

import com.geographiequiz.entity.Highscore;
import com.geographiequiz.entity.Player;
import com.geographiequiz.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST-Controller für Spieler-Management.
 * Endpunkte für Spieler und Highscores.
 */
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    /**
     * GET /api/players — Alle Spieler laden
     */
    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    /**
     * GET /api/players/{id} — Spieler nach ID laden
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/players — Neuen Spieler erstellen
     */
    @PostMapping
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) {
        Player created = playerService.createOrUpdatePlayer(player.getName(), player.getEmail());
        return ResponseEntity.ok(created);
    }

    /**
     * GET /api/players/{id}/highscores — Highscores eines Spielers
     */
    @GetMapping("/{id}/highscores")
    public List<Highscore> getHighscoresByPlayer(@PathVariable Long id) {
        return playerService.getHighscoresByPlayer(id);
    }

    /**
     * GET /api/highscore — Top 10 Highscores
     */
    @GetMapping("/highscore")
    public List<Highscore> getTop10Highscores() {
        return playerService.getTop10Highscores();
    }
}
