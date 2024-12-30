package com.eventtrade.model.calculation;

import com.eventtrade.model.candelchart.Candle;

public record OutputNewsResult (boolean positiveNews, Candle startCandle, Candle endCandle) {

    public boolean isContinuation(){
        return startCandle.isBull() ^ endCandle.isBear();
    }
}