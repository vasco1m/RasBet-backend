package net.java.rasbetbackend.payload.request;

import net.java.rasbetbackend.model.MultipleBetLine;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Service
@Component
public class MultipleBetRequest {

    @NotNull
    private double value;

    @NotNull
    private Set<BetRequest> betRequests;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Set<BetRequest> getBetRequests() {
        return betRequests;
    }

    public void setBetRequests(Set<BetRequest> betRequests) {
        this.betRequests = betRequests;
    }
}
