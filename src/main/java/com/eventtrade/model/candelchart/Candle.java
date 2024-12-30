package com.eventtrade.candelchart;

import java.time.ZonedDateTime;

import static java.lang.Math.max;
import static java.lang.Math.min;

public record Candle(ZonedDateTime time, double open, double maximum, double minimum, double close , TimeFrame timeFrame) {

    public Candle add(Candle c){
        return new Candle(
                time,
                open,
                max(this.maximum, c.maximum),
                min(this.minimum, c.minimum),
                close,
                timeFrame
        );
    }

    public boolean isBull(){
        return close > open;
    }

    public  boolean isBear(){
        return  !isBull();
    }



}
