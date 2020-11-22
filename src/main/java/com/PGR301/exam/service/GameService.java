package com.PGR301.exam.service;

import com.PGR301.exam.entity.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class GameService {

    @Autowired
    private EntityManager em;

    public boolean addGame(Game game) {
        String name = game.getName();
        String category = game.getCategory();
        int price = game.getPrice();

        if (em.find(Game.class, name) != null) {
            return false;
        }

        if (name.equals("") || category.equals("") || price < 0) {
            return false;
        }

        em.persist(game);
        return true;
    }

    public boolean deleteGame(String name) {
        Game game = getGame(name);

        if (game == null || !em.contains(game)) {
            return false;
        }

        em.remove(game);
        return true;
    }

    public Game getGame(String name) {
        if (name.equals("")) {
            return null;
        }

        return em.find(Game.class, name);
    }
}
