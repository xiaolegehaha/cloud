package com.geostar.cloud.gateway.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.geostar.gateway.dynamic.entity.DynamicVersion;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
public interface IDynamicVersionService extends IService<DynamicVersion> {

    long lastVersion();

    void add(DynamicVersion dynamicVersion) throws Exception;
}
