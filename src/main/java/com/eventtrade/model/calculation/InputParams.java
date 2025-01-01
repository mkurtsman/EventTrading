package com.eventtrade.model.calculation;

import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.model.news.News;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.SortedMap;

public record InputParams (SortedMap<ZonedDateTime, Candle> candles, News news, ZonedDateTime before, ZonedDateTime after, ChronoUnit chronoUnit){

    public InputParams(SortedMap<ZonedDateTime, Candle> candles, News news, int beforeCount, int afterCount, ChronoUnit chronoUnit) {
        this(candles, news, news.time().truncatedTo(chronoUnit).minus(beforeCount, chronoUnit), news.time().plus(afterCount+1, chronoUnit).truncatedTo(chronoUnit), chronoUnit);
    }

}
