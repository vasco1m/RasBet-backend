package net.java.rasbetbackend;

import net.java.rasbetbackend.controller.GamesController;
import org.springframework.beans.factory.annotation.Autowired;

public class APITimer implements Runnable {
    private GamesController gamesController = new GamesController();

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("Will update the games now!");
                gamesController.updateGames();
                System.out.println("Games updated!");
                Thread.sleep(1000*60*5);
            } catch (InterruptedException e) {
                System.out.println("Deu erro update API.");
            }
        }

    }
}
