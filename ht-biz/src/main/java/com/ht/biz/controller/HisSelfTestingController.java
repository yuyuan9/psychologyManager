package com.ht.biz.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CollectionDownService;
import com.ht.biz.service.HisSelfTestingService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.RewardRuleService;
import com.ht.biz.service.SelfTestingService;
import com.ht.biz.service.impl.hisself.estimate.RuleComparisonService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.FileDownload;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.biz.freeassess.HisSelfTesting;
import com.ht.entity.biz.freeassess.SelfTesting;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.honeymanager.RewardRule.Code;
import com.ht.entity.biz.msg.WorkReminder.Work;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MapBeanUtil;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/hisself")
@Api(value="HisSelfTestingController",description = "免费评估前端管理")
public class HisSelfTestingController extends BaseController{
	
	@Autowired
	private SelfTestingService selfTestingService;
	
	@Autowired
	private HisSelfTestingService hisSelfTestingService;
	
	@Autowired
	private CollectionDownService collectionDownService;
	
	@Autowired
    private PointRedemptionService pointRedemptionService;
	
	@Autowired
	private RewardRuleService rewardRuleService;
	
	@GetMapping("/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("hisself/"+page);
	}
	
	
	@ApiOperation(value="免费评估提交")
	@ApiImplicitParams({
        //@ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@PostMapping("submit")
	public Respon estimateSubmit( HisSelfTesting sf, SelfTesting ssf, BindingResult result)throws Exception {
		Respon respon = new Respon();
		PageData pd = this.getPageData();
		
		System.out.println(!result.hasErrors());
		System.out.println(result.getAllErrors().toString());
		//System.out.println(MsgUtil.errors(result.getFieldErrors()));
		Map<String, Object> map=getLoginInfo();
		if (map != null) {
			if (!result.hasErrors()) {
					pd.put("createid", map.get("userId"));
					QueryWrapper<SelfTesting> qw=new QueryWrapper<SelfTesting>();
					qw.eq("createId", map.get("userId"));
					qw.orderByDesc("createid");
					SelfTesting oldsf =selfTestingService.getOne(qw, false);
					if (oldsf != null) {
						ssf.setId(oldsf.getId());
						ssf.setLastmodified(new Date());
						selfTestingService.updateById(ssf);
					} else {
						ssf.setCreatedate(new Date());
						ssf.setCreateid(String.valueOf(map.get("userId")));
						ssf.setRegionid(String.valueOf(map.get("companyid")));
						selfTestingService.save(ssf);
					}
					sf.setCreatedate(new Date());
					sf.setTestId(ssf.getId());
					sf.setCreateid(String.valueOf(map.get("userId")));
					sf.setRegionid(String.valueOf(map.get("companyid")));
				//sf.setId(get32UUID());
				new RuleComparisonService().getTable(sf);
				hisSelfTestingService.saveOrUpdate(sf);
				MsgUtil.addMsg(String.valueOf(Work.free_assess_result), "/ht-biz/hisself/result?id="+sf.getId()+"&province="+sf.getProvince()+"&city="+sf.getCity(), String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
				respon.success(sf);
			} else {
				respon.error("error");
			}
		}else{
			respon.loginerror("未登录");
		}
		return respon;
	}
	@ApiOperation(value="免费评估查看详情")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "免费评估数据id",  dataType = "String"),
	})
	@GetMapping(value = "/id")
	public Respon getdata(String id) throws Exception{
		Respon respon = new Respon();
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				if(StringUtils.isNoneBlank(id)){
					HisSelfTesting hst=hisSelfTestingService.getById(id);
					if(StringUtils.isNoneBlank(hst.getProductServ())){
						hst.setProductServ(hst.getProductServ().split("&")[0]);
					}
					String table=new RuleComparisonService().getTable(hst);
					hst.setSzmsg(table);
					respon.success(hst);
				}else{
					respon.success(null);
				}
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	/*
	 * 保存草稿
	 */
	@ApiOperation(value="免费保存草稿")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "testing", value = "testing数据",  dataType = "HisSelfTesting"),
	})
	@PostMapping(value = "/saveTemporary")
	public Respon saveHisSelfTesting(HisSelfTesting testing) throws Exception{
		Respon respon=this.getRespon();
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				HisSelfTesting testing1 =hisSelfTestingService.getById(testing.getId());
				testing.setTemporary(1);
				if(testing1!=null){
					testing.setLastmodified(new Date());
					hisSelfTestingService.updateById(testing);
				}else{
					testing.setCreatedate(new Date());
					testing.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					testing.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
					hisSelfTestingService.save(testing);
				}
				MsgUtil.addMsg(String.valueOf(Work.free_assess_save), "/ht-biz/hisself/index?id="+testing.getId(), String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
				respon.success(testing);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}
	/*
	 * 导出现状对比
	 */
	@ApiOperation(value="导出现状对比")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "testingId", value = "评估数据id",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "suffix", value = "后缀名（.xls或者.xlsx）",  dataType = "string"),
	})
	@GetMapping(value = "/exportexcel")
	public void exportexcel(String testingId,HttpServletResponse response,String suffix) throws Exception{
		//
		Map<String,Object> map=getLoginInfo();
		if(map!=null){
			Double myhoney = pointRedemptionService.getWaterTotal(String.valueOf(map.get("userId")));
			myhoney=myhoney==null?0:myhoney;
			QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
			qw.eq("code", Code.condit_compare);
			RewardRule r=rewardRuleService.getOne(qw);
			if(myhoney>Math.abs(Double.valueOf(r.getReturnValue()))){
				if(!StringUtils.isBlank(testingId)){
					HisSelfTesting hst=hisSelfTestingService.getById(testingId);
					if(StringUtils.isNoneBlank(hst.getProductServ())){
						hst.setProductServ(hst.getProductServ().split("&")[0]);
					}
					String szmsgString=new RuleComparisonService().getTable(hst);
					if(StringUtils.isBlank(szmsgString)){
						szmsgString=hst.getSztable();
					}
					szmsgString=szmsgString.replace("<tr align='center'>", "");
					szmsgString=szmsgString.replace("</tr>", "");
					szmsgString=szmsgString.replace("<td>", "");
					szmsgString=szmsgString.replace("<th>", "");
					szmsgString=szmsgString.replace("</th>", "</td>");
					szmsgString=szmsgString.replace("<s class='sign right'></s>", "√");
					szmsgString=szmsgString.replace("<s class='sign wrong'></s>", "×");
					szmsgString=szmsgString.replace("<font color='red'>", "");
					szmsgString=szmsgString.replace("</font>", "");
					String[] strings= szmsgString.split("</td>");
					Workbook workbook = null ;
					if(!StringUtils.isBlank(suffix)&&StringUtils.equals(suffix, ".xlsx")){ 
					    //2007 
					    workbook = new XSSFWorkbook(); 
					}else{
						//2003
						suffix=".xls";
						workbook = new HSSFWorkbook(); 
					}
					OutputStream  os = response.getOutputStream();
					response.reset();
					response.setContentType("application/msexcel");
					response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(hst.getCity()+"现状对比","UTF8")+suffix);
					Sheet sheet = workbook.createSheet();
					//第一行
					Row row0=sheet.createRow(0);
					//字体加粗
					Font font1=workbook.createFont();
					font1.setFontName("粗体");;
					font1.setFontHeightInPoints((short)18);
					Cell row0cell0=row0.createCell(0);
					font1.setFontName("等线");  
					row0cell0.setCellValue(hst.getCity()+"现状对比");
					//合并单元格
					CellRangeAddress crarow0cell0 = new CellRangeAddress(0,0,0,6);
					CellStyle style = workbook.createCellStyle();
					//设置居中
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					style.setFont(font1);
					row0cell0.setCellStyle(style);
					row0.setHeightInPoints(6*sheet.getDefaultRowHeightInPoints()); 
					sheet.addMergedRegion(crarow0cell0);
					sheet.setColumnWidth(0, 10 * 256);
					sheet.setColumnWidth(1, 15 * 256);
					sheet.setColumnWidth(2, 40 * 256);
					sheet.setColumnWidth(3, 40 * 256);
					sheet.setColumnWidth(4, 40 * 256);
					sheet.setColumnWidth(5, 15 * 256);
					sheet.setColumnWidth(6, 15 * 256);
					int i=strings.length/7;
					CellStyle style2 = workbook.createCellStyle();
					//设置居中
					style2.setAlignment(HorizontalAlignment.CENTER);
					style2.setVerticalAlignment(VerticalAlignment.CENTER);
					Font font2=workbook.createFont();
					font2.setFontHeightInPoints((short)12);
					style2.setFont(font2);
					for(int k=1;k<=i;k++){
						int m=(k-1)*7;
						Row row=sheet.createRow(k);
						row.setHeightInPoints(3*sheet.getDefaultRowHeightInPoints()); 
						for(int n=0;n<7;n++){
							Cell cell=row.createCell(n);
							cell.setCellValue(strings[m+n]);
							cell.setCellStyle(style2);
						}
					}
//					QueryWrapper<CollectionDown> qws=new QueryWrapper<CollectionDown>();
//					qws.eq("targetId", testingId);
//					qws.eq("createid", LoginUserUtils.getLoginUser().getUserId());
//					CollectionDown c=collectionDownService.getOne(qws, false);
//					if(c==null){
//						CollectionDown collection = new CollectionDown();
//			            collection.setType( 1 );
//			            collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
//			            collection.setTargetId( testingId );
//			            collection.setClazzName("com.ht.entity.biz.freeassess.HisSelfTesting");
//			            collection.setCreatedate( new Date(  ) );
//			            collectionDownService.save(  collection);
//			            RewardUtil.disHoney(r, String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("regionId")));
//					}
					RewardUtil.disHoney(r, String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("regionId")));
					workbook.write(os);
				     os.flush();
				     os.close();
				}
			}
		}
	}
	@ApiOperation(value="导出政策匹配")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "path", value = "文件路径",  dataType = "string"),
	})
	@GetMapping(value = "/download")
	//此方法针对在Resources目录下文件有效
	public void export(String path,HttpServletRequest request,HttpServletResponse response){
		try {
			///static/statics/js/cityData.json
			//String basePath = request.getSession().getServletContext().getRealPath("/src/main/resources/static/");
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				Double myhoney = pointRedemptionService.getWaterTotal(String.valueOf(map.get("userId")));
				myhoney=myhoney==null?0:myhoney;
				QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
				qw.eq("code", Code.project_matching);
				RewardRule r=rewardRuleService.getOne(qw);
				if(myhoney>Math.abs(Double.valueOf(r.getReturnValue()))){
					if (!StringUtils.isBlank(path)) {
//						QueryWrapper<CollectionDown> qws=new QueryWrapper<CollectionDown>();
//						qws.eq("targetId", path);
//						qws.eq("createid", LoginUserUtils.getLoginUser().getUserId());
//						CollectionDown c=collectionDownService.getOne(qws, false);
//						if(c==null){
//							CollectionDown collection = new CollectionDown();
//				            collection.setType( 1 );
//				            collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
//				            collection.setTargetId( path );
//				            collection.setClazzName("com.ht.entity.biz.freeassess.HisSelfTesting");
//				            collection.setCreatedate( new Date(  ) );
//				            collectionDownService.save(  collection);
//				            RewardUtil.disHoney(r, String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
//						}
						RewardUtil.disHoney(r, String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
						Resource resource = new ClassPathResource(path);
						File sourceFile =  resource.getFile();
						FileDownload download = new FileDownload();
						String fileName = "政策匹配表";
						fileName = fileName + StringUtil.getFileType(path);
						download.fileDownload(response, sourceFile.getPath(), fileName);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@ApiOperation(value="查询用户的评估记录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "temporary", value = "不是参数，无需传递（temporary为1表示暂存0表示评估提交）",  dataType = "string"),
	})
	@GetMapping(value = "/findByCreateId")
	public Respon findByCreateId(MyPage<HisSelfTesting> page){
		Respon respon=this.getRespon();
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				QueryWrapper<HisSelfTesting> qw=new QueryWrapper<HisSelfTesting>();
				qw.select("id","companyName","orgcode","createdate","province",
						"city","area","productServ","total");
				qw.eq("createid", String.valueOf(map.get("userId")));
				page=(MyPage<HisSelfTesting>) hisSelfTestingService.page(page, qw);
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respon;
	}
	@ApiOperation(value="根据id查询评估记录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping(value = "/findById")
	public Respon findById(String id){
		Respon respon=this.getRespon();
		try {
			HisSelfTesting ht=new HisSelfTesting();
			if(StringUtils.isNotBlank(id)){
				ht=hisSelfTestingService.getById(id);
			}
			respon.success(ht);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="根据orgcode查询评估记录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "orgcode", value = "orgcode",  dataType = "string"),
	})
	@GetMapping(value = "/findByOrgcode")
	public Respon findByOrgcode(String orgcode){
		Respon respon=this.getRespon();
		try {
			HisSelfTesting ht=new HisSelfTesting();
			if(StringUtils.isNotBlank(orgcode)){
				QueryWrapper<HisSelfTesting> qw=new QueryWrapper<HisSelfTesting>();
				qw.eq("orgcode", orgcode);
				qw.orderByDesc("createdate");
				ht=hisSelfTestingService.getOne(qw, false);
			}
			respon.success(ht);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@GetMapping(value = "/exportpdf")
	public void exportpdf(String testingid,HttpServletResponse resopnse) throws Exception{ 
		//path=/pdf/free
		try {
			Map<String,Object> map=getLoginInfo();
			if(map!=null){
				Double myhoney = pointRedemptionService.getWaterTotal(String.valueOf(map.get("userId")));
				myhoney=myhoney==null?0:myhoney;
				QueryWrapper<RewardRule> qw=new QueryWrapper<RewardRule>();
				qw.eq("code", Code.his_pdf_export);
				RewardRule r=rewardRuleService.getOne(qw);
				if(myhoney>Math.abs(Double.valueOf(r.getReturnValue()))){
					ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
					List list = new ArrayList();
					HisSelfTesting testing=hisSelfTestingService.getById(testingid);
					if(StringUtils.isNoneBlank(testing.getProductServ())){
						testing.setProductServ(testing.getProductServ().split("&")[0]);
					}
					String szmsgString=new RuleComparisonService().getTable(testing);
					if(StringUtils.isBlank(szmsgString)){
						szmsgString=testing.getSztable();
					}
					szmsgString=szmsgString.replace("<tr align='center'>", "");
					szmsgString=szmsgString.replace("</tr>", "");
					szmsgString=szmsgString.replace("<td>", "");
					szmsgString=szmsgString.replace("<th>", "");
					szmsgString=szmsgString.replace("</th>", "</td>");
					szmsgString=szmsgString.replace("<s class='sign right'></s>", "√");
					szmsgString=szmsgString.replace("<s class='sign wrong'></s>", "×");
					szmsgString=szmsgString.replace("<font color='red'>", "");
					szmsgString=szmsgString.replace("</font>", "");
					String[] strings= szmsgString.split("</td>");
					Map<String,Object> table1=new MapBeanUtil().beanAllToMap(testing);
//					if("A".equals(testing.getSaleyfrate())){
//						table1.put("saleyfrate", "销售收入≤5000万，≥5%");
//					}else if("B".equals(testing.getSaleyfrate())){
//						table1.put("saleyfrate", "5000万＜销售收入≤2亿，≥4%");
//					}else if("C".equals(testing.getSaleyfrate())){
//						table1.put("saleyfrate", "销售收入＞2亿，≥3%");	
//					}else if("D".equals(testing.getSaleyfrate())){
//						table1.put("saleyfrate", "近2年研发费用＜3%");
//					}else{
//						table1.put("saleyfrate", "近1年研发费用，＜3%");
//					}
//					
//					if("A".equals(testing.getJnyfrate())){
//						table1.put("jnyfrate", "≥60%");
//					}else if("B".equals(testing.getJnyfrate())){
//						table1.put("jnyfrate", "＜60%");
//					}
//					
//					if("A".equals(testing.getProdrate())){
//						table1.put("prodrate", "不低于60%");
//					}else if("B".equals(testing.getProdrate())){
//						table1.put("prodrate", "不低于50%");
//					}else if("C".equals(testing.getProdrate())){
//						table1.put("prodrate", "高于50%但低于60%");	
//					}
					String path=hisSelfTestingService.getHisSelfTesting(table1, testing);
					table1.put("xtotal", testing.getXselftotal()+testing.getXegTotal()+testing.getXtotal());
					table1.put("ytotal", testing.getYselftotal()+testing.getYegtotal()+testing.getYtotal());
					table1.put("total", testing.getTotal()+"~"+(testing.getTotal()+6));
					String compsys = testing.getCompsys();
					if("A".equals(compsys)){	compsys="3项都满足";}
					else if("B".equals(compsys)){ compsys="满足2项";}
					else if("C".equals(compsys)){ compsys="满足1项";}
					else {	compsys="都不满足";	}
					table1.put("compsys",compsys);
					
					String cooper=testing.getCooper();
					if("A".equals(cooper)){cooper="设立研发机构并开展产学研合作";}
					else if("B".equals(cooper)){cooper="设立研发机构但无产学研合作";}
					else{cooper="无研发机构无产学研合作";}
					table1.put("cooper", cooper);
					
					String platform=testing.getPlatform();
					if("A".equals(platform)){platform="建立了科技成果转化的组织实施与激励奖励制度, 且有开放式的创新创业平台";}
					else if("B".equals(platform)){platform="建立了科技成果转化的组织实施与激励奖励制度, 无开放式的创新创业平台";}
					else{platform="没有科技成果转化的组织实施与激励奖励制度, 无开放式的创新创业平台";}
					table1.put("platform", platform);
					
					String personnel=testing.getPersonnel();
					if("A".equals(personnel)){personnel="是";}
					else{personnel="否";}
					table1.put("personnel", personnel);
					int i=1;
					for(String s:strings){
						table1.put("di"+i, s);
						i++;
					}
					table1.put("title", "企业申请"+testing.getCity()+"/"+testing.getProvince()+"国家高新技术企业现状对比");
					list.add(table1);
					
					Resource resource = new ClassPathResource("/pdf/free/"+path);
					File file=resource.getFile();
					if(file.exists()){
						hisSelfTestingService.exportpdfs(list, byteArray, resource.getFile().getPath(),file.listFiles().length);
					}
					resopnse.setContentType("application/octet-stream;charset=UTF-8");
					resopnse.setContentLength(byteArray.size());
					FileDownload.setFileDownloadHeader(resopnse, "评测表.pdf");
					OutputStream os = resopnse.getOutputStream();
					IOUtils.write(byteArray.toByteArray(), os);
					os.flush();
					IOUtils.closeQuietly(os);
					RewardUtil.disHoney(r, String.valueOf(map.get("userId")), String.valueOf(map.get("userId")), String.valueOf(map.get("companyid")));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
