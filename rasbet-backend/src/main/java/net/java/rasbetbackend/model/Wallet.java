package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wallet",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nif")
        })
public class Wallet {

    @Id
    private int nif;

    @NotNull
    @Min(0)
    private double budget;

    public Wallet() {

    }

    public Wallet(int nif, double budget) {
        this.nif = nif;
        this.budget = budget;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
}
