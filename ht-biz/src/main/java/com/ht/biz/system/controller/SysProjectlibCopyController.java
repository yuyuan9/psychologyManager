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
import com.ht.biz.service.ProjectlibCopyService;
import com.ht.biz.service.ProjectlibService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.solr.projectlib.ProjectlibCopy;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/projectlibcopy")
@Api(value="SysProjectlibCopyController",description = "立项库后台重复数据管理")
public class SysProjectlibCopyController extends BaseController{
	@Autowired
	private ProjectlibCopyService projectlibCopyService;
	
	@Autowired
    private ProjectlibService projectlibService;
	
	@ApiOperation(value="政策库后台重复数据")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "companyName", value = "企业名称",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "name", value = "项目名称",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			QueryWrapper<ProjectlibCopy> qw=new QueryWrapper<ProjectlibCopy>();
			if(pd.get("companyName")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("projecname")))){
				qw.like("companyName", pd.get("companyName"));
			}
			if(pd.get("name")!=null&&StringUtils.isNotBlank(String.valueOf(pd.get("name")))){
				qw.like("name", pd.get("name"));
			}
			projectlibCopyService.page(page, qw);
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
			ProjectlibCopy p=null;
			if(StringUtils.isNotBlank(id)){
				p=projectlibCopyService.getById(id);
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
			HttpSolrClient httpSolrClient=httpSolrService.getHttpSolrClient(Const.solrUrl, Const.solrCore_projectlib);
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					projectlibCopyService.removeById(id);
					if(forever){
						projectlibService.removeById(id);
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
