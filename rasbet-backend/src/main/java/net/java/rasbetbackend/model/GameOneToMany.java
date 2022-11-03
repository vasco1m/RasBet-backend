package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "gameOneToMany",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class GameOneToMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGame;
    @NotBlank
    private String name;
    @NotNull
    private boolean draw;

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

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public GameOneToMany(int idGame, String name, boolean draw) {
        this.idGame = idGame;
        this.name = name;
        this.draw = draw;
    }
}
