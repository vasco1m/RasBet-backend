package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "odd",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idOdd")
        })
public class Odd {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idOdd;
    @NotNull
    private int idGame;
    @NotBlank
    private String house;
    @NotNull
    @Min(1)
    private double value;
    @NotNull
    private int type;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    public Odd() {

    }

    public int getIdOdd() {
        return idOdd;
    }

    public void setIdOdd(int idOdd) {
        this.idOdd = idOdd;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Odd(int idOdd, int idGame, String house, double value, int type, LocalDateTime date) {
        this.idOdd = idOdd;
        this.idGame = idGame;
        this.house = house;
        this.value = value;
        this.type = type;
        this.date = date;
    }
}
