package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.CatalogMapper;
import com.ht.biz.service.CatalogService;
import com.ht.entity.biz.catalog.Catalog;
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {

}
