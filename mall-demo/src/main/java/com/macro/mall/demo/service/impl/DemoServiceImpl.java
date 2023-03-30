package com.macro.mall.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.macro.mall.demo.dto.OrderParam;
import com.macro.mall.demo.dto.PmsBrandDto;
import com.macro.mall.demo.fegin.FeignPortalService;
import com.macro.mall.demo.service.DemoService;
import com.macro.mall.mapper.PmsBrandMapper;
import com.macro.mall.model.PmsBrand;
import com.macro.mall.model.PmsBrandExample;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * DemoService实现类
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private FeignPortalService portalService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public int createBrand(PmsBrandDto pmsBrandDto) {

        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandDto,pmsBrand);
        brandMapper.insertSelective(pmsBrand);

        OrderParam orderParam = new OrderParam();
        List<Long> cartIds = new ArrayList<Long>();
        cartIds.add(1L);
        cartIds.add(2L);
        orderParam.setCartIds(cartIds);
        orderParam.setPayType(1);
        orderParam.setCouponId(1L);
        orderParam.setUseIntegration(1);
        orderParam.setMemberReceiveAddressId(1L);
        System.out.println("XID######"+ RootContext.getXID());
        portalService.generateOrder(orderParam);

//        String url = "http://localhost:8085/order/generateOrder";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String content = JSON.toJSONString(orderParam);
//        HttpEntity<String> request = new HttpEntity<>(content, headers);
//
//        restTemplate.postForEntity(url,request, String.class);


        return 1;
    }

    @Override
    public int updateBrand(Long id, PmsBrandDto pmsBrandDto) {
        PmsBrand pmsBrand = new PmsBrand();
        BeanUtils.copyProperties(pmsBrandDto,pmsBrand);
        pmsBrand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(pmsBrand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
