package com.ht.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Product;
import com.ht.entity.biz.product.ProductType;
import com.ht.entity.biz.product.ProductVo;


public interface ProductTypeService extends IService<ProductType> {

  ProductVo getProductvo(Product product);
  void getPageDatevo(PageData pageData);
}
