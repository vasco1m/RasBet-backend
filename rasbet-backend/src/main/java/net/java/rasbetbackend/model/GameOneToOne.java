package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "gameOneToOne",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class GameOneToOne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGame;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    @NotNull
    private String tpA;
    @NotNull
    private String tpB;

    public GameOneToOne() {

    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public String getTpA() {
        return tpA;
    }

    public void setTpA(String tpA) {
        this.tpA = tpA;
    }

    public String getTpB() {
        return tpB;
    }

    public void setTpB(String tpB) {
        this.tpB = tpB;
    }

    public GameOneToOne(int idGame, LocalDateTime date, String tpA, String tpB) {
        this.idGame = idGame;
        this.date = date;
        this.tpA = tpA;
        this.tpB = tpB;
    }
}
