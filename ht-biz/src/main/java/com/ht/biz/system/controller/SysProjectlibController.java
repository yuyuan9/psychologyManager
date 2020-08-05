package com.ht.biz.system.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ht.biz.service.ImportRecordService;
import com.ht.biz.service.ProjectlibService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.constants.Const.Code;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.support.mysql.DBConnection;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.ReflectHelper;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.recore.ImportRecord;
import com.ht.entity.policydig.PolicydigVo;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/sys/ht-biz/projectlib")
@Api(value = "SysProjectlibController", description = "立项库后台管理")
public class SysProjectlibController extends BaseController {

	@Autowired
	private ProjectlibService projectlibService;

	@Autowired
	private ImportRecordService importRecordService;

	@Value("${solr.dataupload}")
    private boolean dataupload;
	
	@ApiOperation(value = "立项库后台列表数据接口")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "keyword", value = "关键字搜索", dataType = "string"),
			@ApiImplicitParam(paramType = "query", name = "yearProject", value = "立项年度", dataType = "string"),
			@ApiImplicitParam(paramType = "query", name = "province", value = "省", dataType = "string"),
			@ApiImplicitParam(paramType = "query", name = "city", value = "市索", dataType = "string"),
			@ApiImplicitParam(paramType = "query", name = "area", value = "区", dataType = "string"),
			@ApiImplicitParam(paramType = "query", name = "id", value = "数据id", dataType = "string"), })
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd) throws Exception {
		Respon respon = this.getRespon();
		try {
			boolean b = true;
			MyPage page = getMyPage(pd);
			SysUser user=LoginUserUtils.getLoginUser();
			String fq=projectlibService.getPcAfterStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("yearProject"),user.getUserType(),user.getCompanyid());
			System.out.println("fq="+fq);
			SolrUtils.getList(page, new Projectlib(), b,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),false);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value="政策库后台编辑数据接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "立项库数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id) throws Exception{
		Respon respon=this.getRespon();
		Projectlib p=new Projectlib();
		PolicydigVo vo=null;
		try {
			if(!StringUtils.isBlank(id)){
				p=(Projectlib) SolrUtils.getById(Const.solrUrl, Const.solrCore_projectlib, id, p);
				if(p!=null){
					vo=new PolicydigVo(p.getTechnicalField(),null,p.getIndustry(),null);
				}
			}
			respon.success(p,vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	
	@ApiOperation(value = "立项库后台保存数据接口")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "projectlib", value = "立项库数据封装", dataType = "projectlib"), })
	@PostMapping("save")
	public Respon save(@RequestBody Projectlib projectlib) throws Exception {
		Respon respon = this.getRespon();
		try {
			if (StringUtils.isBlank(projectlib.getId())) {
				projectlib.setCreatedate(new Date());
				Map<String, Object> map = getLoginInfo();
				if (map != null) {
					projectlib.setCreateid(map.get("userId") == null ? "" : String.valueOf(map.get("userId")));
					projectlib.setRegionid(map.get("companyid") == null ? "" : String.valueOf(map.get("companyid")));
				}
				projectlibService.save(projectlib);
			} else {
				Projectlib p=new Projectlib();
				p=(Projectlib) SolrUtils.getById(Const.solrUrl, Const.solrCore_projectlib, projectlib.getId(),p);
				projectlib.setCreatedate(p.getCreatedate());
				projectlib.setRegionid(p.getRegionid());
				projectlib.setCreateid(p.getCreateid());
				projectlib.setLastmodified(new Date());
				projectlibService.updateById(projectlib);
			}
			SolrUtils.add(Const.solrUrl, Const.solrCore_projectlib, projectlib);
			respon.success(projectlib);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}

	@ApiOperation(value = "立项库后台删除接口")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "ids", value = "数据id", dataType = "string"), })
	@GetMapping("deleted")
	public Respon deleted(String ids) throws Exception {
		Respon respon = this.getRespon();
		try {
			if (!StringUtils.isBlank(ids)) {
				for (String id : ids.split(",")) {
					projectlibService.removeById(id);
				}
				SolrUtils.deleted(Const.solrUrl, Const.solrCore_projectlib, ids);
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		return respon;
	}

	@ApiOperation(value = "立项库后台导入接口")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "file", value = "导入文件", dataType = "file"), })
	@PostMapping("imports")
	public Respon imports(HttpServletRequest request) throws Exception {
		Date date1=new Date();
		Respon respon = new Respon();
		ImportRecord im = createImportRecord();
		List<Integer> listint=new ArrayList<Integer>();
		try {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = mRequest.getFile("file");
			if (multipartFile != null) {
				respon = Excel.excelImport(multipartFile, new Projectlib(), 1, getLoginInfo());
				List<Projectlib> list = (List<Projectlib>) respon.getData();
				if (list != null && list.size() < 2001) {
					try {
						if(StringUtils.isBlank(respon.getMsg())){
							int i=1;
							for (Projectlib p : list) {
								p.setId(UuidUtil.get32UUID());
								if (StringUtils.isNotBlank(p.getYearProject())) {
									p.setYearProject(p.getYearProject().replace(".0", ""));
								}
								p.setCreatedate(new Date());
								p.setCreateid(im.getCreateid());
								p.setRegionid(im.getRegionid());
								p.setHisImportId(im.getId());
								if(StringUtils.isBlank(p.getCompanyName())
										||StringUtils.isBlank(p.getType())
										||StringUtils.isBlank(p.getDirectorUnit())
										||StringUtils.isBlank(p.getYearProject())
										||StringUtils.isBlank(p.getRegion())
										||StringUtils.isBlank(p.getProvince())){
										listint.add(i);
									}
									i++;
							}
							if(listint.size()>0){
								StringBuffer sub=new StringBuffer("");
								sub.append("第");
								for(int k=0;k<listint.size();k++){
									if(k==listint.size()-1){
										sub.append(listint.get(k));
									}else{
										sub.append((listint.get(k))+"、");
									}
								}
								sub.append("行数据错误，导入失败");
								respon.setMsg(sub.toString());
								respon.setCode(Code.C10110.name());
								
							}else{
								new Thread(new Runnable() {
									@Override
									public void run() {
										try {
											if(dataupload){
												SolrUtils.addList(Const.solrUrl, Const.solrCore_projectlib, list);
											}
											//	SolrUtils.addList(Const.solrUrl, Const.solrCore_projectlib, list);
												im.setAllCounts(list.size());
												im.setFileName(multipartFile.getOriginalFilename());
												im.setRemark("本次一共导入" + list.size() + "条数据");
												importRecordService.save(im);
												//respon.setMsg(im.getRemark());
											    importProjectLib(list);
											} catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}).start();		
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							respon.setMsg(e.getMessage());
						} 
						
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date2=new Date();
		long d=date2.getTime()-date1.getTime();
		d=d/1000;
		respon.setData(im.getId());
		respon.setReserveData("耗时"+d+"秒");
		//respon.setMsg(im.getRemark());
		return respon;
	}
	@ApiOperation(value = "立项库后台查询导入结果")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", value = "结果id", dataType = "String"), })
	@GetMapping("getResult")
	public Respon getResult(String id) throws Exception {
		Respon respon = new Respon();
		ImportRecord im=null;
		try {
			if(StringUtils.isNotBlank(id)){
				im=importRecordService.getById(id);
				respon.success(im);
			}else{
				respon.error(im);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	/*
	 * solr 条件获取
	 */
	private String solrCondition(Projectlib p) {
		StringBuffer sub = new StringBuffer();
		sub.append(StringUtils.isBlank(p.getCompanyName()) ? "companyName:\"\"":"companyName:" + p.getCompanyName()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getName()) ? "name:\"\"" : "name:" + p.getName()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getType()) ? "type:\"\"" : "type:" + p.getType()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getSpecial()) ? "special:\"\"" : "special:" + p.getSpecial());
		return sub.toString();
	}
	
	private ImportRecord createImportRecord() {
		ImportRecord im = new ImportRecord();
		SysUser user=LoginUserUtils.getLoginUser();
		im.setImportLocal("立项库");
		im.setTableName("t_project_lib");
		//im.setAllCounts(counts);
		im.setId(UuidUtil.get32UUID());
		im.setProvince(user.getRegprovince());
		im.setCity(user.getRegcity());
		im.setArea(user.getRegarec());
		im.setCreatedate(new Date());
		im.setCreateid(user.getUserId());
		im.setRegionid(user.getCompanyid());
		im.setPhone(user.getPhone());
		return im;
	}
	
	private void importProjectLib(List<Projectlib> list) {
		Date date1=new Date();
		StringBuffer subsql=new StringBuffer();
				try {
					
					Field[] fs=ReflectHelper.getAllField(Projectlib.class);
					StringBuffer sub=new StringBuffer();
					for(int i=0;i<fs.length;i++){
						sub.append(fs[i].getName());
						if(i!=fs.length-1){
							sub.append(",");
						}
					}
					subsql.append("insert into t_project_lib ("+sub.toString()+") values ");
					StringBuffer subvalues=new StringBuffer();
					DBConnection dbconn = new DBConnection(dataupload);
					
					int k=0;
					while(k<list.size()){
						
						subvalues.append("(");
						for(int i=0;i<fs.length;i++){
							Object object=ReflectHelper.getValueByFieldName(list.get(k), fs[i].getName());
							if(StringUtils.equals(fs[i].getType().getName(), "java.lang.String")){
								object=object==null?"":object;
								subvalues.append("'"+object+"'");
							}else if(StringUtils.equals(fs[i].getType().getName(), "java.util.Date")){
								object=object==null?null:"'"+DateUtil.dateToStr(DateUtil.StrTodate(String.valueOf(object), "EEE MMM dd HH:mm:ss z yyyy"),12)+"'";
								subvalues.append(object);
							}else{
								object=object==null?0:object;
								subvalues.append("'"+object+"'");
							}
							if(i!=fs.length-1){
								subvalues.append(",");
							}
						}
						subvalues.append("),");
						if((k+1)%20==0){
							//subsql.append(subvalues.toString().subSequence(0, subvalues.toString().length()-1));
							//System.out.println("sql="+subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
							dbconn.getConnection();
							dbconn.executeSql2(subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
							subvalues.delete(0, subvalues.length());
							dbconn.close();
						}
						k++;
					}
					if(StringUtils.isNotBlank(subvalues.toString())){
						dbconn.getConnection();
						dbconn.executeSql2(subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
						dbconn.close();
					}
					//dbconn.close();
					subsql=null;
					sub=null;
					subvalues=null;
					list=null;
					dbconn=null;
//					for(Projectlib p:list) {
//						try {
//							
//							subvalues.append("(");
//							for(int i=0;i<fs.length;i++){
//								Object object=ReflectHelper.getValueByFieldName(p, fs[i].getName());
//								if(StringUtils.equals(fs[i].getType().getName(), "java.lang.String")){
//									object=object==null?"":object;
//									subvalues.append("'"+object+"'");
//								}else if(StringUtils.equals(fs[i].getType().getName(), "java.util.Date")){
//									object=object==null?null:"'"+DateUtil.dateToStr(DateUtil.StrTodate(String.valueOf(object), "EEE MMM dd HH:mm:ss z yyyy"),12)+"'";
//									subvalues.append(object);
//								}else{
//									object=object==null?0:object;
//									subvalues.append("'"+object+"'");
//								}
//								if(i!=fs.length-1){
//									subvalues.append(",");
//								}
//							}
//							subvalues.append("),");
//							
////							//solr引擎查重
////							String sub=solrCondition(p);
////							Boolean b = SolrUtils.getone(Const.solrUrl, Const.solrCore_projectlib, sub);
////							if (b) {
////								SolrUtils.deleted(Const.solrUrl, Const.solrCore_projectlib, p.getId());
////							}
////							//mysql数据库查重
////							QueryWrapper<Projectlib> qw=new QueryWrapper<Projectlib>();
////							if(p.getCompanyName()==null){
////								qw.isNull("companyName");
////							}else{
////								qw.eq("companyName", p.getCompanyName());
////							}
////							if(p.getName()==null){
////								qw.isNull("name");
////							}else{
////								qw.eq("name", p.getName());
////							}
////							if(p.getType()==null){
////								qw.isNull("type");
////							}else{
////								qw.eq("type", p.getType());
////							}
////							if(p.getSpecial()==null){
////								qw.isNull("special");
////							}else{
////								qw.eq("special", p.getSpecial());
////							}
////							Projectlib pl=projectlibService.getOne(qw, false);
////							if(pl==null){
////								projectlibService.save(p);
////							}else{
////								SolrUtils.deleted(Const.solrUrl, Const.solrCore_projectlib, p.getId());
////							}
//						}catch(Exception e) {
//							e.printStackTrace();
//						}
//					}
				//	subsql.append(subvalues.toString().subSequence(0, subvalues.toString().length()-1));
					//DBConnection dbconn = new DBConnection();
					//dbconn.getConnection();
				//	dbconn.executeSql(subsql.toString());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Date date2=new Date();
				long d=date2.getTime()-date1.getTime();
				d=d/1000;
				System.out.println("耗时"+d+"秒");
	}
	

	
	public static void main(String[] args) {
		//Projectlib p = new Projectlib();
		Field[] fs=ReflectHelper.getAllField(Projectlib.class);
		StringBuffer sub=new StringBuffer();
		for(Field f:fs){
			sub.append(f.getName()+",");
		}
		System.out.println(sub.toString());
		System.out.println(sub.toString().substring(0,sub.toString().length()-1));
	}
}
