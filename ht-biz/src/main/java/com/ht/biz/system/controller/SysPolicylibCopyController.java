package com.ht.biz.system.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.HttpSolrService;
import com.ht.biz.service.PolicylibCopyService;
import com.ht.biz.service.PolicylibService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.solr.policylib.PolicylibCopy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/policylibcopy")
@Api(value="SysPolicylibController",description = "政策库后台重复数据管理")
public class SysPolicylibCopyController extends BaseController{
	
	@Autowired
	private PolicylibCopyService policylibCopyService;
	
	@Autowired
    private PolicylibService PolicylibService;
	
	@ApiOperation(value="政策库后台重复数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "projecname", value = "政策名称",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			QueryWrapper<PolicylibCopy> qw=new QueryWrapper<PolicylibCopy>();
			if(pd.get("projecname")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("projecname")))){
				qw.like("projecname", pd.get("projecname"));
			}
			policylibCopyService.page(page, qw);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="政策库后台重复数据查看接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "政策库数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id) throws Exception{
		Respon respon=this.getRespon();
		try {
			PolicylibCopy p=null;
			if(StringUtils.isNotBlank(id)){
				p=policylibCopyService.getById(id);
			}
			respon.success(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();respon
			.error(e);
		}
		return respon;
	}
	@ApiOperation(value="政策库后台重复数据删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据ids",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "forever", value = "是否同时清除政策库（true清除，false只在当前列表删除）",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids,Boolean forever) throws Exception{
		Respon respon=this.getRespon();
		try {
			if(forever==null){
				forever=true;
			}
			HttpSolrService httpSolrService=(HttpSolrService) SpringContextUtil.getBean("httpSolrService");
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(Const.solrUrl, Const.solrCore_policylib);
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					policylibCopyService.removeById(id);
					if(forever){
						PolicylibService.removeById(id);
						httpSolrClient.deleteById(id);
					}
				}
			}
			httpSolrService.commitAndCloseSolr(httpSolrClient);
			httpSolrService=null;
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
