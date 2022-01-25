package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class PublicRoute implements Route{

    @Override
    public String executeSearch() {
        // 대중교통과 관련한 알고리즘 적용
        return "Publicroute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.PublicRoute;
    }
}
