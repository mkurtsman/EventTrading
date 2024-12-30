package com.eventtrade.service.impl;

import com.eventtrade.model.calculation.InputParams;
import com.eventtrade.model.calculation.OutputNewsResult;
import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.service.CalculationService;

import java.time.ZonedDateTime;
import java.util.SortedMap;
import java.util.stream.Collector;

public class CalculationServiceImpl implements CalculationService {

    @Override
    public OutputNewsResult calculate(InputParams inputParams) {

        SortedMap<ZonedDateTime, Candle> beforeMap = inputParams.candles().subMap(inputParams.before(), inputParams.news().time());
        SortedMap<ZonedDateTime, Candle> afterMap = inputParams.candles().subMap(inputParams.news().time(), inputParams.after());

        return new OutputNewsResult(inputParams.news().positive(), getAggregatedCandle(beforeMap), getAggregatedCandle(afterMap));
    }

    private Candle getAggregatedCandle(SortedMap<ZonedDateTime, Candle> map){
        Candle candle = map.get(map.firstKey());
        Candle empty = new Candle(candle.time(), 0,0,0,0, candle.timeFrame());
        return map.values().stream().reduce(empty, Candle::add);
    }
}
