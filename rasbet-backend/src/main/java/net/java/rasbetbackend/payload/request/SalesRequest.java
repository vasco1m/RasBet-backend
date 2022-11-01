package net.java.rasbetbackend.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class SalesRequest {
    @NotBlank
    private int idGame;
    @NotBlank
    private LocalDateTime validationTime;


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
}
