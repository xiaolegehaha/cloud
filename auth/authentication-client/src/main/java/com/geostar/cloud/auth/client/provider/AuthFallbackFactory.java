package com.geostar.cloud.auth.client.provider;

import com.geostar.cloud.common.core.vo.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthFallbackFactory implements FallbackFactory<AuthProvider> {
    @Override
    public AuthProvider create(Throwable throwable) {
        throwable.printStackTrace();
        return new AuthProvider() {
            @Override
            public Result auth(String authentication, String url, String method) {
                return Result.fail(throwable.getMessage());
            }
        };
    }
}
