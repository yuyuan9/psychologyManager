package com.ht.biz.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.UserbankMapper;
import com.ht.biz.service.UserbankService;
import com.ht.entity.biz.Userbank;
import org.springframework.stereotype.Service;


@Service("userbanKservice")
public class UserbankServiceImpl extends ServiceImpl<UserbankMapper, Userbank> implements UserbankService {



}
