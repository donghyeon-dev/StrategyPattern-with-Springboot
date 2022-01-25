package com.pattern.strategy.strategypattern.service;

import com.pattern.strategy.strategypattern.domain.Route;
import com.pattern.strategy.strategypattern.domain.RouteFactory;
import com.pattern.strategy.strategypattern.domain.RouteInput;
import com.pattern.strategy.strategypattern.domain.RouteName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteFactory routeFactory;

    public String getRoute(RouteInput request){
        String routeStrategy = request.getStrategy();
        RouteName routeName = routeStrategy.equals("walk") ? RouteName.WalkRoute :
                routeStrategy.equals("drive")? RouteName.WalkRoute :
                        RouteName.PublicRoute;
        Route route = routeFactory.findRoute(routeName);
        return route.executeSearch();
    }
}
