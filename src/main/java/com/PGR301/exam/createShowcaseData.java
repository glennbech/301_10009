package com.PGR301.exam;

import com.PGR301.exam.controller.GameController;
import com.PGR301.exam.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class createShowcaseData {

    @Autowired
    private GameController gameController;

    private String[] games = {"Fifa 21", "Counter-Strike: Global Offensive", "Dota 2", "League of Legends", "Among Us",
    "Destiny 2", "Rocket League", "Rust", "Apex Legends", "DayZ", "Grand Theft Auto V", "Tom Clancy's Rainbow Six Siege",
    "Football Manager 2020", "Garry's Mod", "Phasmophobia", "Minecraft", "Call of Duty: Black Ops Cold War",
    "NBA 2K21", "Fortnite", "Demon's Souls", "VALORANT", "World of Warcraft"};

    //@PostConstruct
    void start() throws InterruptedException {
        new Thread(() -> {
            try {
                insertData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Scheduled(fixedDelay = 5000)
    private void insertData() throws InterruptedException {
        Random random = new Random();
        double randomNum = Math.random() * 10000;

        String gameName = "showcase" + randomNum;
        String category = "Showcase";
        int price = random.nextInt(649 - 99) + 99;

        Game game = new Game(gameName, category, price);
        gameController.add(game);

        if (random.nextInt(10) <= 3) {
            gameController.delete(gameName);
        }
    }
}
