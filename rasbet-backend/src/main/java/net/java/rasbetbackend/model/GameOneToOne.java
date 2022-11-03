package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "gameOneToOne",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class GameOneToOne {
    @Id
    private int idGame;
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

    public GameOneToOne(int idGame, String tpA, String tpB) {
        this.idGame = idGame;
        this.tpA = tpA;
        this.tpB = tpB;
    }
}
