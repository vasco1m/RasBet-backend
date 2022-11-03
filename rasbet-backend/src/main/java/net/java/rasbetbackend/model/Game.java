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
    private String apiID;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
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

    public String getApiID() {
        return apiID;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public Game(String apiID, LocalDateTime dateTime, boolean type, int idCategory) {
        this.apiID = apiID;
        this.dateTime = dateTime;
        this.type = type;
        this.idCategory = idCategory;
    }
}
