package com.geostar.cloud.auth.authorization.provider;

import com.geostar.cloud.auth.authorization.entity.Role;
import com.geostar.cloud.auth.authorization.entity.User;
import com.geostar.cloud.common.core.vo.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrganizationFallbackFactory implements FallbackFactory<OrganizationProvider> {
    @Override
    public OrganizationProvider create(Throwable throwable) {
        throwable.printStackTrace();
        return new OrganizationProvider() {
            @Override
            public Result<User> getUserByUniqueId(String uniqueId) {
                return Result.success(new User());
            }

            @Override
            public Result<Set<Role>> queryRolesByUserId(String userId) {
                return Result.success(new HashSet<Role>());
            }
        };
    }
}
