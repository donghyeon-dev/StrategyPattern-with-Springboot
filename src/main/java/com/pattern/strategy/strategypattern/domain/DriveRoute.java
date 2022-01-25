package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class DriveRoute implements Route{

    @Override
    public String executeSearch() {
        return "DriveRoute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.DriveRoute;
    }
}
