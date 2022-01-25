package com.pattern.strategy.strategypattern.service;

import com.pattern.strategy.strategypattern.domain.Route;
import com.pattern.strategy.strategypattern.domain.RouteFactory;
import com.pattern.strategy.strategypattern.domain.RouteInput;
import com.pattern.strategy.strategypattern.domain.RouteName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteFactory routeFactory;

    public String getRoute(RouteInput request){
        String routeStrategy = request.getStrategy();
        // 조건식을 덜 사용하기 위해 쓰는 패턴인데.. 예시니까..
        RouteName routeName = routeStrategy.equals("walk") ? RouteName.WalkRoute :
                routeStrategy.equals("drive")? RouteName.WalkRoute :
                        RouteName.PublicRoute;
        Route route = routeFactory.findRoute(routeName);
        return route.executeSearch();
    }
}
