package com.company;

import java.time.LocalDate;

public class GameOneToMany {
    private int idGame;
    private LocalDate dateTime;
    private String name;
    private String category;
    private boolean draw;

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public GameOneToMany(int idGame, LocalDate dateTime, String name, String category, boolean draw) {
        this.idGame = idGame;
        this.dateTime = dateTime;
        this.name = name;
        this.category = category;
        this.draw = draw;
    }
}
