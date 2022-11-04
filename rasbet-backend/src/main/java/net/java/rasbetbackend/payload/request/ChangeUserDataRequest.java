package net.java.rasbetbackend.payload.request;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Service
@Component
public class ChangeUserDataRequest {

    private String username;

    @Email
    private String email;

    private String password;

    @NotNull
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}