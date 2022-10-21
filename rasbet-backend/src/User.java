package com.company;

import java.time.LocalDate;

public class User {
    private int nif;
    private String username;
    private String email;
    private LocalDate bornDate;

    public User (int nif, String username, String email, LocalDate bornDate){
        this.nif=nif;
        this.username=username;
        this.email=email;
        this.bornDate=bornDate;
    }
    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

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

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

}
