package com.macro.mall.demo.fegin;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.demo.dto.OrderParam;
import com.macro.mall.demo.fegin.factory.FeignPortalFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "mall-portal", fallbackFactory = FeignPortalFactory.class)
public interface FeignPortalService {

    @PostMapping("/sso/login")
    CommonResult login(@RequestParam String username, @RequestParam String password);

    @GetMapping("/cart/list")
    CommonResult list();

    @PostMapping("/order/generateOrder")
    CommonResult generateOrder(@RequestBody OrderParam orderParam);
}
