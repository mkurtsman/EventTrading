package com.eventtrade.service.impl;

import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.model.candelchart.TimeFrame;
import com.eventtrade.model.news.NonFarmUSA;
import com.eventtrade.service.CandleReadService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.eventtrade.model.candelchart.Candle.FORECAST;
import static com.eventtrade.model.candelchart.TimeFrame.H1;
import static com.eventtrade.util.DateUtil.*;

public class CandleReadServiceImpl implements CandleReadService {

    public static final String CSV_FILE_NAME = "src/main/resources/XAUUSD60.csv";

    @Override
    public Collection<Candle> readCsv() {
        try {
            Reader in = new FileReader(CSV_FILE_NAME);

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .build();

            CSVParser records = csvFormat.parse(in);


            return records
                    .getRecords()
                    .stream()
                    .map(record -> {

                        String date = record.get(0);
                        String time = record.get(1);
                        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER_C);
                        LocalTime localTime = LocalTime.parse(time);
                        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, TIME_ZONE);

                        return new Candle(
                                zonedDateTime,
                                Double.valueOf(record.get(2)),
                                Double.valueOf(record.get(3)),
                                Double.valueOf(record.get(4)),
                                Double.valueOf(record.get(5)),
                                H1);

                    }).collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        CandleReadServiceImpl newsReadService = new CandleReadServiceImpl();
        newsReadService.readCsv().stream().forEach(System.out::println);
    }
}
