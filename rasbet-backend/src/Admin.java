package com.company;

import java.time.LocalDate;

public class Admin  extends User{
    public Admin(int nif, String username, String email, LocalDate bornDate) {
        super(nif, username, email, bornDate);
    }
}
