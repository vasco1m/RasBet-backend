package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bet",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idBet")
        })
public class Bet {


    public enum Bet_State{
        Concluded,
        Done,
        Waiting_Payment,
        Canceled;
    }
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
    private Bet_State state;

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

    public Bet_State getState() {
        return state;
    }

    public void setState(Bet_State state) {
        this.state = state;
    }

    public Bet(int idBet, int nif, double value, LocalDateTime dateTime,Bet_State state) {
        this.idBet = idBet;
        this.nif = nif;
        this.value = value;
        this.dateTime = dateTime;
        this.state = state;
    }
}
