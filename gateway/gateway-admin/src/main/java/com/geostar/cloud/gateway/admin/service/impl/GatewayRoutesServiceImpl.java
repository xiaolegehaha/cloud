package com.geostar.cloud.gateway.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.geostar.cloud.common.core.util.RedisUtil;
import com.geostar.cloud.gateway.admin.dto.GatewayRouteDefinition;
import com.geostar.cloud.gateway.admin.entity.GatewayRoutes;
import com.geostar.cloud.gateway.admin.mapper.GatewayRoutesMapper;
import com.geostar.cloud.gateway.admin.service.IGatewayRoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@Service
public class GatewayRoutesServiceImpl extends ServiceImpl<GatewayRoutesMapper, GatewayRoutes> implements IGatewayRoutesService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<GatewayRouteDefinition> routes() {
        List<GatewayRoutes> list;
        String routes = (String) redisUtil.get(Constant.ROUTEKEY);
        if (StringUtils.isEmpty(routes)) {
            Wrapper<GatewayRoutes> wrapper = new EntityWrapper<GatewayRoutes>().eq(GatewayRoutes.getIs_ebl(), 0).and().eq(GatewayRoutes.getIs_del(), 0);
            list = selectList(wrapper);
        } else {
            list = JSONArray.parseArray(routes, GatewayRoutes.class);
        }
        List<GatewayRouteDefinition> routeDefinitions = new ArrayList<>();
        for(GatewayRoutes gatewayRoute : list){
            GatewayRouteDefinition routeDefinition = new GatewayRouteDefinition();
            routeDefinition.setId(gatewayRoute.getRouteId());
            routeDefinition.setUri(gatewayRoute.getRouteUri());
            routeDefinition.setFilters(gatewayRoute.getFilterDefinition());
            routeDefinition.setPredicates(gatewayRoute.getPredicateDefinition());
            routeDefinition.setOrder(gatewayRoute.getRouteOrder());
            routeDefinitions.add(routeDefinition);
        }
        return routeDefinitions;
    }
}
