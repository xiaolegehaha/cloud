package com.geostar.cloud.auth.authorization.provider;

import com.geostar.cloud.auth.authorization.entity.Role;
import com.geostar.cloud.auth.authorization.entity.User;
import com.geostar.cloud.common.core.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "organization", fallbackFactory = OrganizationFallbackFactory.class)//这种方式可以捕获异常
//@FeignClient(name = "organization", fallback = OrganizationProviderFallback.class)
@Primary
public interface OrganizationProvider {

    @GetMapping(value = "/user")
    Result<User> getUserByUniqueId(@RequestParam("uniqueId") String uniqueId);

    @GetMapping(value = "/role/user/{userId}")
    Result<Set<Role>> queryRolesByUserId(@PathVariable("userId") String userId);
}
