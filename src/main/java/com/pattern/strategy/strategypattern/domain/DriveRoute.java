package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class DriveRoute implements Route{

    @Override
    public String executeSearch() {
        // 운전과 관련한 알고리즘 적용
        return "DriveRoute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.DriveRoute;
    }
}
