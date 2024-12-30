package com.eventtrade.model.news;

import java.time.ZonedDateTime;

public interface News {
    ZonedDateTime time();
    boolean positive();
    String value();
    String forecast();
    String prev();


}
