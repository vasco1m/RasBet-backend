package net.java.rasbetbackend.model;

public class BetGame {

    private int idBet;
    private int idGame;
    private double odd;
    //private enum bet;

    public int getIdBet() {
        return idBet;
    }

    public void setIdBet(int idBet) {
        this.idBet = idBet;
    }

    public int getIdGame() {
        return idGame;
    }
    //public int getBet() {
    //    return bet;
    //}

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public BetGame(int idBet, int idGame, double odd) {
        this.idBet = idBet;
        this.idGame = idGame;
        this.odd = odd;
        //this.bet=bet;
    }
}
