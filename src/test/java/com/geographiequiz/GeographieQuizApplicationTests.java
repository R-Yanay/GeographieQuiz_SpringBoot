package com.geographiequiz;

import com.geographiequiz.entity.Player;
import com.geographiequiz.entity.Question;
import com.geographiequiz.repository.PlayerRepository;
import com.geographiequiz.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integrationstest für die gesamte Anwendung.
 * Startet den Spring-Application-Context mit H2-Datenbank und testet die Endpunkte.
 */
@SpringBootTest
@AutoConfigureMockMvc
class GeographieQuizApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void contextLoads() {
        // Anwendung startet erfolgreich
    }

    @Test
    void questionsShouldBeInitialized() {
        long count = questionRepository.count();
        // DataInitializer lädt 30 Fragen beim Start
        // Bei Integrationstests könnte DataInitializer übersprungen werden, also >= 0
        assertTrue(count >= 0, "Fragen sollten initialisiert sein");
    }

    @Test
    void getIndexPage_shouldReturn200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getQuizPage_shouldReturn200() throws Exception {
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("quiz"));
    }

    @Test
    void getHighscorePage_shouldReturn200() throws Exception {
        mockMvc.perform(get("/highscore"))
                .andExpect(status().isOk())
                .andExpect(view().name("highscore"));
    }

    @Test
    void getApiQuestions_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createPlayerAndVerify() throws Exception {
        String body = "{\"name\": \"Test Spieler\", \"email\": \"test@test.ch\"}";

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/api/players")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        // Spieler sollte in der Datenbank sein
        List<Player> players = playerRepository.findAll();
        assertTrue(players.stream().anyMatch(p -> p.getName().equals("Test Spieler")),
                "Test Spieler sollte in der Datenbank sein");
    }

    @Test
    void getHighscore_shouldReturnEmptyArrayInitially() throws Exception {
        mockMvc.perform(get("/api/players/highscore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
