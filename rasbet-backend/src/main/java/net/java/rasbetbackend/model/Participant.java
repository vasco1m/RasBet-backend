package net.java.rasbetbackend.model;

public class Participant {

    private int idGame;
    private int idParticipant;
    private String name;

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
