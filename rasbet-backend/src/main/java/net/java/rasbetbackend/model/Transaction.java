package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idTransaction")
        })
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTransaction;
    @NotNull
    private int nif;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private TransactionType type;
    @NotNull
    private Double value;

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Transaction(){

    }

    public Transaction(int nif, TransactionType type, Double value) {
        this.nif = nif;
        this.date = LocalDateTime.now();
        this.type = type;
        this.value = value;
    }
}
