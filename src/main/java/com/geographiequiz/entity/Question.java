package com.geographiequiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * JPA-Entität für eine Quiz-Frage.
 * Repräsentiert die Tabelle `question` in der H2-Datenbank.
 */
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    @NotBlank(message = "Frage-Text darf nicht leer sein")
    private String questionText;

    @Column(name = "answer_a", nullable = false)
    @NotBlank(message = "Antwort A darf nicht leer sein")
    private String answerA;

    @Column(name = "answer_b", nullable = false)
    @NotBlank(message = "Antwort B darf nicht leer sein")
    private String answerB;

    @Column(name = "answer_c", nullable = false)
    @NotBlank(message = "Antwort C darf nicht leer sein")
    private String answerC;

    @Column(name = "answer_d", nullable = false)
    @NotBlank(message = "Antwort D darf nicht leer sein")
    private String answerD;

    @Column(name = "correct_answer", nullable = false)
    @NotBlank(message = "Korrekte Antwort darf nicht leer sein")
    @Size(min = 1, max = 1, message = "Korrekte Antwort muss A, B, C oder D sein")
    private String correctAnswer;

    @Column(name = "category", nullable = false)
    @NotBlank(message = "Kategorie darf nicht leer sein")
    private String category;

    @Column(name = "difficulty", nullable = false)
    @NotBlank(message = "Schwierigkeit darf nicht leer sein")
    private String difficulty;

    // Default-Konstruktor für JPA
    public Question() {
    }

    public Question(String questionText, String answerA, String answerB, String answerC,
                    String answerD, String correctAnswer, String category, String difficulty) {
        this.questionText = questionText;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
