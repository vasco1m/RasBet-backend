package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idSale")
        })
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSale;
    @NotNull
    private int idGame;
    @NotNull
    private LocalDateTime validationTime;
    @NotNull
    private SaleState state;

    @NotNull
    private Double multiplier;

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

    public SaleState getResult() {
        return this.state;
    }

    public void setResult(SaleState state) {
        this.state = state;
    }


    public SaleState getState() {
        return state;
    }

    public void setState(SaleState state) {
        this.state = state;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Sale(){

    }

    public Sale(int idGame,LocalDateTime validationTime,SaleState state, Double multiplier){
        this.idGame = idGame;
        this.validationTime=validationTime;
        this.state=state;
        this.multiplier = multiplier;
    }

}
