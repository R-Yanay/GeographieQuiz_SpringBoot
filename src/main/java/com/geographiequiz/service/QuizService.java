package com.geographiequiz.service;

import com.geographiequiz.entity.Question;
import com.geographiequiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service für Quiz-Logik.
 * Lädt Fragen, prüft Antworten und berechnet den Score.
 */
@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Lädt alle Fragen aus der Datenbank.
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     * Lädt Fragen nach Kategorie.
     */
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    /**
     * Lädt Fragen nach Schwierigkeit.
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    /**
     * Lädt Fragen nach Kategorie und Schwierigkeit.
     */
    public List<Question> getQuestionsByCategoryAndDifficulty(String category, String difficulty) {
        return questionRepository.findByCategoryAndDifficulty(category, difficulty);
    }

    /**
     * Wählt zufällige Fragen aus der Datenbank.
     * @param count Anzahl der gewünschten Fragen
     * @return Liste mit zufälligen Fragen
     */
    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, Math.min(count, allQuestions.size()));
    }

    /**
     * Prüft, ob eine Antwort korrekt ist.
     * @param correctAnswer Die korrekte Antwort (A, B, C, D)
     * @param selectedAnswer Die gewählte Antwort (A, B, C, D)
     * @return true wenn korrekt, false sonst
     */
    public boolean checkAnswer(String correctAnswer, String selectedAnswer) {
        return correctAnswer.equalsIgnoreCase(selectedAnswer);
    }

    /**
     * Berechnet den Score basierend auf korrekten Antworten.
     * @param correctAnswers Anzahl der korrekten Antworten
     * @param totalQuestions Gesamtanzahl der Fragen
     * @return Score als Prozentsatz (0-100)
     */
    public int calculateScore(int correctAnswers, int totalQuestions) {
        if (totalQuestions == 0) {
            return 0;
        }
        return (correctAnswers * 100) / totalQuestions;
    }
}
