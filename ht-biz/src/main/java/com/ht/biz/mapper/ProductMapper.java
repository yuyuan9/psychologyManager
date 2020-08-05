package com.ht.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Product;

import java.util.List;

public interface ProductMapper  extends BaseMapper<Product> {
    List<PageData> findstautnum(String userId);

    List<PageData> findallstautnum();

    List<PageData> sysfindByPage(MyPage page);

    List<Product> promoteproduct(PageData pd);

    List<PageData> getnumprolist(String createid);

    List<Product> getprolistbyser(MyPage page);

    List<PageData> findpagepro(MyPage page);
}
