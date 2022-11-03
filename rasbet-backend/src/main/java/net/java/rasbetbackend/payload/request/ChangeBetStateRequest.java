package net.java.rasbetbackend.payload.request;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@Component
public class ChangeBetStateRequest {
    @NotBlank
    private String state;
    @NotNull
    private int idBet;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIdBet() {
        return idBet;
    }

    public void setIdBet(int idBet) {
        this.idBet = idBet;
    }
}
