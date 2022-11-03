package net.java.rasbetbackend.payload.request;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotNull;


@Service
@Component
public class WalletRequest {
    @NotNull
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
