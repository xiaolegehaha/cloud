package com.geostar.cloud.gateway.admin.commander;

import com.geostar.cloud.gateway.admin.service.IGatewayRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  * spring boot 容器加载后自动监听
 *  
 */
@Component
public class MyCommandRunner implements CommandLineRunner {

    @Autowired
    private IGatewayRouteService iGatewayRouteService;

    @Override
    public void run(String... args) {
        //初始化路由
        iGatewayRouteService.overload();
    }
}
