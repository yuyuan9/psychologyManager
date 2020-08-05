package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.UnitMapper;
import com.ht.biz.service.UnitService;
import com.ht.entity.biz.unit.Unit;


@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService {

}
