package com.PGR301.exam.services;


import com.PGR301.exam.entity.Game;
import com.PGR301.exam.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GameServiceTest {

    @Autowired
    public GameService gameService;

    @Autowired
    public ResetService resetService;

    @BeforeEach
    public void cleanDatabase() {
        resetService.resetDatabase();
    }

    @Test
    public void createGame() {
        String gameName = "Fifa 21";
        String category = "Sports";
        int price = 599;

        Game game = new Game(gameName, category, price);
        boolean success = gameService.addGame(game);

        assertTrue(success);

        Game retrievedGame = gameService.getGame(gameName);
        assertEquals(gameName, retrievedGame.getName());
        assertEquals(category, retrievedGame.getCategory());
    }

    @Test
    public void getNonExistingGame() {
        String gameName = "Call of Duty: Cold War";
        String category = "Shooter";
        int price = 599;

        Game game = gameService.getGame(gameName);
        assertNull(game);
    }

    @Test
    public void deleteGame() {
        String gameName = "Age of Empires III: Definitive Edition";
        String category = "RTS";
        int price = 199;

        // Create game and verify that it gets added successfully
        Game game = new Game(gameName, category, price);
        boolean success = gameService.addGame(game);
        assertTrue(success);

        // Verify more
        Game retrievedGame = gameService.getGame(gameName);
        assertEquals(gameName, retrievedGame.getName());
        assertEquals(category, retrievedGame.getCategory());

        // Delete game
        gameService.deleteGame(gameName);

        // Check if game is null
        Game retrievedGameAfterDeletion = gameService.getGame(gameName);
        assertNull(retrievedGameAfterDeletion);
    }
}
