package com.ht.shiro.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SendCode;
import com.ht.entity.shiro.SysPermission;
import com.ht.shiro.mapper.SmsMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信发送记录sevice
 *
 */

public interface SmsService extends IService<SendCode> {



    boolean findByCode(String code ,String phone);
    List<SendCode> findThirtymit(String phone);
}
