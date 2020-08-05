package com.ht.biz.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.KeyworkService;
import com.ht.biz.service.SolrSearchRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.msg.WorkReminder.Work;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.ht.utils.MsgUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/solr")
@Api(value="SysSolrSearchRecordController",description = "搜索关键字后台管理")
public class SysSolrSearchRecordController extends BaseController {
	
	@Autowired
	private SolrSearchRecordService solrSearchRecordService;
	
	@Autowired
    KeyworkService keyworkService;
	
	@ApiOperation(value="搜索关键字")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<SolrSearchRecord> page=getMyPage(pd);
			QueryWrapper<SolrSearchRecord> qw=new QueryWrapper<SolrSearchRecord>();
			String solrCore=pd.get("solrCore")==null?"":String.valueOf(pd.get("solrCore"));
			if(StringUtils.isNotBlank(solrCore)){
				qw.eq("solrCore", solrCore);
			}
			String status=pd.get("status")==null?"":String.valueOf(pd.get("status"));
			if(StringUtils.isNotBlank(status)){
				qw.eq("status", status);
			}
			qw.orderByDesc("status","createdate");
			page=(MyPage<SolrSearchRecord>) solrSearchRecordService.page(page,qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="搜索关键字明细")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		try {
			SolrSearchRecord gc=new SolrSearchRecord();
			if(StringUtils.isNotBlank(id)){
				gc=solrSearchRecordService.getById(id);
			}
			respon.success(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="搜索关键字保存")
	@ApiImplicitParams({
		//@ApiImplicitParam(paramType="query",name = "RewardRule", value = "数据id",  dataType = "string"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody SolrSearchRecord gc){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(gc.getId())){
				gc.setLastmodified(new Date());
				solrSearchRecordService.updateById(gc);
			}else{
				Map<String ,Object > map=getLoginInfo();
				if(map!=null){
					gc.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					gc.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				gc.setCreatedate(new Date());
				solrSearchRecordService.save(gc);
			}
			respon.success(gc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="搜索关键字删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					solrSearchRecordService.removeById(id);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="处理")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "highlight", value = "需要处理的关键字",  dataType = "string"),
	})
	@GetMapping("deal")
	public Respon deal(SolrSearchRecord gc){
		Respon respon=this.getRespon();
		try {
			Map<String ,Object > map=getLoginInfo();
			if(StringUtils.isNotBlank(gc.getHighlight())){
				List<String> list=solrSearchRecordService.selectUser(gc);
				for(String s:list){
					MsgUtil.addMsg(Work.deal_keyword.name(), null, s, String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
				}
				solrSearchRecordService.updateStatus(gc);
//				Keywork k=new Keywork();
//				k.setKeywordname(gc.getHighlight());
//				k.setCreatedate(new Date());
//				if(map!=null){
//					k.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
//				}
				//keyworkService.save(k);
			}
			respon.success(gc.getHighlight());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
