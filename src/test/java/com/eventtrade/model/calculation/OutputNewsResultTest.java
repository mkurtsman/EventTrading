package com.eventtrade.model.calculation;

import org.junit.jupiter.api.Test;

import static com.eventtrade.DataFactory.bearCandle;
import static com.eventtrade.DataFactory.bullCandle;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputNewsResultTest {

    @Test
    public void isContinuationTest(){
        assertTrue(new OutputNewsResult(true, bullCandle(), bullCandle()).continuation());
        assertTrue(new OutputNewsResult(true, bearCandle(), bearCandle()).continuation());
        assertFalse(new OutputNewsResult(true, bullCandle(), bearCandle()).continuation());
        assertFalse(new OutputNewsResult(true, bearCandle(), bullCandle()).continuation());
    }
}