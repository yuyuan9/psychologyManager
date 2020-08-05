package com.ht.biz.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ht.biz.service.CustomerInfoService;
import com.ht.biz.service.CustomerRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.policydig.CustomerInfo;
import com.ht.entity.policydig.CustomerRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/sys/ht-biz/syscust")
@Api(value="SysCustomerInfoController",description = "客户信息后台管理")
public class SysCustomerInfoController extends BaseController{
	
	@Autowired
    private CustomerInfoService customerInfoService;
	
	@Autowired
    private CustomerRecordService customerRecordService;
	
	public static final String strings[]={"客户名称","统一信用代码","联系人",
			"手机号码","QQ号","微信号",
			"联系邮箱","省份","市","区","技术领域","行业"};
	
	@ApiOperation(value="后台客户信息列表接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "name", value = "政策类型", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "field", value = "来源", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "total", value = "查询总数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页条数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "pages", value = "总页数", dataType = "int"),
	})
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon = this.getRespon();
		try {
			MyPage page=getMyPage(pd);
			page.setPd(pd);
			page.setRecords(customerInfoService.findlistPage(page));
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台单条客户信息查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "客户信息id" ,dataType = "String"),
	})
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public Respon edit(String id){
		Respon respon = this.getRespon();
		CustomerInfo customerInfo=new CustomerInfo();
		try {
			if(!StringUtils.isBlank(id)){
				customerInfo=customerInfoService.findById(id);
			}
			respon.success(customerInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台单条客户信息保存接口")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "customerInfo", value = "客户信息customerInfo" ,dataType = "CustomerInfo"),
	})
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Respon save(@RequestBody CustomerInfo customerInfo){
		Respon respon = this.getRespon();
		try {
			if(StringUtils.isBlank(customerInfo.getId())){
				customerInfo.setCreatedate(new Date());
				customerInfo.setCreateid(getLoginInfo().get("userId")==null?"":String.valueOf(getLoginInfo().get("userId")));
				customerInfo.setRegionid(getLoginInfo().get("companyid")==null?"":String.valueOf(getLoginInfo().get("companyid")));
				customerInfo.setDeleted("0");
				customerInfoService.insert(customerInfo);
			}else{
				customerInfo.setLastmodified(new Date());
				customerInfoService.edit(customerInfo);
			}
			respon.success(customerInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台单条客户信息删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "客户信息id" ,dataType = "String"),
	})
	@RequestMapping(value="/deleted",method=RequestMethod.GET)
	public Respon deleted(String id){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(id)){
				customerInfoService.deleteById(id);
			}
			respon.success(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	
	@ApiOperation(value="后台批量客户信息删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "要删除的客户id集合" ,dataType = "String"),
	})
	@RequestMapping(value="/deletedAll",method=RequestMethod.GET)
	public Respon deletedAll(String ids){
		Respon respon = this.getRespon();
		try {
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					customerInfoService.deleteById(id);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			respon.error();
		}
		return respon;
	}
	@ApiOperation(value="后台导出客户信息接口(也可以下载模板)")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "total", value = "导出总数（由查询条件决定）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "size", value = "导出条数（由客户决定）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "suffix", value = "导出后缀名（.xls或者.xlsx，默认.xls）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "fileName", value = "导出文件名（若用户不指定以日期命名，默认.xls）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "template", value = "传递该参数（任意值）表示下载模板并非导出数据", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "total", value = "导出总数（由查询条件决定）", dataType = "int"),
	})
	@RequestMapping(value="/export",method=RequestMethod.GET)
	public void export(HttpServletResponse response,MyPage<CustomerInfo> page,String suffix,String fileName,String template){
		PageData pd = this.getPageData();
		List<CustomerInfo> list=null;
		try {
			if(StringUtils.isBlank(template)){
				page.setPd(pd);
				list=customerInfoService.findlistPage(page);
			}
			//new CustomerInfo()为具体的实现类（用于反射字段）
			//list导出的数据集合
			//strings导出数据第二行
			//"客户信息管理"为第一个工作表的名称，根据实际情况更改
			Excel.excelExport(response, list,new CustomerInfo(), suffix, strings, fileName,"客户信息管理");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	@ApiOperation(value="后台导入客户信息接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "total", value = "导入总数（由查询条件决定）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "size", value = "导入条数（由客户决定）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "suffix", value = "导入后缀名（.xls或者.xlsx，默认.xls）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "fileName", value = "导入文件名（若用户不指定以日期命名，默认.xls）", dataType = "String"),
	})
	@RequestMapping(value="/import",method=RequestMethod.POST)
	public Respon imports(HttpServletRequest request, HttpServletResponse response){
		Respon respon = this.getRespon();
		List<CustomerInfo> list=null;
		CustomerRecord customerRecord=new CustomerRecord();
		int i=0,k=0,m=1;
		try {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = mRequest.getFile("file");
			if(multipartFile!=null){
				respon=Excel.excelImport(multipartFile, new CustomerInfo(),1,getLoginInfo());
				list=(List<CustomerInfo>) respon.getData();
				if(list!=null&&list.size()>0&&list.size()<2001){
					customerRecord.setId(UuidUtil.get32UUID());
					customerRecord.setFileName(multipartFile.getOriginalFilename());
					customerRecord.setCounts(list.size());
					customerRecord.setCreatedate(new Date());
					Map<String ,Object > map=getLoginInfo();
					if(map!=null){
						customerRecord.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
						customerRecord.setUplaodPerson(map.get("userId")==null?"":String.valueOf(map.get("userId")));
						customerRecord.setPhone(map.get("phone")==null?"":String.valueOf(map.get("phone")));
						customerRecord.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
						String regprovince=map.get("regprovince")==null?"":String.valueOf(map.get("regprovince"));
						String regcity=map.get("regcity")==null?"":String.valueOf(map.get("regcity"));
						String regarec=map.get("regarec")==null?"":String.valueOf(map.get("regarec"));
						customerRecord.setAddr(regprovince+regcity+regarec);
					}
					for(CustomerInfo c:list){
						if(StringUtils.isBlank(c.getName())||StringUtils.isBlank(c.getOrgcode())){
							continue;
						}else if(customerInfoService.findByNameAndOrgcode(c)!=null){
							k++;
						}else{
							c.setCreatedate(new Date());
							if(map!=null){
								c.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
								c.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
							}
							c.setCustomerRecordId(customerRecord.getId());
							c.setDeleted("0");
							customerInfoService.insert(c);
							i++;
						}
						m++;
					}
					customerRecord.setUploadCon("本次一共"+customerRecord.getCounts()+"条数据，成功上传"+i+"条,重复"+k+"条，未上传"+(list.size()-i-k)+"条");
					customerRecordService.save(customerRecord);
					respon.success(list);
				}
			}
		} catch (Exception e) {
			if(list!=null){
				customerRecord.setUploadCon("本次一共"+customerRecord.getCounts()+"条数据，成功上传"+i+"条,重复"+k+"条，未上传"+(list.size()-k-i)+"条,第"+m+"行数据有误");
				customerRecordService.save(customerRecord);
				respon.error(customerRecord.getUploadCon());
			}
		}
		return respon;
	}
	
	//以下接口仅仅测试导入
	@RequestMapping(value="/imports",method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("imports/import");
		return mv;
	}
	@RequestMapping(value="/imports2",method=RequestMethod.GET)
	public ModelAndView index2(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("imports/import2");
		return mv;
	}
}
