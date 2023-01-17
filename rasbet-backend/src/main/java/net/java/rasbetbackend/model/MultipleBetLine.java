package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "multiple_bet_line")
public class MultipleBetLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    private int idBet;

    @NotNull
    private double odd;

    @NotNull
    private int idGame;

    @NotNull
    private int type;


    public MultipleBetLine() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBet() {
        return idBet;
    }

    public void setIdBet(int idBet) {
        this.idBet = idBet;
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

    public MultipleBetLine(int idBet, double odd, int idGame) {
        this.idBet = idBet;
        this.odd = odd;
        this.idGame = idGame;
    }

}
