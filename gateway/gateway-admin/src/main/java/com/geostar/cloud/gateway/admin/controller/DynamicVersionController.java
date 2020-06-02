package com.geostar.cloud.gateway.admin.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.geostar.gateway.dynamic.entity.DynamicVersion;
import com.geostar.gateway.dynamic.service.IDynamicVersionService;
import com.geostar.gateway.model.GeoResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@RestController
@RequestMapping("/dynamicVersion")
public class DynamicVersionController {

    @Autowired
    private IDynamicVersionService iDynamicVersionService;

    @PostMapping("add")
    public GeoResponse<Object> add() {
        try {
            DynamicVersion dynamicVersion = new DynamicVersion();
            dynamicVersion.setCreateTime(new Date());
            iDynamicVersionService.add(dynamicVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GeoResponse.defaultError();
    }

    @GetMapping("/lastVersion")
    public GeoResponse<Object> lastVersion() {
        try {
            long versionId = iDynamicVersionService.lastVersion();
            return GeoResponse.success(versionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GeoResponse.defaultError();
    }

    @GetMapping("/list")
    public GeoResponse<Object> list(int pageNum, int size) {
        try {
            Page<DynamicVersion> page;
            if (StringUtils.isBlank(pageNum + "") || StringUtils.isBlank(size + "")) {
                page = new Page<DynamicVersion>(1, 10);
            } else {
                page = new Page<DynamicVersion>(pageNum, size);
            }
            Wrapper<DynamicVersion> wrapper = new EntityWrapper<DynamicVersion>().orderBy(DynamicVersion.getCreate_time(), false);
            Page<DynamicVersion> list = iDynamicVersionService.selectPage(page, wrapper);
            return GeoResponse.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GeoResponse.defaultError();
    }

}
