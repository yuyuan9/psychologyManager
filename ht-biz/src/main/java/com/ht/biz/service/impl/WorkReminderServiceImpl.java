package com.ht.biz.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.WorkReminderMapper;
import com.ht.biz.service.WorkReminderService;
import com.ht.entity.biz.msg.WorkReminder;
@Service("workReminderService")
public class WorkReminderServiceImpl extends ServiceImpl<WorkReminderMapper, WorkReminder> implements WorkReminderService {

}
