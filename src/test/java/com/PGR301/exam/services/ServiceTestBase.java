package com.PGR301.exam.services;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTestBase {

    @Autowired
    public ResetService resetService;

    @BeforeEach
    public void cleanDatabase() {
        resetService.resetDatabase();
    }
}
