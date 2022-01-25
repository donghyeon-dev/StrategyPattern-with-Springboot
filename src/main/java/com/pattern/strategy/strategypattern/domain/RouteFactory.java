package com.pattern.strategy.strategypattern.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteFactory {
    // 모든 Route 전략을 주입
    private List<Route> routes;

    @Autowired
    public RouteFactory(List<Route> routeList){
        this.routes = routeList;
    };

    public Route findRoute(RouteName routeName){
        return routes.stream().filter(
                route -> route.getRouteName().equals(routeName)
        ).findAny().get();
    }

}
