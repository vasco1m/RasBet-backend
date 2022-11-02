package net.java.rasbetbackend;

import net.java.rasbetbackend.controller.GamesController;
import org.springframework.beans.factory.annotation.Autowired;

public class APITimer implements Runnable {
    @Autowired
    GamesController update;

    @Override
    public void run() {
        while (true){
            try {
                update.updateGames();
                Thread.sleep(1000*60*5);
            } catch (InterruptedException e) {
                System.out.println("Deu erro update API.");
            }
        }

    }
}
