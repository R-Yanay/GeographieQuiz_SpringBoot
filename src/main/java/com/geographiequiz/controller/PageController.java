package com.geographiequiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller für die Frontend-Seiten (Thymeleaf Templates).
 * Leitet die Hauptseiten an die Templates weiter.
 */
@Controller
public class PageController {

    /**
     * GET / — Startseite (wird auch von WebConfig ViewController bedient)
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * GET /quiz — Quiz-Spiel-Seite
     */
    @GetMapping("/quiz")
    public String quiz() {
        return "quiz";
    }

    /**
     * GET /highscore — Highscore-Tabelle
     */
    @GetMapping("/highscore")
    public String highscore() {
        return "highscore";
    }
}
