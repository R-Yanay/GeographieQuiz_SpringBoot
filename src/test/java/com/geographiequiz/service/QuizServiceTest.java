package com.geographiequiz.service;

import com.geographiequiz.entity.Question;
import com.geographiequiz.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit-Tests für QuizService.
 * Testet das isolierte Verhalten des Services ohne Datenbank.
 */
@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizService quizService;

    private Question question1;
    private Question question2;
    private Question question3;

    @BeforeEach
    void setUp() {
        question1 = new Question("Hauptstadt von Frankreich?", "Berlin", "London", "Paris", "Madrid", "C", "Europa", "easy");
        question1.setId(1L);
        question2 = new Question("Höchster Berg in Afrika?", "Kilimandscharo", "Mount Kenya", "Rwenzori", "Atlas", "A", "Afrika", "easy");
        question2.setId(2L);
        question3 = new Question("Längste Bergkette?", "Alpen", "Himalaya", "Anden", "Rocky Mountains", "C", "Amerika", "medium");
        question3.setId(3L);
    }

    @Test
    void getAllQuestions_shouldReturnAllQuestions() {
        List<Question> questions = Arrays.asList(question1, question2, question3);
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> result = quizService.getAllQuestions();

        assertEquals(3, result.size());
        verify(questionRepository).findAll();
    }

    @Test
    void getAllQuestions_shouldReturnEmptyListWhenNoQuestions() {
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Question> result = quizService.getAllQuestions();

        assertTrue(result.isEmpty());
    }

    @Test
    void getQuestionsByCategory_shouldReturnQuestionsForCategory() {
        when(questionRepository.findByCategory("Europa")).thenReturn(List.of(question1));

        List<Question> result = quizService.getQuestionsByCategory("Europa");

        assertEquals(1, result.size());
        assertEquals("Europa", result.get(0).getCategory());
    }

    @Test
    void getQuestionsByCategory_shouldReturnEmptyListForUnknownCategory() {
        when(questionRepository.findByCategory("Antarktis")).thenReturn(Collections.emptyList());

        List<Question> result = quizService.getQuestionsByCategory("Antarktis");

        assertTrue(result.isEmpty());
    }

    @Test
    void getQuestionsByDifficulty_shouldReturnQuestionsForDifficulty() {
        when(questionRepository.findByDifficulty("easy")).thenReturn(Arrays.asList(question1, question2));

        List<Question> result = quizService.getQuestionsByDifficulty("easy");

        assertEquals(2, result.size());
    }

    @Test
    void getQuestionsByCategoryAndDifficulty_shouldReturnMatchingQuestions() {
        when(questionRepository.findByCategoryAndDifficulty("Europa", "easy")).thenReturn(List.of(question1));

        List<Question> result = quizService.getQuestionsByCategoryAndDifficulty("Europa", "easy");

        assertEquals(1, result.size());
        assertEquals("Europa", result.get(0).getCategory());
        assertEquals("easy", result.get(0).getDifficulty());
    }

    @Test
    void getRandomQuestions_shouldReturnRequestedCount() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question1, question2, question3));

        List<Question> result = quizService.getRandomQuestions(2);

        assertEquals(2, result.size());
    }

    @Test
    void getRandomQuestions_shouldReturnAllWhenCountExceedsTotal() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question1, question2));

        List<Question> result = quizService.getRandomQuestions(10);

        assertEquals(2, result.size());
    }

    @Test
    void checkAnswer_correctAnswer_shouldReturnTrue() {
        assertTrue(quizService.checkAnswer("A", "A"));
        assertTrue(quizService.checkAnswer("B", "B"));
        assertTrue(quizService.checkAnswer("C", "C"));
        assertTrue(quizService.checkAnswer("D", "D"));
    }

    @Test
    void checkAnswer_wrongAnswer_shouldReturnFalse() {
        assertFalse(quizService.checkAnswer("A", "B"));
        assertFalse(quizService.checkAnswer("C", "D"));
    }

    @Test
    void checkAnswer_shouldBeCaseInsensitive() {
        assertTrue(quizService.checkAnswer("A", "a"));
        assertTrue(quizService.checkAnswer("b", "B"));
    }

    @Test
    void calculateScore_allCorrect_shouldReturn100() {
        int score = quizService.calculateScore(10, 10);
        assertEquals(100, score);
    }

    @Test
    void calculateScore_halfCorrect_shouldReturn50() {
        int score = quizService.calculateScore(5, 10);
        assertEquals(50, score);
    }

    @Test
    void calculateScore_noCorrect_shouldReturn0() {
        int score = quizService.calculateScore(0, 10);
        assertEquals(0, score);
    }

    @Test
    void calculateScore_zeroTotalQuestions_shouldReturn0() {
        int score = quizService.calculateScore(5, 0);
        assertEquals(0, score);
    }

    @Test
    void calculateScore_oneOutOfThree_shouldReturn33() {
        int score = quizService.calculateScore(1, 3);
        assertEquals(33, score);
    }
}
