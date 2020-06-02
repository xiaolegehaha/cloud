package com.geostar.cloud.gateway.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.geostar.gateway.dynamic.dto.GatewayRouteDefinition;
import com.geostar.gateway.dynamic.entity.GatewayRoutes;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
public interface IGatewayRoutesService extends IService<GatewayRoutes> {

    List<GatewayRouteDefinition> routes();
}
