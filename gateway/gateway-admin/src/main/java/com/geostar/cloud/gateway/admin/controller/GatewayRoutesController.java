package com.geostar.cloud.gateway.admin.controller;

import com.geostar.cloud.common.core.vo.Result;
import com.geostar.cloud.gateway.admin.entity.DynamicVersion;
import com.geostar.cloud.gateway.admin.entity.GatewayRoutes;
import com.geostar.cloud.gateway.admin.service.IDynamicVersionService;
import com.geostar.cloud.gateway.admin.service.IGatewayRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@RestController
@RequestMapping("/gatewayRoutes")
public class GatewayRoutesController {

    @Autowired
    private IGatewayRoutesService iGatewayRoutesService;
    @Autowired
    private IDynamicVersionService iDynamicVersionService;

    @GetMapping("/routes")
    public GeoResponse<Object> routes() {
        try {
            List<GatewayRouteDefinition> routeDefinitions = iGatewayRoutesService.routes();
            return GeoResponse.success(routeDefinitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GeoResponse.defaultError();
    }

    @PostMapping("/add")
    public Result<Object> add(@ModelAttribute GatewayRoutes GatewayRoutes) {
        try {
            GatewayRoutes.setUpdateTime(new Date());
            if (GatewayRoutes.getId() == null) {
                GatewayRoutes.setCreateTime(new Date());
            }
            if (iGatewayRoutesService.insertOrUpdate(GatewayRoutes)) {
                RouteDefinition routeDefinition = dynamicRouteService.assembleRouteDefinition(GatewayRoutes);
                dynamicRouteService.update(routeDefinition);
                DynamicVersion dynamicVersion = new DynamicVersion();
                iDynamicVersionService.add(dynamicVersion);
                return Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    @PostMapping("/delete")
    public Result<Object> delete(long id) {
        try {
            GatewayRoutes GatewayRoutes = iGatewayRoutesService.selectById(id);
            GatewayRoutes.setUpdateTime(new Date());
            GatewayRoutes.setIsEbl(1);
            GatewayRoutes.setIsDel(1);
            if (iGatewayRoutesService.updateById(GatewayRoutes)) {
                dynamicRouteService.delete(GatewayRoutes.getRouteId());
                DynamicVersion dynamicVersion = new DynamicVersion();
                iDynamicVersionService.add(dynamicVersion);
                return Result.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

}
