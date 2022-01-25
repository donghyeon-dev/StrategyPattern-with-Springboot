package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class PublicRoute implements Route{

    @Override
    public String executeSearch() {
        return "Publicroute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.PublicRoute;
    }
}
