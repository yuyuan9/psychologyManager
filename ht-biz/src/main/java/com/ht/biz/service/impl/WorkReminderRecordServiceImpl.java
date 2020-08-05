package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.WorkReminderRecordMapper;
import com.ht.biz.service.WorkReminderRecordService;
import com.ht.entity.biz.msg.WorkReminderRecord;

@Service("workReminderRecordService")
public class WorkReminderRecordServiceImpl extends ServiceImpl<WorkReminderRecordMapper, WorkReminderRecord> implements WorkReminderRecordService {

}
