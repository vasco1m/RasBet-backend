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

    private int idParticipant;

    @NotNull
    private BetState state;

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

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }


    public BetState getSate() {
        return state;
    }

    public void setState(BetState state) {
        this.state = state;
    }


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
