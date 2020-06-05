package com.geostar.cloud.gateway.web.commander;

import com.geostar.cloud.gateway.web.service.impl.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *  * spring boot 容器加载后自动监听
 *  
 */
@Component
public class MyCommandRunner implements CommandLineRunner {

    @Autowired
    private RouteService routeService;

    @Override
    public void run(String... args) {
        //初始化路由
        routeService.loadRouteDefinition();
    }
}
