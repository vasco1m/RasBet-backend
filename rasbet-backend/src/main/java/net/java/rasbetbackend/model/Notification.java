package net.java.rasbetbackend.model;

import java.time.LocalDate;

public class Notification {
    private int idNotification;
    private int nif;
    private LocalDate date;
    private String title;
    private String description;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public Notification(int idNotification, int nif, LocalDate date, String title, String description) {
        this.idNotification = idNotification;
        this.nif = nif;
        this.date = date;
        this.title = title;
        this.description = description;
    }
}
