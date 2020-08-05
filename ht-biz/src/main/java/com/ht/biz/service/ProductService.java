package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Product;

import java.util.List;

public interface ProductService  extends IService<Product> {

    //查询我创建商品的分类数据
    List<PageData> findstautnum(String userId);
    //查询商品总量分类
    List<PageData> findallstautnum();
   //后端产品列表
    List<PageData> sysfindByPage(MyPage page);
    // 查询推荐的商品
    List<Product> promoteproduct(PageData pd);
    //上线产品分类统计
    List<PageData> getnumprolist(String createid);
    //根据店铺查询商品
    List<Product> getprolistbyser(MyPage page);
   //重载类型条数
    List<PageData> getstautname(List<PageData> num);
    //前端产品类表展示
    List<PageData> findpagepro(MyPage page);
}
