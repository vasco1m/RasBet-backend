package net.java.rasbetbackend;

import net.java.rasbetbackend.controller.GamesController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RasbetBackendApplication{

    public static void main(String[] args) {
        APITimer timer = new APITimer();
        Thread thread = new Thread(timer);
        thread.run();

        SpringApplication.run(RasbetBackendApplication.class, args);
    }


}
