package com.eventtrade;

import com.eventtrade.model.news.NonFarmUSA;

import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        NonFarmUSA nonFarmUsa = new NonFarmUSA(ZonedDateTime.now(), true, "23", "234", "@34");
        System.out.println(nonFarmUsa.value());
    }

}