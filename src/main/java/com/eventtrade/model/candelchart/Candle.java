package com.eventtrade.model.candelchart;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public record Candle(ZonedDateTime time, double open, double maximum, double minimum, double close , TimeFrame timeFrame) {

    public static final String TIME = "time";
    public static final String POSITIVE = "positive";
    public static final String VALUE = "value";
    public static final String FORECAST = "forecast";
    public static final String PREV = "prev";

    public Candle add(Candle c){
        return new Candle(
                time,
                open,
                max(this.maximum, c.maximum),
                min(this.minimum, c.minimum),
                c.close,
                timeFrame
        );
    }

    public boolean isBull(){
        return close > open;
    }

    public  boolean isBear(){
        return  !isBull();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Candle candle = (Candle) o;
        return Double.compare(open, candle.open) == 0 && Double.compare(close, candle.close) == 0 && Double.compare(maximum, candle.maximum) == 0 && Double.compare(minimum, candle.minimum) == 0 && Objects.equals(time, candle.time) && timeFrame == candle.timeFrame;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, open, maximum, minimum, close, timeFrame);
    }
}
