package com.fly4j.yshop.ums.feign.factory;

import com.fly4j.yshop.ums.feign.UmsFeignClient;
import feign.hystrix.FallbackFactory;

public class UmsFeignClientFallbackFactory implements FallbackFactory<UmsFeignClient> {

    @Override
    public UmsFeignClient create(Throwable throwable) {
        return new UmsFeignClient() {
        };
    }
}