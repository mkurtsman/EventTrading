package com.eventtrade.service.impl;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class NewsReadServiceImplTest {

    @Test
    void readHtml() throws IOException {
        new NewsReadServiceImpl().readHtml();
    }

    @Test
    void writeToCsv() {
    }

    @Test
    void readCsv() {
    }
}