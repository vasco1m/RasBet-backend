package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "betSingle",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idBet")
        })
public class BetSingle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBet;
    @NotNull
    private int idGame;
    @NotNull
    private double odd;
    @NotNull
    private int idParticipant;

    public BetSingle() {

    }

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
