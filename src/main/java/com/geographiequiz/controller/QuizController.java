package com.geographiequiz.controller;

import com.geographiequiz.entity.*;
import com.geographiequiz.service.GameSessionService;
import com.geographiequiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST-Controller für das Quiz-Spiel.
 * Endpunkte für Spiel-Start, Antwort-Abgabe und Spiel-Status.
 */
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private GameSessionService gameSessionService;

    @Autowired
    private QuizService quizService;

    /**
     * POST /api/quiz/start — Spiel starten
     * Body: { "playerId": 1, "questionCount": 10 }
     */
    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startGame(@RequestBody Map<String, Object> request) {
        Long playerId = Long.valueOf(request.get("playerId").toString());
        int questionCount = request.get("questionCount") != null ? Integer.parseInt(request.get("questionCount").toString()) : 10;

        GameSession session = gameSessionService.startSession(playerId, questionCount);
        List<Question> questions = quizService.getRandomQuestions(questionCount);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("questions", questions);
        response.put("totalQuestions", questionCount);
        response.put("score", 0);

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/quiz/answer — Antwort abgeben
     * Body: { "sessionId": 1, "questionId": 5, "selectedAnswer": "A" }
     */
    @PostMapping("/answer")
    public ResponseEntity<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());
        Long questionId = Long.valueOf(request.get("questionId").toString());
        String selectedAnswer = request.get("selectedAnswer").toString();

        // Frage finden
        Question question = quizService.getAllQuestions().stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Frage nicht gefunden: " + questionId));

        // Antwort speichern
        Answer answer = gameSessionService.saveAnswer(sessionId, question, selectedAnswer);

        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", answer.isCorrect());
        response.put("correctAnswer", question.getCorrectAnswer());
        response.put("selectedAnswer", selectedAnswer);

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/quiz/status/{sessionId} — Spiel-Status abrufen
     */
    @GetMapping("/status/{sessionId}")
    public ResponseEntity<Map<String, Object>> getStatus(@PathVariable Long sessionId) {
        GameSession session = gameSessionService.getSessionById(sessionId);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("score", session.getScore());
        response.put("totalQuestions", session.getTotalQuestions());
        response.put("answeredQuestions", session.getAnswers().size());
        response.put("isFinished", session.getEndTime() != null);
        response.put("startTime", session.getStartTime());
        response.put("endTime", session.getEndTime());

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/quiz/end/{sessionId} — Spiel beenden
     */
    @PostMapping("/end/{sessionId}")
    public ResponseEntity<Map<String, Object>> endGame(@PathVariable Long sessionId) {
        GameSession session = gameSessionService.endSession(sessionId);

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("finalScore", session.getScore());
        response.put("totalQuestions", session.getTotalQuestions());
        response.put("percentage", quizService.calculateScore(session.getScore(), session.getTotalQuestions()));
        response.put("endTime", session.getEndTime());

        return ResponseEntity.ok(response);
    }
}
