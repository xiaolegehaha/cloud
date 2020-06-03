package com.geostar.cloud.sysadmin.organization.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geostar.cloud.sysadmin.organization.dao.GroupMapper;
import com.geostar.cloud.sysadmin.organization.entity.param.GroupQueryParam;
import com.geostar.cloud.sysadmin.organization.entity.po.Group;
import com.geostar.cloud.sysadmin.organization.service.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Slf4j
public class GroupService extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Override
    public boolean add(Group group) {
        return this.save(group);
    }

    @Override
    @CacheInvalidate(name = "group::", key = "#id")
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @CacheUpdate(name = "group::", key = "#group.id", value = "#group")
    public boolean update(Group group) {
        return this.updateById(group);
    }

    @Override
    @Cached(name = "group::", key = "#id", cacheType = CacheType.REMOTE)
    public Group get(String id) {
        return this.getById(id);
    }

    @Override
    public List<Group> query(GroupQueryParam groupQueryParam) {
        QueryWrapper<Group> queryWrapper = groupQueryParam.build();
        queryWrapper.eq("name", groupQueryParam.getName());
        return this.list(queryWrapper);
    }

    @Override
    public List<Group> queryByParentId(String id) {
        return this.list(new QueryWrapper<Group>().eq("parent_id", id));
    }

}
