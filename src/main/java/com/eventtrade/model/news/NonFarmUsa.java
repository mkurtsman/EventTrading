package com.eventtrade.model.news;

import java.time.ZonedDateTime;
import java.util.Objects;

public record NonFarmUSA(ZonedDateTime time, boolean positive, String value, String forecast, String prev) implements News{
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NonFarmUSA that = (NonFarmUSA) o;
        return positive == that.positive && Objects.equals(prev, that.prev) && Objects.equals(value, that.value) && Objects.equals(forecast, that.forecast) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, positive, value, forecast, prev);
    }
}
