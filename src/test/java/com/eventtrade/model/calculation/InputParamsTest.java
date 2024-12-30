package com.eventtrade.model.calculation;

import org.junit.jupiter.api.Test;

import static com.eventtrade.DataFactory.*;
import static java.time.temporal.ChronoUnit.HOURS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InputParamsTest {

    @Test
    void testRangeDates(){
        InputParams params = new InputParams(createCandleRow(), getNews(), 2, 3, HOURS);
        assertEquals(params.before(), DATE202402051600.minus(2, HOURS));
        assertEquals(params.after(), DATE202402051600.plus(4, HOURS));
    }

}