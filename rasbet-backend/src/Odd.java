package com.company;

import java.time.LocalDate;

public class Odd {
    private int idOdd;
    private int idGame;
    private String house;
    private double value;
    private int type;
    private LocalDate date;

    public int getIdOdd() {
        return idOdd;
    }

    public void setIdOdd(int idOdd) {
        this.idOdd = idOdd;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Odd(int idOdd, int idGame, String house, double value, int type, LocalDate date) {
        this.idOdd = idOdd;
        this.idGame = idGame;
        this.house = house;
        this.value = value;
        this.type = type;
        this.date = date;
    }
}
