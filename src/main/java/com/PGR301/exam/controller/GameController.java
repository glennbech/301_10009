package com.PGR301.exam.controller;

import com.PGR301.exam.entity.Game;
import com.PGR301.exam.service.GameService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class GameController {

    Gson gson = new Gson();

    @Autowired
    private GameService gameService;

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("name") String name) {
        Game game = gameService.getGame(name);
        return gson.toJson(game);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Game game) {
        boolean success = gameService.addGame(game);
        return gson.toJson(game);
    }

    @RequestMapping(value = "/games/delete", method = RequestMethod.DELETE)
    public String delete(@PathParam("name") String name) {
        Game game = gameService.deleteGame(name);
        return gson.toJson(game);
    }
}
