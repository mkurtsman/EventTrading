package com.eventtrade.service;

import com.eventtrade.model.candelchart.Candle;

import java.util.Collection;

public interface CandleReadService {
    Collection<Candle> readCsv();
}
