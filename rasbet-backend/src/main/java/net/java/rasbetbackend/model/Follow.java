package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "follow")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotNull
    private Integer nif;

    @NotNull
    private Integer idGame;

    public Follow() {

    }

    public Follow(Integer nif, Integer idGame) {
        this.nif = nif; this.idGame=idGame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getNif() {
        return nif;
    }

    public void setNif(Integer nif) {
        this.nif = nif;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }
}
