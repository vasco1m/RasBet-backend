package com.company;

import java.time.LocalDate;

public class BetSingle {
    private int idBet;
    private int idGame;
    private double odd;
    private int idParticipant;

    public int getIdBet() {
        return idBet;
    }

    public void setIdBet(int idBet) {
        this.idBet = idBet;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public BetSingle(int idBet, int idGame, double odd, int idParticipant) {
        this.idBet = idBet;
        this.idGame = idGame;
        this.odd = odd;
        this.idParticipant = idParticipant;
    }
}
