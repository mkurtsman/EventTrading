package com.eventtrade.service.impl;

import com.eventtrade.model.calculation.InputParams;
import com.eventtrade.model.calculation.OutputNewsResult;
import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.model.news.News;
import com.eventtrade.service.CalculationService;
import com.eventtrade.service.CandleReadService;
import com.eventtrade.service.NewsReadService;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eventtrade.util.DateUtil.TIME_ZONE;
import static java.util.Objects.nonNull;

public class CalculationServiceImpl implements CalculationService {

    public static final ZonedDateTime DATE_FROM = ZonedDateTime.of(2014, 7, 7, 0, 0, 0, 0, TIME_ZONE);

    private final CandleReadService candleReadService;
    private final NewsReadService newsReadService;

    public CalculationServiceImpl(CandleReadService candleReadService, NewsReadService newsReadService) {
        this.candleReadService = candleReadService;
        this.newsReadService = newsReadService;
    }

    @Override
    public void calculate() {

        SortedMap<ZonedDateTime, Candle> candle = new TreeMap<>(candleReadService.readCsv().stream().collect(Collectors.toMap(Candle::time, Function.identity()))).tailMap(DATE_FROM);
        SortedMap<ZonedDateTime, News> news = new TreeMap<>(newsReadService.readCsv().stream().collect(Collectors.toMap(News::time, Function.identity()))).tailMap(DATE_FROM);

        List<InputParams> inputParamsList = news
                .entrySet()
                .stream()
                .map(n -> new InputParams(candle, n.getValue(), 4, 4, ChronoUnit.HOURS))
                .collect(Collectors.toUnmodifiableList());
        List<OutputNewsResult> outputNewsResults = inputParamsList.stream()
                .map(this::newsToOutputResult)
                .filter(o -> nonNull(o.startCandle())  && nonNull(o.endCandle()))
                .collect(Collectors.toUnmodifiableList());

        System.out.println("positive,continuation " + getCount(outputNewsResults, true, true));
        System.out.println("positive,reversal " + getCount(outputNewsResults, true, false));
        System.out.println("negative,continuation " + getCount(outputNewsResults, false, true));
        System.out.println("negative,reversal " + getCount(outputNewsResults, false, false));

    }

    private long getCount(List<OutputNewsResult> outputNewsResults, boolean positive, boolean continuation) {
        return outputNewsResults.stream().filter(p -> p.positiveNews() == positive && p.continuation() == continuation).count();
    }

    private OutputNewsResult newsToOutputResult(InputParams inputParams){

        SortedMap<ZonedDateTime, Candle> beforeMap = inputParams.candles().subMap(inputParams.before(), inputParams.news().time());
        SortedMap<ZonedDateTime, Candle> afterMap = inputParams.candles().subMap( inputParams.news().time().plus(1, inputParams.chronoUnit()), inputParams.after().plus(1, inputParams.chronoUnit()));

        return new OutputNewsResult(inputParams.news().positive(), getAggregatedCandle(beforeMap), getAggregatedCandle(afterMap));
    }


    private Candle getAggregatedCandle(SortedMap<ZonedDateTime, Candle> map){
        try {
            map = new TreeMap<>(map);
            Candle first = map.get(map.firstKey());
            map.remove(first.time());
            return map.values().stream().reduce(first, Candle::add);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        new CalculationServiceImpl(new CandleReadServiceImpl(), new NewsReadServiceImpl()).calculate();
    }
}
