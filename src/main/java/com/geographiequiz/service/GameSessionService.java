package com.geographiequiz.service;

import com.geographiequiz.entity.*;
import com.geographiequiz.repository.GameSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service für Spiel-Session-Management.
 * Startet und beendet Sessions, speichert Antworten.
 */
@Service
@Transactional
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private PlayerService playerService;

    /**
     * Startet eine neue Spiel-Session.
     * @param playerId ID des Spielers
     * @param questionCount Anzahl der Fragen
     * @return Neue GameSession mit zufälligen Fragen
     */
    public GameSession startSession(Long playerId, int questionCount) {
        Player player = playerService.getPlayerById(playerId)
                .orElseThrow(() -> new RuntimeException("Spieler nicht gefunden: " + playerId));
        GameSession session = new GameSession(player, questionCount);
        return gameSessionRepository.save(session);
    }

    /**
     * Speichert eine Antwort in der aktuellen Session.
     * @param sessionId ID der Session
     * @param question Die Frage
     * @param selectedAnswer Die gewählte Antwort (A, B, C, D)
     * @return Die gespeicherte Antwort
     */
    public Answer saveAnswer(Long sessionId, Question question, String selectedAnswer) {
        GameSession session = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session nicht gefunden: " + sessionId));

        boolean isCorrect = quizService.checkAnswer(question.getCorrectAnswer(), selectedAnswer);
        Answer answer = new Answer(session, question, selectedAnswer, isCorrect);
        session.getAnswers().add(answer);

        if (isCorrect) {
            session.setScore(session.getScore() + 1);
        }

        gameSessionRepository.save(session);
        return answer;
    }

    /**
     * Beendet eine Spiel-Session und aktualisiert den Highscore.
     * @param sessionId ID der Session
     * @return Die beendete Session
     */
    public GameSession endSession(Long sessionId) {
        GameSession session = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session nicht gefunden: " + sessionId));

        session.setEndTime(LocalDateTime.now());
        GameSession savedSession = gameSessionRepository.save(session);

        // Highscore aktualisieren
        playerService.updateHighscore(
                session.getPlayer().getId(),
                session.getScore(),
                session.getTotalQuestions()
        );

        return savedSession;
    }

    /**
     * Findet alle Sessions eines Spielers.
     */
    public List<GameSession> getSessionsByPlayer(Long playerId) {
        return gameSessionRepository.findByPlayerId(playerId);
    }

    /**
     * Findet die letzten 10 Sessions eines Spielers.
     */
    public List<GameSession> getRecentSessionsByPlayer(Long playerId) {
        return gameSessionRepository.findTop10ByPlayerIdOrderByStartTimeDesc(playerId);
    }

    /**
     * Findet eine Session nach ID.
     */
    public GameSession getSessionById(Long sessionId) {
        return gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session nicht gefunden: " + sessionId));
    }
}
