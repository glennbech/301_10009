package com.PGR301.exam.exceptions;

public class GameError extends RuntimeException {
    public GameError(String message) {
        super(message);
    }
}