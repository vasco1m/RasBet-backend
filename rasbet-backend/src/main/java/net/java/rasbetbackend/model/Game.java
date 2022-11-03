package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    private boolean type;
    @NotNull
    private int idCategory;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Game(int idGame, boolean type, int idCategory) {
        this.idGame = idGame;
        this.type = type;
        this.idCategory = idCategory;
        this.dateTime = LocalDateTime.now();
    }
}
