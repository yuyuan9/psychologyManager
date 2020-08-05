package com.ht.shiro.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.commons.utils.PageData;
import com.ht.entity.shiro.SendCode;
import com.ht.entity.shiro.SysUser;
import com.ht.shiro.mapper.SmsMapper;
import com.ht.shiro.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信发送记录sevice
 *
 */
@Service("smsService")
public class SmsServiceImpl extends ServiceImpl<SmsMapper, SendCode> implements SmsService {
    @Override
    public boolean findByCode(String code,String phone ) {
     /*   select * from
        sms_records
        where phone=#{phone} and is_available=0 and content=#{code}*/
        boolean flag = false;
        QueryWrapper<SendCode> ew = new QueryWrapper<SendCode>();
        ew.eq("content",code).eq("is_available",0  ).eq( phone,phone );
        SendCode sendCode =  baseMapper.selectOne( ew );
        if(null!=sendCode){
            flag =true;
        }
        return flag;
    }

    @Override
    public List<SendCode> findThirtymit(String phone) {
        return baseMapper.findThirtymit(phone);
    }


//	//新增
//	public void save(PageData pd)throws Exception{
//		baseMapper
//	}
//	//系统管理员群发短信（定时任务）绕过request和response
//	public void save2(PageData pd)throws Exception{
//		dao.save("SmsMapper.save", pd);
//	}
//
//
//	//列表
//	@SuppressWarnings("unchecked")
//	public List<PageData>  listPage(Page page) throws Exception {
//		return (List<PageData>) dao.findForList("SmsMapper.listPage", page);
//
//	}
//
//	//列表
//	@SuppressWarnings("unchecked")
//	public List<PageData>  smsList(PageData pd) throws Exception {
//		return (List<PageData>) dao.findForList("SmsMapper.smsList", pd);
//
//	}
//
//
//	//修改
//	public void edit(PageData pd)throws Exception{
//		dao.update("SmsMapper.edit", pd);
//	}
//
//	//记录未发送成功的内容
//	public void editSms(PageData pd)throws Exception{
//		dao.update("SmsMapper.editSms", pd);
//	}
//
//
//	/*
//	 * 根据code phone 查询 是否可以更改密码
//	 */
//	public Boolean findByCode(PageData pd)throws Exception{
//		PageData pageData=(PageData)dao.findForObject("SmsMapper.findByCode", pd);
//		if(pageData!=null){
//			return true;
//	    }else{
//			return false;
//		}
//	}
//
//	/*
//	 * 查询当前ip 一个ip只可以发送10条
//	 */
//	public Integer findCountByIp(String ip)throws Exception{
//		Integer count =(Integer)dao.findForObject("SmsMapper.findCountByIp", ip);
//		return count;
//	}
//	/*
//	 * 1个号码30分钟只允许发送一条
//	 */
//	public Integer findPhoneCount(String phone)throws Exception{
//		PageData pd = new PageData();
//		pd.add("endtime", DateUtil.get30MinuteBefore());
//		pd.add("phone", phone);
//		Integer count =(Integer)dao.findForObject("SmsMapper.findPhoneCount", pd);
//		return count;
//	}
	
}
