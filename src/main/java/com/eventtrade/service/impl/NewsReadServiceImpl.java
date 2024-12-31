package com.eventtrade.service.impl;

import com.eventtrade.model.candelchart.Candle;
import com.eventtrade.model.news.News;
import com.eventtrade.model.news.NonFarmUSA;
import com.eventtrade.service.NewsReadService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.eventtrade.model.candelchart.Candle.*;
import static com.eventtrade.util.DateUtil.DATE_FORMATTER;
import static com.eventtrade.util.DateUtil.TIME_ZONE;

public class NewsReadServiceImpl implements NewsReadService {


    public static final String CSV_FILE_NAME = "src/main/resources/NonFarmUSA.csv";
    public static final String HTML_FILE_NAME = "src/main/resources/NonFarmUSA.mhtml";

    @Override
    public Collection<News> readHtml() throws IOException {
        Document document = Jsoup.parse(new File(HTML_FILE_NAME));
        System.out.println(document);
        Element element = document.selectFirst("table#eventHistoryTable227").selectFirst("tbody");
        Elements elements = element.select("tr");
        return elements
                .stream()
                .map(this::mapRow)
                .collect(Collectors.toUnmodifiableList());

    }

    private News mapRow(Element element) {
        Elements cells = element.select("td");
        String date = cells.get(0).text().substring(0, 10);
        String time = cells.get(1).text();
        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
        LocalTime localTime = LocalTime.parse(time);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDate, localTime, TIME_ZONE);
        boolean positive = cells.get(2).select(".redFont").stream().count() <= 0 ;

        News news = new NonFarmUSA(
            zonedDateTime,
            positive,
            cells.get(2).text(),
            cells.get(3).text(),
            cells.get(4).text()
        );

        return news;
    }

    @Override
    public void writeToCsv(Collection<News> news) {
        StringWriter sw = new StringWriter();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(TIME, POSITIVE, VALUE, FORECAST, PREV)
                .build();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            news.forEach(record -> {
                try {
                    printer.printRecord(record.time().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME), record.positive(), record.value(), record.forecast(), record.prev());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            FileUtils.writeStringToFile(new File(CSV_FILE_NAME), sw.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        NewsReadServiceImpl newsReadService = new NewsReadServiceImpl();
        newsReadService.writeToCsv(newsReadService.readHtml());
        System.out.println(newsReadService.readCsv());
    }



    @Override
    public Collection<News> readCsv() {
        try {
            Reader in = new FileReader(CSV_FILE_NAME);

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("time", "positive", Candle.VALUE, FORECAST, Candle.PREV)
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser records = csvFormat.parse(in);
            return records
                    .getRecords()
                    .stream()
                    .map(record ->
                         new NonFarmUSA(
                                ZonedDateTime.parse(record.get("time")),
                                Boolean.valueOf(record.get("positive")),
                                record.get(Candle.VALUE),
                                record.get(FORECAST),
                                record.get(Candle.PREV)

                    )).collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
