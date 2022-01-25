package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class WalkRoute implements Route{

    @Override
    public String executeSearch() {
        return "WalkRoute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.WalkRoute;
    }
}
