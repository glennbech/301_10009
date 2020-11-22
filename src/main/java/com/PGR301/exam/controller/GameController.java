package com.PGR301.exam.controller;

import com.PGR301.exam.exceptions.GameError;
import com.PGR301.exam.entity.Game;
import com.PGR301.exam.service.GameService;
import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@RestController
public class GameController {

    private Gson gson = new Gson();
    private static final Logger LOG = Logger.getLogger(GameController.class.getName());
    private MeterRegistry meterRegistry;
    private int amountOfGames = 0;
    private AtomicInteger gauge;

    @Autowired
    private GameService gameService;

    @Autowired
    public GameController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        gauge = meterRegistry.gauge("games_owned", new AtomicInteger(0));
    }

    @RequestMapping(value = "/")
    @ResponseBody
    public String getErrorPath() {
        return "Hello";
    }

    // Retrieving a game
    @RequestMapping(value = "/api/games/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String name) {
        Game game = gameService.getGame(name);

        if (game != null) {
            LOG.info("Requested game. " + game.toString());
        } else {
            LOG.info("Game not found.");
            throw new GameError("Game not found");
        }

        return gson.toJson(game);
    }

    // Adding a game
    @RequestMapping(value = "/api/games/add", method = RequestMethod.POST)
    @Timed(value = "games_purchase_processtime")
    public String add(@RequestBody Game game) {
        boolean success = gameService.addGame(game);

        if (success) {
            LOG.info("Game was purchased. " + game.toString());
            handleMetrics(true, game.getPrice());
        } else {
            LOG.info("Game purchase not successful. " + game.toString());
            throw new GameError("Game purchase failed. The game is already owned.");
        }

        return gson.toJson(game);
    }

    // Deleting a game
    @RequestMapping(value = "/api/games/{name}/delete", method = RequestMethod.DELETE)
    @Timed(value = "games_sale_timetaken", longTask = true)
    public String delete(@PathVariable String name) throws InterruptedException {
        Game game = gameService.getGame(name);

        if (game == null) {
            LOG.info("Game sale not successful. Could not find game named: " + name);
            throw new GameError("Game not found");
        }

        // Random sleep up to 30 seconds
        double randomSaleTime = Math.random() * 30000;
        Thread.sleep((long) randomSaleTime);

        boolean success = gameService.deleteGame(game.getName());

        if (success) {
            LOG.info("Game found a buyer and was sold after " + Math.round(randomSaleTime/1000) + " seconds. " + game.toString());
            handleMetrics(false, 0);
        } else {
            LOG.info("Game sale unsuccessful. Could not find game named: " + name);
            throw new GameError("Game not found. It was likely sold already.");
        }

        return gson.toJson(game);
    }

    // Update metrics
    private void handleMetrics(boolean purchasedGame, int price) {
        if (purchasedGame) {
            meterRegistry.counter("games_purchased").increment();
            amountOfGames += 1;
            DistributionSummary.builder("games_prices").baseUnit("NOK").register(meterRegistry).record(price);
        } else {
            meterRegistry.counter("games_sold").increment();
            amountOfGames -= 1;
        }

        gauge.set(amountOfGames);
    }
}