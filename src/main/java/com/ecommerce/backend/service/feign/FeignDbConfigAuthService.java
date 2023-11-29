package com.ecommerce.backend.service.feign;


import com.ecommerce.backend.config.CustomFeignConfig;
import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.dto.dbConfig.DbConfigDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dbconfig-svr", url = "${auth.internal.base.url}", configuration = CustomFeignConfig.class)
public interface FeignDbConfigAuthService {

    @GetMapping(value = "/v1/db-config/get/{storeId}")
    ApiMessageDto<DbConfigDto> authGetByStoreId(@PathVariable("storeId") Long id);

    @GetMapping(value = "/v1/db-config/get_by_name")
    ApiMessageDto<DbConfigDto> authGetByName(@RequestParam(value = "name") String name);
}
