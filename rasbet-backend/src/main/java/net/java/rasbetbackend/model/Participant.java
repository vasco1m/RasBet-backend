package net.java.rasbetbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "participant",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idGame")
        })
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idParticipant;
    @NotNull
    private int idGame;
    @NotBlank
    private String name;

    public Participant() {

    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Participant(int idGame, int idParticipant, String name) {
        this.idGame = idGame;
        this.idParticipant = idParticipant;
        this.name = name;
    }

}
