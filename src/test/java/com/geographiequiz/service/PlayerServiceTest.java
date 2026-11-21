package com.geographiequiz.service;

import com.geographiequiz.entity.Highscore;
import com.geographiequiz.entity.Player;
import com.geographiequiz.repository.HighscoreRepository;
import com.geographiequiz.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit-Tests für PlayerService.
 * Testet Spieler-Erstellung, -Aktualisierung und Highscore-Management.
 */
@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private HighscoreRepository highscoreRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        player1 = new Player("Felix Muster", "felix@test.ch");
        player1.setId(1L);
        player2 = new Player("Max Mustermann", "max@test.ch");
        player2.setId(2L);
    }

    @Test
    void getAllPlayers_shouldReturnAllPlayers() {
        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> result = playerService.getAllPlayers();

        assertEquals(2, result.size());
        verify(playerRepository).findAll();
    }

    @Test
    void getAllPlayers_shouldReturnEmptyListWhenNoPlayers() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Player> result = playerService.getAllPlayers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getPlayerById_shouldReturnPlayerWhenExists() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));

        Optional<Player> result = playerService.getPlayerById(1L);

        assertTrue(result.isPresent());
        assertEquals("Felix Muster", result.get().getName());
    }

    @Test
    void getPlayerById_shouldReturnEmptyWhenNotFound() {
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Player> result = playerService.getPlayerById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void getPlayerByEmail_shouldReturnPlayerWhenExists() {
        when(playerRepository.findByEmail("felix@test.ch")).thenReturn(Optional.of(player1));

        Optional<Player> result = playerService.getPlayerByEmail("felix@test.ch");

        assertTrue(result.isPresent());
        assertEquals("felix@test.ch", result.get().getEmail());
    }

    @Test
    void getPlayerByEmail_shouldReturnEmptyWhenNotFound() {
        when(playerRepository.findByEmail("unknown@test.ch")).thenReturn(Optional.empty());

        Optional<Player> result = playerService.getPlayerByEmail("unknown@test.ch");

        assertFalse(result.isPresent());
    }

    @Test
    void createOrUpdatePlayer_newPlayer_shouldCreateAndSave() {
        when(playerRepository.findByEmail("new@test.ch")).thenReturn(Optional.empty());
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Player result = playerService.createOrUpdatePlayer("Neuer Spieler", "new@test.ch");

        assertEquals("Neuer Spieler", result.getName());
        assertEquals("new@test.ch", result.getEmail());
        assertNotNull(result.getCreatedAt());
        verify(playerRepository).save(any(Player.class));
    }

    @Test
    void createOrUpdatePlayer_existingPlayer_shouldUpdateName() {
        when(playerRepository.findByEmail("felix@test.ch")).thenReturn(Optional.of(player1));
        when(playerRepository.save(any(Player.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Player result = playerService.createOrUpdatePlayer("Felix Neuer", "felix@test.ch");

        assertEquals("Felix Neuer", result.getName());
        assertEquals("felix@test.ch", result.getEmail());
        verify(playerRepository).save(player1);
    }

    @Test
    void updateHighscore_shouldCreateAndSaveHighscore() {
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player1));
        when(highscoreRepository.save(any(Highscore.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Highscore result = playerService.updateHighscore(1L, 8, 10);

        assertEquals(player1, result.getPlayer());
        assertEquals(8, result.getScore());
        assertEquals(10, result.getTotalQuestions());
        assertNotNull(result.getGameDate());
        verify(highscoreRepository).save(any(Highscore.class));
    }

    @Test
    void updateHighscore_playerNotFound_shouldThrowException() {
        when(playerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> playerService.updateHighscore(99L, 8, 10));
    }

    @Test
    void getTop10Highscores_shouldReturnTop10() {
        Highscore hs1 = new Highscore(player1, 100, 10);
        Highscore hs2 = new Highscore(player2, 80, 10);
        when(highscoreRepository.findTop10ByOrderByScoreDesc()).thenReturn(Arrays.asList(hs1, hs2));

        List<Highscore> result = playerService.getTop10Highscores();

        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getScore());
        assertEquals(80, result.get(1).getScore());
    }

    @Test
    void getTop10Highscores_shouldReturnEmptyWhenNoHighscores() {
        when(highscoreRepository.findTop10ByOrderByScoreDesc()).thenReturn(Collections.emptyList());

        List<Highscore> result = playerService.getTop10Highscores();

        assertTrue(result.isEmpty());
    }

    @Test
    void getHighscoresByPlayer_shouldReturnPlayerHighscores() {
        Highscore hs1 = new Highscore(player1, 90, 10);
        when(highscoreRepository.findByPlayerIdOrderByGameDateDesc(1L)).thenReturn(List.of(hs1));

        List<Highscore> result = playerService.getHighscoresByPlayer(1L);

        assertEquals(1, result.size());
        assertEquals(90, result.get(0).getScore());
    }
}
