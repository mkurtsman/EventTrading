package com.eventtrade.news;

import java.time.ZonedDateTime;

public record NonFarmUsa(ZonedDateTime time,  boolean positive, String value, String forecast, String prev) implements News{
}
