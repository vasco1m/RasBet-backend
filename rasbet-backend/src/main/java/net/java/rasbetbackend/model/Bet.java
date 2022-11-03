package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "bet",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idBet")
        })
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idBet;

    @NotNull
    private int nif;
    @NotNull
    private double value;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    @NotNull
    private BetState state;

    @NotNull
    private double odd;

    @NotNull
    private int idGame;

    @NotNull
    private int type;

    public Bet() {

    }

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BetState getState() {
        return state;
    }

    public void setState(BetState state) {
        this.state = state;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bet(int nif, double value, BetState state, double odd, int idGame, int type) {
        this.nif = nif;
        this.value = value;
        this.dateTime = LocalDateTime.now();
        this.state = state;
        this.odd = odd;
        this.idGame = idGame;
        this.type = type;
    }
}
