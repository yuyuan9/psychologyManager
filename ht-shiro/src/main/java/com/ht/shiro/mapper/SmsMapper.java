package com.ht.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SendCode;
import com.ht.entity.shiro.SysUser;

import java.util.List;

/**
 * Created by Administrator on 2018/1/10.
 */
public interface SmsMapper extends BaseMapper<SendCode> {

    List<SendCode> findThirtymit(String phone);
}
