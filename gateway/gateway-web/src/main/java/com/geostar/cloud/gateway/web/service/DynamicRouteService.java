package com.geostar.cloud.gateway.web.service;

import com.geostar.cloud.gateway.web.model.GatewayFilterDefinition;
import com.geostar.cloud.gateway.web.model.GatewayPredicateDefinition;
import com.geostar.cloud.gateway.web.model.GatewayRouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由服务
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;
    private RouteDefinitionLocator routeDefinitionLocator;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    //获取所有路由
    public Flux<RouteDefinition> getRouteDefinitions(){
        return routeDefinitionLocator.getRouteDefinitions();
    }

    //增加路由
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    //更新路由
    public String update(RouteDefinition definition) {
        try {
            delete(definition.getId());
        } catch (Exception e) {
            return "update fail,not find route  routeId: "+definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }
    }

    //删除路由
    public Mono<ResponseEntity<Object>> delete(String id) {
        return this.routeDefinitionWriter.delete(Mono.just(id)).then(Mono.defer(() -> {
            return Mono.just(ResponseEntity.ok().build());
        })).onErrorResume((t) -> {
            return t instanceof NotFoundException;
        }, (t) -> {
            return Mono.just(ResponseEntity.notFound().build());
        });
    }


    //把前端传递的参数转换成路由对象
    public RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gwdefinition.getId());
        definition.setOrder(gwdefinition.getOrder());

        //设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilters();
        for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if (gwdefinition.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
        } else {
            uri = URI.create(gwdefinition.getUri());
        }
        definition.setUri(uri);
        return definition;
    }

    //把前端传递的参数转换成路由对象
//    public RouteDefinition assembleRouteDefinition(GatewayRoutes gwdefinition) {
//        RouteDefinition definition = new RouteDefinition();
//        definition.setId(gwdefinition.getRouteId());
//        definition.setOrder(gwdefinition.getRouteOrder());
//
//        //设置断言
//        List<PredicateDefinition> pdList = new ArrayList<>();
//        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwdefinition.getPredicateDefinition();
//        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
//            PredicateDefinition predicate = new PredicateDefinition();
//            predicate.setArgs(gpDefinition.getArgs());
//            predicate.setName(gpDefinition.getName());
//            pdList.add(predicate);
//        }
//        definition.setPredicates(pdList);
//
//        //设置过滤器
//        List<FilterDefinition> filters = new ArrayList();
//        List<GatewayFilterDefinition> gatewayFilters = gwdefinition.getFilterDefinition();
//        for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
//            FilterDefinition filter = new FilterDefinition();
//            filter.setName(filterDefinition.getName());
//            filter.setArgs(filterDefinition.getArgs());
//            filters.add(filter);
//        }
//        definition.setFilters(filters);
//
//        URI uri = null;
//        if (gwdefinition.getRouteUri().startsWith("http")) {
//            uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getRouteUri()).build().toUri();
//        } else {
//            uri = URI.create(gwdefinition.getRouteUri());
//        }
//        definition.setUri(uri);
//        return definition;
//    }
}
