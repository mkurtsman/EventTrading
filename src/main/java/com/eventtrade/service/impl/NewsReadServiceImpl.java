package com.eventtrade.service.impl;

import com.eventtrade.model.news.News;
import com.eventtrade.model.news.NonFarmUSA;
import com.eventtrade.service.NewsReadService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.eventtrade.util.DateUtil.DATE_FORMATTER;
import static com.eventtrade.util.DateUtil.TIME_ZONE;

public class NewsReadServiceImpl implements NewsReadService {


    @Override
    public Collection<News> readHtml() throws IOException {
        Document document = Jsoup.parse(new File("src/main/resources/NonFarmUSA.mhtml"));
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
    public Collection<News> writeToCsv() {
        return List.of();
    }

    @Override
    public Collection<News> readCsv() {
        return List.of();
    }
}
