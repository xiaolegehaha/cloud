package com.geostar.cloud.gateway.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.geostar.cloud.common.core.util.RedisUtil;
import com.geostar.cloud.gateway.admin.entity.DynamicVersion;
import com.geostar.cloud.gateway.admin.entity.GatewayRoutes;
import com.geostar.cloud.gateway.admin.mapper.DynamicVersionMapper;
import com.geostar.cloud.gateway.admin.service.IDynamicVersionService;
import com.geostar.cloud.gateway.admin.service.IGatewayRoutesService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@Service
public class DynamicVersionServiceImpl extends ServiceImpl<DynamicVersionMapper, DynamicVersion> implements IDynamicVersionService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    IGatewayRoutesService iGatewayRoutesService;

    @Override
    public void add(DynamicVersion dynamicVersion) throws Exception{
        dynamicVersion.setCreateTime(new Date());
        try{
            if (insertOrUpdate(dynamicVersion)) {
                redisUtil.set(Constant.VERSIONKEY, dynamicVersion.getId(), Constant.ExpireTime);
                Wrapper<GatewayRoutes> wrapper = new EntityWrapper<GatewayRoutes>().eq(GatewayRoutes.getIs_ebl(), 0).and().eq(GatewayRoutes.getIs_del(), 0);
                redisUtil.set(Constant.ROUTEKEY, JSON.toJSONString(iGatewayRoutesService.selectList(wrapper)), Constant.ExpireTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public long lastVersion() {
        long versionId = 0;
        Object obj = redisUtil.get(Constant.VERSIONKEY);
        if(null != obj){
            versionId = Long.parseLong(obj.toString());
        }else {
            Page<DynamicVersion> page = new Page<DynamicVersion>(1, 1);
            Wrapper<DynamicVersion> wrapper = new EntityWrapper<DynamicVersion>().orderBy(DynamicVersion.getCreate_time(), false);
            Page<DynamicVersion> list = selectPage(page, wrapper);
            if (list.getSize() > 0) {
                versionId = list.getRecords().get(0).getId();
                redisUtil.set(Constant.VERSIONKEY, versionId, Constant.ExpireTime);
            }
        }
        return versionId;
    }
}
