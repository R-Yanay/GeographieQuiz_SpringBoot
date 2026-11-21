package com.geographiequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hauptklasse für das GeographieQuiz Spring Boot Projekt.
 * Startet die Anwendung mit H2-Datenbank, REST-API und Frontend.
 */
@SpringBootApplication
public class GeographieQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeographieQuizApplication.class, args);
    }
}
