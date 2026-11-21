package com.geographiequiz.repository;

import com.geographiequiz.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA-Repository für Quiz-Fragen.
 * Bietet CRUD-Operationen und benutzerdefinierte Queries für Fragen.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Findet alle Fragen einer bestimmten Kategorie.
     */
    List<Question> findByCategory(String category);

    /**
     * Findet alle Fragen einer bestimmten Schwierigkeit.
     */
    List<Question> findByDifficulty(String difficulty);

    /**
     * Findet alle Fragen nach Kategorie und Schwierigkeit.
     */
    List<Question> findByCategoryAndDifficulty(String category, String difficulty);

    /**
     * Wählt zufällige Fragen aus der Datenbank.
     * @param count Anzahl der gewünschten Fragen
     * @return Liste mit zufälligen Fragen
     */
    @Query("SELECT q FROM Question q ORDER BY FUNCTION('RAND()')")
    List<Question> findRandomQuestions(@Param("count") int count);

    /**
     * Wählt zufällige Fragen einer bestimmten Kategorie.
     */
    @Query("SELECT q FROM Question q WHERE q.category = :category ORDER BY FUNCTION('RAND()')")
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, int count);
}
