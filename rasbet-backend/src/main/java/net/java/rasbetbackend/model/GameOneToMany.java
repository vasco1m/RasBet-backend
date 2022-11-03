package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "gameOneToMany",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class GameOneToMany {
    @Id
    private int idGame;
    @NotBlank
    private String name;

    public GameOneToMany() {

    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public GameOneToMany(int idGame, String name, boolean draw) {
        this.idGame = idGame;
        this.name = name;
    }
}
