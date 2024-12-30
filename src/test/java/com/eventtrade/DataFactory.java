package com.eventtrade;

import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.model.news.News;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.eventtrade.model.candelchart.TimeFrame.H1;
import static com.eventtrade.util.DateUtil.TIME_ZONE;
import static java.util.function.Function.identity;

abstract public class DataFactory {

    public static final ZonedDateTime DATE202402051600 = ZonedDateTime.of(2024, 2, 26, 16, 00, 0, 0, TIME_ZONE);
    public static final ZonedDateTime DATE202402051630 = ZonedDateTime.of(2024, 2, 26, 16, 30, 0, 0, TIME_ZONE);

    public static SortedMap<ZonedDateTime, Candle> createCandleRow() {
        Candle candle1 = new Candle(
                DATE202402051600,
                2.0,
                3.0,
                0.5,
                1.0,
                H1
        );

        Candle candle2 = new Candle(
                DATE202402051600.minus(1, ChronoUnit.HOURS),
                2.1,
                3.1,
                0.51,
                1.1,
                H1
        );

        Candle candle3 = new Candle(
                DATE202402051600.minus(2, ChronoUnit.HOURS),
                2.2,
                3.2,
                0.52,
                1.2,
                H1
        );

        Candle candle4 = new Candle(
                DATE202402051600.minus(3, ChronoUnit.HOURS),
                2.3,
                3.3,
                0.53,
                1.3,
                H1
        );

        Candle candle5 = new Candle(
                DATE202402051600.plus(1, ChronoUnit.HOURS),
                2.5,
                3.5,
                0.55,
                1.5,
                H1
        );

        Candle candle6 = new Candle(
                DATE202402051600.plus(2, ChronoUnit.HOURS),
                2.6,
                3.6,
                0.56,
                1.6,
                H1
        );

        Candle candle7 = new Candle(
                DATE202402051600.plus(3, ChronoUnit.HOURS),
                2.7,
                3.7,
                0.57,
                1.7,
                H1
        );

        return new TreeMap<>(Stream.of(candle1, candle2, candle3, candle4, candle5, candle6, candle7).collect(Collectors.toMap(Candle::time, identity())));
    }

    public static News getNews(){
        return new News() {
            @Override
            public ZonedDateTime time() {
                return DATE202402051630;
            }

            @Override
            public boolean positive() {
                return false;
            }

            @Override
            public String value() {
                return "";
            }

            @Override
            public String forecast() {
                return "";
            }

            @Override
            public String prev() {
                return "";
            }
        };
    }

    public static Candle bullCandle(){
        return new Candle(
                DATE202402051600.plus(3, ChronoUnit.HOURS),
                1.7,
                13.7,
                0.1,
                3.7,
                H1
        );
    }

    public static Candle bearCandle(){
        return new Candle(
                DATE202402051600.plus(3, ChronoUnit.HOURS),
                12.7,
                18.7,
                1.57,
                2.7,
                H1
        );
    }
}
