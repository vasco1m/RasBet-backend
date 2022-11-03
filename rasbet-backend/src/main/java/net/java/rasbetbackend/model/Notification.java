package net.java.rasbetbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idNotification")
        })
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idNotification;
    @NotNull
    private int nif;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    public Notification() {

    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Notification(int nif, String title, String description) {
        this.nif = nif;
        this.date = LocalDateTime.now();
        this.title = title;
        this.description = description;
    }
}
