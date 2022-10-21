package com.company;

import java.time.LocalDate;

public class oneToOne {
    private int idGame;
    private LocalDate date;
    private String category;
    private String tpA;
    private String tpB;

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTpA() {
        return tpA;
    }

    public void setTpA(String tpA) {
        this.tpA = tpA;
    }

    public String getTpB() {
        return tpB;
    }

    public void setTpB(String tpB) {
        this.tpB = tpB;
    }

    public oneToOne(int idGame, LocalDate date, String category, String tpA, String tpB) {
        this.idGame = idGame;
        this.date = date;
        this.category = category;
        this.tpA = tpA;
        this.tpB = tpB;
    }
}
