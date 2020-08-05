package com.ht.biz.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.PoliProRecordService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.biz.solr.policylib.PoliProRecord;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/foreign")
@Api(value="ForeignController",description = "对外接口统一管理")
public class ForeignController extends BaseController{
	
	@Autowired
	private PoliProRecordService poliProRecordService;
	
	@ApiOperation(value="政策库接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "搜索关键字",  dataType = "String"),
	})
	@GetMapping("getPolicylib")
	public Respon getPolicylib(MyPage<Policylib> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			StringBuffer subq=new StringBuffer("");//solr查询条件q
			StringBuffer subfq=new StringBuffer("province:*上海* OR city:*上海*");//solr查询条件fq
			String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
			if(!StringUtils.isBlank(keyword)){
				keyword=String.valueOf(pd.get("keyword"));
				keyword=StringUtils.trim(keyword).replaceAll("\\s+|、","OR");
				String[] stris=keyword.split("OR");
				for(String s:stris){
					//String ks=RequestUtils.getParticiple(s, "*");
					String ks="*"+s+"*";
					if(subq.toString().equals("")){
						subq.append(String.format("(projecname:%s OR specialmum:%s OR managerway:%s OR supportobj:%s OR applyterm:%s)",ks,ks,ks,ks,ks));
					}else{
						subq.append(String.format(" AND (projecname:%s OR specialmum:%s OR managerway:%s OR supportobj:%s OR applyterm:%s)",ks,ks,ks,ks,ks));
					}
				}
			}else{
				subq.append("*:*");
			}
			page=getMyPage(pd);
			page=SolrUtils.getList(page, new Policylib(), true,Const.solrCore_policylib,subq.toString(),subfq.toString(),"year desc",keyword,true);
			PoliProRecord p=new PoliProRecord();
			p.setCounts(Double.valueOf(page.getTotal()));
			p.setIp(RequestUtils.getIp());
			p.setInterfaceName("/ht-biz/foreign/getPolicylib");
			p.setLocal("政策库");
			p.setCreatedate(new Date());
			//p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
			//p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
			poliProRecordService.save(p);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="立项库接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "搜索关键字",  dataType = "String"),
	})
	@GetMapping("getProjectlib")
	public Respon getProjectlib(MyPage<Projectlib> page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			StringBuffer subq=new StringBuffer("");//solr查询条件q
			StringBuffer subfq=new StringBuffer("province:*上海* OR city:*上海*");//solr查询条件fq
			String keyword=pd.get("keyword")==null?"":String.valueOf(pd.get("keyword"));
			if(!StringUtils.isBlank(keyword)){
				keyword=String.valueOf(pd.get("keyword"));
				keyword=StringUtils.trim(keyword).replaceAll("\\s+|、","OR");
				String[] stris=keyword.split("OR");
				for(String s:stris){
					//String ks=RequestUtils.getParticiple(s, "*");
					String ks="*"+s+"*";
					if(subq.toString().equals("")){
						subq.append(String.format("(companyName:%s OR name:%s OR type:%s OR special:%s)",ks,ks,ks,ks));
					}else{
						subq.append(String.format(" AND (companyName:%s OR name:%s OR type:%s OR special:%s)",ks,ks,ks,ks));
					}
				}
			}else{
				subq.append("*:*");
			}
			page=getMyPage(pd);
			page=SolrUtils.getList(page, new Projectlib(), true,Const.solrCore_projectlib,subq.toString(),subfq.toString(),"yearProject desc",keyword,true);
			PoliProRecord p=new PoliProRecord();
			p.setCounts(Double.valueOf(page.getTotal()));
			p.setIp(RequestUtils.getIp());
			p.setInterfaceName("/ht-biz/foreign/getProjectlib");
			p.setLocal("立项库");
			p.setCreatedate(new Date());
			//p.setCreateid(map2.get("userId")==null?"":String.valueOf(map2.get("userId")));
			//p.setRegionid(map2.get("companyid")==null?"":String.valueOf(map2.get("companyid")));
			poliProRecordService.save(p);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
}
