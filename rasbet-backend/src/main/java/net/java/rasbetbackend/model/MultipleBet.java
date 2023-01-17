package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "multiple_bet",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idBet")
        })
public class MultipleBet {

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


    public MultipleBet() {

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

    public MultipleBet(int nif, double value, BetState state) {
        this.nif = nif;
        this.value = value;
        this.dateTime = LocalDateTime.now();
        this.state = state;
    }
}
