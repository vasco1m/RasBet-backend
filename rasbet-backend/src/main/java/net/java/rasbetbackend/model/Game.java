package net.java.rasbetbackend.model;

public class Game {
    private int idGame;
    private boolean type;
    private int idCategory;

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public Game(int idGame, boolean type, int idCategory) {
        this.idGame = idGame;
        this.type = type;
        this.idCategory = idCategory;
    }
}
