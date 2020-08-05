package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.MatchRecordMapper;
import com.ht.biz.service.MatchRecordService;
import com.ht.entity.biz.policymatch.MatchRecord;
@Service
public class MatchRecordServiceImpl extends ServiceImpl<MatchRecordMapper, MatchRecord> implements MatchRecordService{

}
