package net.java.rasbetbackend.payload.request;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;


@Service
@Component
public class IntRequest {
    @NotNull
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
