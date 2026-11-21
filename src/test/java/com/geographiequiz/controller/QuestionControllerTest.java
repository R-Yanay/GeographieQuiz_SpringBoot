package com.geographiequiz.controller;

import com.geographiequiz.entity.Question;
import com.geographiequiz.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit-Tests für QuestionController mit @WebMvcTest.
 * Testet nur den Controller, ohne den Service-Stack.
 */
@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuizService quizService;

    @Test
    void getAllQuestions_shouldReturnAllQuestions() throws Exception {
        Question q1 = new Question("Hauptstadt?", "A", "B", "C", "D", "A", "Europa", "easy");
        q1.setId(1L);
        when(quizService.getAllQuestions()).thenReturn(List.of(q1));

        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getAllQuestions_empty_shouldReturnEmptyArray() throws Exception {
        when(quizService.getAllQuestions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getQuestionsByCategory_shouldReturnFilteredQuestions() throws Exception {
        Question q = new Question("Hauptstadt?", "A", "B", "C", "D", "A", "Europa", "easy");
        q.setId(1L);
        when(quizService.getQuestionsByCategory("Europa")).thenReturn(List.of(q));

        mockMvc.perform(get("/api/questions").param("category", "Europa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getQuestionsByDifficulty_shouldReturnFilteredQuestions() throws Exception {
        Question q = new Question("Hauptstadt?", "A", "B", "C", "D", "A", "Europa", "easy");
        q.setId(1L);
        when(quizService.getQuestionsByDifficulty("easy")).thenReturn(List.of(q));

        mockMvc.perform(get("/api/questions").param("difficulty", "easy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getQuestionsByCategoryAndDifficulty_shouldReturnFilteredQuestions() throws Exception {
        Question q = new Question("Hauptstadt?", "A", "B", "C", "D", "A", "Europa", "easy");
        q.setId(1L);
        when(quizService.getQuestionsByCategoryAndDifficulty("Europa", "easy")).thenReturn(List.of(q));

        mockMvc.perform(get("/api/questions")
                        .param("category", "Europa")
                        .param("difficulty", "easy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
