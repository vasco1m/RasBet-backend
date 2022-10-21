package com.company;

import java.time.LocalDate;

public class Apostador extends User{
    public Apostador(int nif, String username, String email, LocalDate bornDate) {
        super(nif, username, email, bornDate);
    }
}
