package net.java.rasbetbackend.payload.request;

import javax.validation.constraints.NotNull;

public class RoleRequest {
    @NotNull
    private int nif;

    @NotNull
    private String role;


    public int getNif() {
        return nif;
    }

    public void setNif(int nifTo) {
        this.nif = nifTo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
