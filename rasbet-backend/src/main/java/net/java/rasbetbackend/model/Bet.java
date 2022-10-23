package net.java.rasbetbackend.model;

import java.time.LocalDate;

public class Bet {
    private int idBet;
    private int nif;
    private double value;
    private LocalDate dateTime;

    public int getIdBet() {
        return idBet;
    }

    public void setIdBet(int idBet) {
        this.idBet = idBet;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public Bet(int idBet, int nif, double value, LocalDate dateTime) {
        this.idBet = idBet;
        this.nif = nif;
        this.value = value;
        this.dateTime = dateTime;
    }
}
