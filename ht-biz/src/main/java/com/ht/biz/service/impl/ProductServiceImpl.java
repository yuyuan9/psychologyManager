package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.ProductMapper;
import com.ht.biz.service.ProductService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Product;

import com.ht.entity.biz.product.productenum.ProductStaut;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Override
    public List<PageData> findstautnum(String userId) {
        return baseMapper.findstautnum(userId);
    }

    @Override
    public List<PageData> findallstautnum() {
        return baseMapper.findallstautnum();
    }

    @Override
    public List<PageData> sysfindByPage(MyPage page) {
        return baseMapper.sysfindByPage(page);
    }

    @Override
    public List<Product> promoteproduct(PageData pd) {
        return baseMapper.promoteproduct(pd);
    }

    @Override
    public List<PageData> getnumprolist(String createid) {
        return baseMapper.getnumprolist(createid);
    }

    @Override
    public List<Product> getprolistbyser(MyPage page) {
        return baseMapper.getprolistbyser(page);
    }

    @Override
    public List<PageData> getstautname(List<PageData> num) {
        for(PageData info : num ){
            if(StringUtils.isNotBlank( info.get( "staut" ).toString() )){
                info.put( "name",ProductStaut.getValue(Integer.parseInt( String.valueOf( info.get(  "staut") ))));
            }
        }
        return num ;
    }

    @Override
    public List<PageData> findpagepro(MyPage page) {
        return baseMapper.findpagepro(page);
    }

}
