package com.geographiequiz.service;

import com.geographiequiz.entity.*;
import com.geographiequiz.repository.GameSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit-Tests für GameSessionService.
 * Testet Session-Start, Antwort-Speicherung und Session-Ende.
 */
@ExtendWith(MockitoExtension.class)
class GameSessionServiceTest {

    @Mock
    private GameSessionRepository gameSessionRepository;

    @Mock
    private QuizService quizService;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameSessionService gameSessionService;

    private Player player;
    private Question question;
    private GameSession session;

    @BeforeEach
    void setUp() {
        player = new Player("Felix Muster", "felix@test.ch");
        player.setId(1L);

        question = new Question("Hauptstadt von Frankreich?", "Berlin", "London", "Paris", "Madrid", "C", "Europa", "easy");
        question.setId(1L);

        session = new GameSession(player, 10);
        session.setId(1L);
    }

    @Test
    void startSession_shouldCreateAndSaveSession() {
        when(playerService.getPlayerById(1L)).thenReturn(Optional.of(player));
        when(gameSessionRepository.save(any(GameSession.class))).thenAnswer(invocation -> {
            GameSession s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        GameSession result = gameSessionService.startSession(1L, 10);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(player, result.getPlayer());
        assertEquals(10, result.getTotalQuestions());
        assertNotNull(result.getStartTime());
        verify(gameSessionRepository).save(any(GameSession.class));
    }

    @Test
    void startSession_playerNotFound_shouldThrowException() {
        when(playerService.getPlayerById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameSessionService.startSession(99L, 10));
    }

    @Test
    void saveAnswer_correctAnswer_shouldIncrementScore() {
        when(gameSessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(quizService.checkAnswer("C", "C")).thenReturn(true);
        when(gameSessionRepository.save(any(GameSession.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Answer result = gameSessionService.saveAnswer(1L, question, "C");

        assertTrue(result.isCorrect());
        assertEquals("C", result.getSelectedAnswer());
        assertEquals(1, session.getScore());
        assertEquals(1, session.getAnswers().size());
    }

    @Test
    void saveAnswer_wrongAnswer_shouldNotIncrementScore() {
        when(gameSessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(quizService.checkAnswer("C", "A")).thenReturn(false);
        when(gameSessionRepository.save(any(GameSession.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Answer result = gameSessionService.saveAnswer(1L, question, "A");

        assertFalse(result.isCorrect());
        assertEquals("A", result.getSelectedAnswer());
        assertEquals(0, session.getScore());
        assertEquals(1, session.getAnswers().size());
    }

    @Test
    void saveAnswer_sessionNotFound_shouldThrowException() {
        when(gameSessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameSessionService.saveAnswer(99L, question, "A"));
    }

    @Test
    void endSession_shouldSetEndTimeAndUpdateHighscore() {
        when(gameSessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(gameSessionRepository.save(any(GameSession.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(playerService.updateHighscore(1L, 0, 10)).thenReturn(new Highscore());

        GameSession result = gameSessionService.endSession(1L);

        assertNotNull(result.getEndTime());
        verify(playerService).updateHighscore(1L, 0, 10);
    }

    @Test
    void endSession_sessionNotFound_shouldThrowException() {
        when(gameSessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameSessionService.endSession(99L));
    }

    @Test
    void getSessionsByPlayer_shouldReturnSessions() {
        when(gameSessionRepository.findByPlayerId(1L)).thenReturn(List.of(session));

        List<GameSession> result = gameSessionService.getSessionsByPlayer(1L);

        assertEquals(1, result.size());
        assertEquals(session, result.get(0));
    }

    @Test
    void getSessionsByPlayer_shouldReturnEmptyWhenNoSessions() {
        when(gameSessionRepository.findByPlayerId(1L)).thenReturn(Collections.emptyList());

        List<GameSession> result = gameSessionService.getSessionsByPlayer(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getRecentSessionsByPlayer_shouldReturnLast10() {
        when(gameSessionRepository.findTop10ByPlayerIdOrderByStartTimeDesc(1L)).thenReturn(Arrays.asList(session));

        List<GameSession> result = gameSessionService.getRecentSessionsByPlayer(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getSessionById_shouldReturnSession() {
        when(gameSessionRepository.findById(1L)).thenReturn(Optional.of(session));

        GameSession result = gameSessionService.getSessionById(1L);

        assertEquals(session, result);
    }

    @Test
    void getSessionById_notFound_shouldThrowException() {
        when(gameSessionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> gameSessionService.getSessionById(99L));
    }
}
