package net.java.rasbetbackend.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.java.rasbetbackend.model.BetState;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Service
@Component
public class BetRequest {

    @NotBlank
    private int idGame;

    @NotBlank
    private double odd;

    @NotNull
    private int type;


    @NotNull
    private double value;


    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
