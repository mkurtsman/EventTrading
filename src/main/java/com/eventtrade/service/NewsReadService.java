package com.eventtrade.service;

import com.eventtrade.model.news.News;

import java.io.IOException;
import java.util.Collection;

public interface NewsReadService {
    Collection<News> readHtml() throws IOException;
    void writeToCsv(Collection<News> news);
    Collection<News> readCsv();
}
