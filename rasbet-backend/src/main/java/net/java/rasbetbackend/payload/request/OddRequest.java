package net.java.rasbetbackend.payload.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OddRequest {
    @NotNull
    private int idGame;
    @NotNull
    private int idSpecialist;
    @NotNull
    @Min(1)
    private double value;
    @NotNull
    private int type;

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIdSpecialist() {
        return idSpecialist;
    }

    public void setIdSpecialist(int idSpecialist) {
        this.idSpecialist = idSpecialist;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}