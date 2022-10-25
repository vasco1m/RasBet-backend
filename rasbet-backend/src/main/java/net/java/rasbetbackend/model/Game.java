package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idGame;
    @NotNull
    private boolean type;
    @NotNull
    private int idCategory;

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

    public Game(int idGame, boolean type, int idCategory) {
        this.idGame = idGame;
        this.type = type;
        this.idCategory = idCategory;
    }
}
