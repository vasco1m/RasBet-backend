package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "game",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idGame;
    @NotNull
    private String apiID;
    @NotNull
    private boolean type;//true -> 1xM  false -> 1x1
    @NotNull
    private int idCategory;//Football -> 0

    public Game() {

    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Game(boolean type, int idCategory) {
        this.apiID = "";
        this.type = type;
        this.idCategory = idCategory;
    }

    public Game(String idApi, boolean type, int idCategory) {
        this.apiID = idApi;
        this.type = type;
        this.idCategory = idCategory;
    }
}
