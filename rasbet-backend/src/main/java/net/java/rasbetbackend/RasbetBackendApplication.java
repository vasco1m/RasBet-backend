package net.java.rasbetbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RasbetBackendApplication{

    public static void main(String[] args) {
        SpringApplication.run(RasbetBackendApplication.class, args);
    }


}

@RestController
class HelloController{
    @GetMapping("/")
    String hello() {
        return "Welcome to RASBET!";
    }
}
