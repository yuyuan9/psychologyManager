package com.ht.biz.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;

public interface PolicyDigService extends IService<PolicyDig>{
	PolicyDig findById(String id);
	List<PolicyDig> findlistPage(MyPage<PolicyDig> mypage);
	void insert(PolicyDig policyDig);
	void edit(PolicyDig policyDig);
	void deleted(PolicyDig policyDig);
	public void deleteById(String id);
	List<PolicyDig> findV3listPage(MyPage<PolicyDig> mypage);
	void updateStatus(PageData pd);
	void updateTop(PolicyDig policyDig);
	List<PolicyDig> findlist(MyPage<PolicyDig> mypage);
	List<PolicyDig> subscribeList(MyPage<PolicyDig> mypage);
	public void getCookies(PageData pd,HttpServletRequest request,String memory,HttpServletResponse response);
	List<PolicyDig> searchlist(MyPage<PolicyDig> mypage);

	List<GeelinkPolicyDig> geelinkfindall();
	
	public String getPcStr(String regions,String province,String city,String area,String countrys,String provinces,String citys,String areas);
	public String getSpStr(String province,String city,String area);
	public String getphoneStr(String province,String city,String area);
	
	public List<PolicyDig> centerlist(MyPage<PolicyDig> mypage);
	public List<PolicyDig> appnewlist(MyPage<PolicyDig> mypage);
}
