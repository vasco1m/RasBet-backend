package net.java.rasbetbackend.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Sale {

    public enum Sale_State{
        Concluded,
        Expired,
        Available
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idSale;
    @NotNull
    private int idGame;
    @NotNull
    private LocalDateTime validationTime;
    @NotNull
    private Sale_State state;

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public LocalDateTime getValidationTime() {
        return validationTime;
    }

    public void setValidationTime(LocalDateTime validationTime) {
        this.validationTime = validationTime;
    }

    public Sale_State getResult() {
        return this.state;
    }

    public void setResult(Sale_State state) {
        this.state = state;
    }

    public Sale(){

    }

    public Sale(int idGame,LocalDateTime validationTime,Sale_State state){
        this.idGame = idGame;
        this.validationTime=validationTime;
        this.state=state;
    }

}
