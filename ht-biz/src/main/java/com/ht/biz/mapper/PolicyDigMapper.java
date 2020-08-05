package com.ht.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;

public interface PolicyDigMapper extends BaseMapper<PolicyDig>{
	List<PolicyDig> findlistPage(MyPage<PolicyDig> mypage);
	List<PolicyDig> findV3listPage(MyPage<PolicyDig> mypage);
	List<PolicyDig> findlist(MyPage<PolicyDig> mypage);
	void updateStatus(PageData pd);
	void updateTop(PolicyDig policyDig);
	List<PolicyDig> subscribeList(MyPage<PolicyDig> mypage);
	List<PolicyDig> searchlist(MyPage<PolicyDig> mypage);

    List<GeelinkPolicyDig> geelinkfindall();
    
    public List<PolicyDig> centerlist(MyPage<PolicyDig> mypage);
    public List<PolicyDig> appnewlist(MyPage<PolicyDig> mypage);
}
