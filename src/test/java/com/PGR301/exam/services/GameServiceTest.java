package com.PGR301.exam.services;


import com.PGR301.exam.entity.Game;
import com.PGR301.exam.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GameServiceTest extends ServiceTestBase {

    @Autowired
    public GameService gameService;

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
    public void failGetNonExistingGame() {
        String gameName = "Call of Duty: Cold War";

        Game game = gameService.getGame(gameName);
        assertNull(game);
    }

    @Test
    public void deleteGame() {
        Game game = new Game("Age of Empires III: Definitive Edition", "RTS", 199);
        gameService.addGame(game);
        assertNotNull(game);

        String gameName = game.getName();

        // Delete game
        boolean success = gameService.deleteGame(gameName);
        assertTrue(success);

        Game retrievedGameAfterDeletion = gameService.getGame(gameName);
        assertNull(retrievedGameAfterDeletion);
    }
}
