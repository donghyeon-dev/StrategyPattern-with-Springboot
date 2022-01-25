package com.pattern.strategy.strategypattern.controller;

import com.pattern.strategy.strategypattern.domain.RouteInput;
import com.pattern.strategy.strategypattern.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @RequestMapping("/route")
    public String getRoute(@RequestBody RouteInput request) {
        return routeService.getRoute(request);
    }
}
