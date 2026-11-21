package com.geographiequiz.controller;

import com.geographiequiz.entity.*;
import com.geographiequiz.service.GameSessionService;
import com.geographiequiz.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit-Tests für QuizController mit @WebMvcTest.
 * Testet den Spiel-Flow: Start, Antwort, Status, Ende.
 */
@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameSessionService gameSessionService;

    @MockitoBean
    private QuizService quizService;

    private Player player;
    private Question question;
    private GameSession session;

    @BeforeEach
    void setUp() {
        player = new Player("Felix", "felix@test.ch");
        player.setId(1L);

        question = new Question("Hauptstadt?", "A", "B", "C", "D", "C", "Europa", "easy");
        question.setId(1L);

        session = new GameSession(player, 10);
        session.setId(1L);
    }

    @Test
    void startGame_shouldReturnSessionAndQuestions() throws Exception {
        when(gameSessionService.startSession(1L, 10)).thenReturn(session);
        when(quizService.getRandomQuestions(10)).thenReturn(List.of(question));

        String body = "{\"playerId\": 1, \"questionCount\": 10}";

        mockMvc.perform(post("/api/quiz/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(1))
                .andExpect(jsonPath("$.totalQuestions").value(10))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.questions").isArray());
    }

    @Test
    void submitAnswer_correct_shouldReturnTrue() throws Exception {
        when(quizService.getAllQuestions()).thenReturn(List.of(question));
        Answer answer = new Answer(session, question, "C", true);
        when(gameSessionService.saveAnswer(anyLong(), any(Question.class), anyString())).thenReturn(answer);

        String body = "{\"sessionId\": 1, \"questionId\": 1, \"selectedAnswer\": \"C\"}";

        mockMvc.perform(post("/api/quiz/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(true))
                .andExpect(jsonPath("$.correctAnswer").value("C"))
                .andExpect(jsonPath("$.selectedAnswer").value("C"));
    }

    @Test
    void submitAnswer_wrong_shouldReturnFalse() throws Exception {
        when(quizService.getAllQuestions()).thenReturn(List.of(question));
        Answer answer = new Answer(session, question, "A", false);
        when(gameSessionService.saveAnswer(anyLong(), any(Question.class), anyString())).thenReturn(answer);

        String body = "{\"sessionId\": 1, \"questionId\": 1, \"selectedAnswer\": \"A\"}";

        mockMvc.perform(post("/api/quiz/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isCorrect").value(false))
                .andExpect(jsonPath("$.correctAnswer").value("C"));
    }

    @Test
    void getStatus_shouldReturnSessionStatus() throws Exception {
        when(gameSessionService.getSessionById(1L)).thenReturn(session);

        mockMvc.perform(get("/api/quiz/status/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(1))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.totalQuestions").value(10))
                .andExpect(jsonPath("$.isFinished").value(false));
    }

    @Test
    void endGame_shouldReturnFinalScore() throws Exception {
        session.setScore(8);
        when(gameSessionService.endSession(1L)).thenReturn(session);
        when(quizService.calculateScore(8, 10)).thenReturn(80);

        mockMvc.perform(post("/api/quiz/end/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value(1))
                .andExpect(jsonPath("$.finalScore").value(8))
                .andExpect(jsonPath("$.totalQuestions").value(10))
                .andExpect(jsonPath("$.percentage").value(80));
    }
}
