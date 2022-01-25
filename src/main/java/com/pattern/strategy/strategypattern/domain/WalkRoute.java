package com.pattern.strategy.strategypattern.domain;

import org.springframework.stereotype.Component;

@Component
public class WalkRoute implements Route{

    @Override
    public String executeSearch() {
        // 보행과 관련한 알고리즘 적용
        return "WalkRoute";
    }

    @Override
    public RouteName getRouteName() {
        return RouteName.WalkRoute;
    }
}
