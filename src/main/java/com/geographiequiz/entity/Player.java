package com.geographiequiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * JPA-Entität für einen Quiz-Spieler.
 * Repräsentiert die Tabelle `player` in der H2-Datenbank.
 */
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name darf nicht leer sein")
    @Size(min = 2, max = 100, message = "Name muss 2-100 Zeichen lang sein")
    private String name;

    @Column(name = "email")
    @Email(message = "E-Mail muss gültig sein")
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Player() {
    }

    public Player(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
