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
    @NotNull
    private int result;
    //1x1 : -1-> empate  0 -> hometeam   1-> awayteam
    //1xM :  pela ordem
    @NotNull
    private boolean draw;

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public Game(String idApi, boolean type, LocalDateTime dateTime, int idCategory) {
        this.apiID = idApi;
        this.type = type;
        this.dateTime = dateTime;
        this.idCategory = idCategory;
        this.draw = false;
    }
}
