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

        if (name.equals("") || category.equals("") || price == 0) {
            return false;
        }

        em.persist(game);
        return true;
    }

    public Game deleteGame(String name) {
        if (em.find(Game.class, name) == null) {
            return null;
        }

        if (name.equals("")) {
            return null;
        }

        Game gameToDelete = em.find(Game.class, name);
        em.remove(gameToDelete);
        return gameToDelete;
    }

    public Game getGame(String name) {
        if (name.equals("")) {
            return null;
        }

        return em.find(Game.class, name);
    }
}
