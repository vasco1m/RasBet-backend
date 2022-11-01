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
    @NotNull
    private int result;

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

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public BetGame(int idBet, int idGame, double odd,int result) {
        this.idBet = idBet;
        this.idGame = idGame;
        this.odd = odd;
        this.result=result;
    }
}
