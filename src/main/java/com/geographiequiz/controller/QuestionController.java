package com.geographiequiz.controller;

import com.geographiequiz.entity.Question;
import com.geographiequiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST-Controller für Quiz-Fragen.
 * CRUD-Endpunkte für Fragen-Management.
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuizService quizService;

    /**
     * GET /api/questions — Alle Fragen laden
     */
    @GetMapping
    public List<Question> getAllQuestions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty
    ) {
        if (category != null && difficulty != null) {
            return quizService.getQuestionsByCategoryAndDifficulty(category, difficulty);
        } else if (category != null) {
            return quizService.getQuestionsByCategory(category);
        } else if (difficulty != null) {
            return quizService.getQuestionsByDifficulty(difficulty);
        } else {
            return quizService.getAllQuestions();
        }
    }

    /**
     * GET /api/questions/{id} — Frage nach ID laden
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) {
        return quizService.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id))
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/questions — Neue Frage erstellen (Admin)
     */
    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        return ResponseEntity.ok(question);
    }

    /**
     * PUT /api/questions/{id} — Frage aktualisieren (Admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @Valid @RequestBody Question question) {
        question.setId(id);
        return ResponseEntity.ok(question);
    }

    /**
     * DELETE /api/questions/{id} — Frage löschen (Admin)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
