package com.eventtrade.model.candelchart;

import org.junit.jupiter.api.Test;

import static com.eventtrade.DataFactory.bearCandle;
import static com.eventtrade.DataFactory.bullCandle;
import static org.junit.jupiter.api.Assertions.*;

class CandleTest {

    @Test
    void add() {
        Candle result = bearCandle().add(bullCandle());
        assertEquals(result.maximum(), 18.7);
        assertEquals(result.minimum(), 0.1);
        assertEquals(result.open(), 12.7);
        assertEquals(result.close(), 3.7);
    }

    @Test
    void isBull() {
        assertTrue(bullCandle().isBull());
    }

    @Test
    void isBear() {
        assertTrue(bearCandle().isBear());
    }
}