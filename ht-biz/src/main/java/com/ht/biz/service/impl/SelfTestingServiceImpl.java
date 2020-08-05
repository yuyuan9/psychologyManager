package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.SelfTestingMapper;
import com.ht.biz.service.SelfTestingService;
import com.ht.entity.biz.freeassess.SelfTesting;
@Service
public class SelfTestingServiceImpl extends ServiceImpl<SelfTestingMapper,SelfTesting>implements SelfTestingService {

}
