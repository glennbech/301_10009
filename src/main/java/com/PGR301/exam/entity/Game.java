package com.PGR301.exam.entity;

import javax.persistence.*;

@Entity
@Table(name="games")
public class Game {

    @Id
    private String name;
    private String category;
    private Integer price;

    public Game() {

    }

    public Game(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Game name: " + name + ", category: " + category + ", price: " + price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
