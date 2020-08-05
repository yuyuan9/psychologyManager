package com.ht.biz.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ht.biz.mapper.PolicyPackageMapper;
import com.ht.biz.service.PolicyPackageSendService;
import com.ht.biz.service.PolicyPackageService;
import com.ht.commons.support.sms.SMSConfig;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyPackage;
import com.ht.entity.policydig.PolicyPackageSend;
import com.ht.utils.SendCodeUtils;
@Service
public class PolicyPackageServiceImpl extends ServiceImpl<PolicyPackageMapper, PolicyPackage> implements PolicyPackageService {

	@Override
	public List<PolicyPackage> findlistPage(MyPage<PolicyPackage> mypage) {
		// TODO Auto-generated method stub
		return baseMapper.findlistPage(mypage);
	}

	@Override
	public PolicyPackage findById(String id) {
		// TODO Auto-generated method stub
		return baseMapper.selectById(id);
	}

	@Override
	public void insert(PolicyPackage policyPackage) {
		// TODO Auto-generated method stub
		baseMapper.insert(policyPackage);
	}

	@Override
	public void edit(PolicyPackage policyPackage) {
		// TODO Auto-generated method stub
		baseMapper.updateById(policyPackage);
	}

	@Override
	public void deleted(String id) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(id);
	}
	//flag标记发送包是否全部发送成功（1全部发送成功0部分未成功）
	@Override
	public void sendMsg(List<PageData> list, PolicyPackage p, PolicyPackageSendService policyPackageSendService,int flag,Map<String ,Object > map) throws Exception {
		// TODO Auto-generated method stub
		for(PageData pds:list){
			PolicyPackageSend ps=new PolicyPackageSend();
			ps.setPackageId(p.getId());
			ps.setCustomerId(pds.getString("ciId"));
			ps.setSendDate(p.getSendTime());
			ps.setpTemplateId(SMSConfig.MSG_POLICY_PUSH_TEMPLATE);
			ps.setCreatedate(new Date());
			boolean resule=SendCodeUtils.sendMsg(p, pds, "smsService");
			if(resule){
				ps.setSendStatus(1);
			}else{
				ps.setSendStatus(0);
				flag=0;
			}
			if(map!=null){
				ps.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
				ps.setRegionid(map.get("regionId")==null?"":String.valueOf(map.get("regionId")));
			}
			PolicyPackageSend pp=policyPackageSendService.findByPidAndCid(ps);
			if(pp==null){
				policyPackageSendService.insert(ps);
			}else{
				ps.setId(pp.getId());
				policyPackageSendService.edit(ps);
			}
			
		}
		if(flag==1){
			p.setStatus(1);
			edit(p);
		}else{
			p.setStatus(2);
			edit(p);
		}
	}

}
