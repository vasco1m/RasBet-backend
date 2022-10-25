package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "betGame",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idBet")
        })
public class BetGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBet;

    @NotNull
    private int idGame;

    @NotNull
    private double odd;

    public BetGame() {

    }
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
