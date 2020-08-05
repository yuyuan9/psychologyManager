package com.ht.biz.system.controller;

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
import com.ht.biz.service.PolicyDigService;
import com.ht.biz.service.PolicylibService;
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
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.recore.ImportRecord;
import com.ht.entity.policydig.PolicyDig;
import com.ht.entity.policydig.PolicydigVo;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/sys/ht-biz/policylib")
@Api(value="SysPolicylibController",description = "政策库后台管理")
public class SysPolicylibController extends BaseController{
	
	@Autowired
	private PolicylibService policylibService;
	
	@Autowired
	private ImportRecordService importRecordService;
	
	@Autowired
    private PolicyDigService policyDigService;
	
	@Value("${solr.dataupload}")
    private boolean dataupload;
	
	@ApiOperation(value="政策库后台列表数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "year", value = "年",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市索",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd) throws Exception{
		Respon respon=this.getRespon();
		try {
			boolean b=true;
			MyPage page=getMyPage(pd);
			SysUser user=LoginUserUtils.getLoginUser();
			String fq=policylibService.getPcAfterStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year"),user.getUserType(),user.getCompanyid());
			//System.out.println("fq="+fq);
			SolrUtils.getList(page, new Policylib(), b,Const.solrCore_policylib,"*:*",fq,"year desc",pd.getString("keyword"),false);
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
        @ApiImplicitParam(paramType="query",name = "id", value = "政策库数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id) throws Exception{
		Respon respon=this.getRespon();
		Policylib p=new Policylib();
		PolicydigVo vo=null;
		try {
			if(!StringUtils.isBlank(id)){
				p=(Policylib) SolrUtils.getById(Const.solrUrl, Const.solrCore_policylib, id, p);
				if(p!=null){
					vo=new PolicydigVo(p.getTechnology(),null,p.getIndustry(),null);
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
	
	
	@ApiOperation(value="政策库后台保存数据接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "policylib", value = "政策库数据封装",  dataType = "policylib"),
	})
	@PostMapping("save")
	public Respon save(@RequestBody Policylib policylib) throws Exception{
		Respon respon=this.getRespon();
		try {
			if(!StringUtils.isBlank(policylib.getPolicyDigId())){
				PolicyDig p=policyDigService.getById(policylib.getPolicyDigId());
				if(p.getBeginDate()!=null&&p.getEndDate()!=null){
					policylib.setEndtime(DateUtil.dateToStr(p.getBeginDate(), 11)+"-"+DateUtil.dateToStr(p.getEndDate(), 11));
				}
			}
			if(StringUtils.isBlank(policylib.getId())){
				policylib.setCreatedate(new Date());
				Map<String ,Object> map=getLoginInfo();
				if(map!=null){
					policylib.setCreateid(map.get("userId")==null?"":String.valueOf(map.get("userId")));
					policylib.setRegionid(map.get("companyid")==null?"":String.valueOf(map.get("companyid")));
				}
				policylibService.save(policylib);
			}else{
				Policylib p=new Policylib();
				p=(Policylib) SolrUtils.getById(Const.solrUrl, Const.solrCore_policylib, policylib.getId(),p);
				policylib.setCreatedate(p.getCreatedate());
				policylib.setRegionid(p.getRegionid());
				policylib.setCreateid(p.getCreateid());
				policylib.setLastmodified(new Date());
				policylibService.updateById(policylib);
			}
			SolrUtils.add(Const.solrUrl, Const.solrCore_policylib, policylib);
			respon.success(policylib);
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
	@ApiOperation(value="政策库后台删除接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "ids", value = "数据ids",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids) throws Exception{
		Respon respon=this.getRespon();
		try { 
			if(!StringUtils.isBlank(ids)){
				for(String id:ids.split(",")){
					policylibService.removeById(id);
				}
				SolrUtils.deleted(Const.solrUrl, Const.solrCore_policylib, ids);
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
	@ApiOperation(value="政策库后台导入接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "file", value = "导入文件",  dataType = "file"),
	})
	@PostMapping("imports")
	public Respon imports(HttpServletRequest request) throws Exception{
	Date date1=new Date();
	Respon respon=new Respon();
	ImportRecord im = createImportRecord();
	List<Integer> listint=new ArrayList<Integer>();
		try {
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = mRequest.getFile("file");
			if(multipartFile!=null){
			respon=Excel.excelImport(multipartFile, new Policylib(),1,getLoginInfo());
			List<Policylib> list=(List<Policylib>) respon.getData();
				if(list!=null&&list.size()>0&&list.size()<2001){
					try {
						if(StringUtils.isBlank(respon.getMsg())){
							int i=1;
							for (Policylib p : list) {
								p.setId(UuidUtil.get32UUID());
								if (StringUtils.isNotBlank(p.getYear())) {
									p.setYear(p.getYear().replace(".0", ""));
								}
								p.setCreatedate(new Date());
								p.setCreateid(im.getCreateid());
								p.setRegionid(im.getRegionid());
								p.setHisImportId(im.getId());
								if(StringUtils.isBlank(p.getYear())
									||StringUtils.isBlank(p.getProjecname())
									||StringUtils.isBlank(p.getSupportobj())
									||StringUtils.isBlank(p.getApplyterm())
									||StringUtils.isBlank(p.getEndtime())
									||StringUtils.isBlank(p.getRegion())
									||StringUtils.isBlank(p.getProvince())
									||StringUtils.isBlank(p.getChargedept())
									||StringUtils.isBlank(p.getRemark())
									||StringUtils.isBlank(p.getTechnology())
									||StringUtils.isBlank(p.getIndustry())){
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
												SolrUtils.addList(Const.solrUrl, Const.solrCore_policylib, list);
											}
											//SolrUtils.addList(Const.solrUrl, Const.solrCore_policylib, list);
											im.setAllCounts(list.size());
											im.setFileName(multipartFile.getOriginalFilename());
											im.setRemark("本次成功导入" + list.size() + "条数据");
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
						// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date2=new Date();
		long d=date2.getTime()-date1.getTime();
		d=d/1000;
		
		respon.setData(im.getId());
		respon.setReserveData("耗时"+d+"秒");
		//respon.setMsg("您上传的数据已上传，稍后请前往导入记录中查看结果");
		return respon;
	}
	@ApiOperation(value = "政策库后台查询导入结果")
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
	private String solrCondition(Policylib p) {
		StringBuffer sub = new StringBuffer();
		sub.append(StringUtils.isBlank(p.getProjecname())?"projecname:\"\"":"projecname:"+p.getProjecname()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getSpecialmum())?"specialmum:\"\"":"specialmum:"+p.getSpecialmum()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getManagerway())?"managerway:\"\"":"managerway:"+p.getManagerway()).append(" AND ");
		sub.append(StringUtils.isBlank(p.getSupportobj())?"supportobj:\"\"":"supportobj:"+p.getSupportobj());
		return sub.toString();
	}
	
	private ImportRecord createImportRecord() {
		ImportRecord im = new ImportRecord();
		SysUser user=LoginUserUtils.getLoginUser();
		im.setImportLocal("政策库");
		im.setTableName("t_policy_lib");
		//im.setAllCounts(counts);
		im.setId(UuidUtil.get32UUID());
		im.setCreatedate(new Date());
		im.setProvince(user.getRegprovince());
		im.setCity(user.getRegcity());
		im.setArea(user.getRegarec());
		im.setCreateid(user.getUserId());
		im.setRegionid(user.getCompanyid());
		im.setPhone(user.getPhone());
		return im;
	}
	
	private void importProjectLib(List<Policylib> list) {
		StringBuffer subsql=new StringBuffer();
					try {
						StringBuffer sub=new StringBuffer();
						sub.append("year,fileno,projecname,specialmum,managerway,supportobj,applyterm,fundfacilitie,endtime,region,province,"
								+ "city,area,chargedept,linkman,remark,technology,industry,hisImportId,source,id,createdate,createid,"
								+ "lastmodified,regionid");
						subsql.append("insert into t_policy_lib ("+sub.toString()+") values ");
						StringBuffer subvalues=new StringBuffer();
						DBConnection dbconn = new DBConnection(dataupload);
						
						int i=0;
						while(i<list.size()){
							subvalues.append("(");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getYear())?"":list.get(i).getYear())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getFileno())?"":list.get(i).getFileno())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getProjecname())?"":list.get(i).getProjecname())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getSpecialmum())?"":list.get(i).getSpecialmum())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getManagerway())?"":list.get(i).getManagerway())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getSupportobj())?"":list.get(i).getSupportobj())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getApplyterm())?"":list.get(i).getApplyterm())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getFundfacilitie())?"":list.get(i).getFundfacilitie())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getEndtime())?"":list.get(i).getEndtime())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getRegion())?"":list.get(i).getRegion())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getProvince())?"":list.get(i).getProvince())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getCity())?"":list.get(i).getCity())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getArea())?"":list.get(i).getArea())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getChargedept())?"":list.get(i).getChargedept())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getLinkman())?"":list.get(i).getLinkman())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getRemark())?"":list.get(i).getRemark())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getTechnology())?"":list.get(i).getTechnology())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getIndustry())?"":list.get(i).getIndustry())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getHisImportId())?"":list.get(i).getHisImportId())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getSource())?"":list.get(i).getSource())+"',");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getId())?"":list.get(i).getId())+"',");
							subvalues.append((list.get(i).getCreatedate()==null?null:("'"+DateUtil.dateToStr(list.get(i).getCreatedate(), 12)+"'"))+",");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getCreateid())?"":list.get(i).getCreateid())+"',");
							subvalues.append((list.get(i).getLastmodified()==null?null:("'"+DateUtil.dateToStr(list.get(i).getLastmodified(), 12)+"'"))+",");
							subvalues.append("'"+(StringUtils.isBlank(list.get(i).getRegionid())?"":list.get(i).getRegionid())+"'");
							subvalues.append("),");
							if((i+1)%20==0){
								//subsql.append(subvalues.toString().subSequence(0, subvalues.toString().length()-1));
								dbconn.getConnection();
								//System.out.println("sql="+subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
								dbconn.executeSql2(subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
								subvalues.delete(0, subvalues.length());
								dbconn.close();
							}
							i++;
						}
						if(StringUtils.isNotBlank(subvalues.toString())){
							dbconn.getConnection();
							//System.out.println("sql="+subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
							dbconn.executeSql2(subsql.toString()+subvalues.toString().subSequence(0, subvalues.toString().length()-1));
							dbconn.close();
						}
					//	dbconn.close();
//						for(Policylib p:list) {
//							try {
//								subvalues.append("(");
//								subvalues.append("'"+(StringUtils.isBlank(p.getYear())?"":p.getYear())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getFileno())?"":p.getFileno())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getProjecname())?"":p.getProjecname())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getSpecialmum())?"":p.getSpecialmum())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getManagerway())?"":p.getManagerway())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getSupportobj())?"":p.getSupportobj())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getApplyterm())?"":p.getApplyterm())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getFundfacilitie())?"":p.getFundfacilitie())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getEndtime())?"":p.getEndtime())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getRegion())?"":p.getRegion())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getProvince())?"":p.getProvince())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getCity())?"":p.getCity())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getArea())?"":p.getArea())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getChargedept())?"":p.getChargedept())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getLinkman())?"":p.getLinkman())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getRemark())?"":p.getRemark())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getTechnology())?"":p.getTechnology())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getIndustry())?"":p.getIndustry())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getHisImportId())?"":p.getHisImportId())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getSource())?"":p.getSource())+"',");
//								subvalues.append("'"+(StringUtils.isBlank(p.getId())?"":p.getId())+"',");
//								subvalues.append((p.getCreatedate()==null?null:("'"+DateUtil.dateToStr(p.getCreatedate(), 12)+"'"))+",");
//								subvalues.append("'"+(StringUtils.isBlank(p.getCreateid())?"":p.getCreateid())+"',");
//								subvalues.append((p.getLastmodified()==null?null:("'"+DateUtil.dateToStr(p.getLastmodified(), 12)+"'"))+",");
//								subvalues.append("'"+(StringUtils.isBlank(p.getRegionid())?"":p.getRegionid())+"'");
//								subvalues.append("),");
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
						//subsql.append(subvalues.toString().subSequence(0, subvalues.toString().length()-1));
						
						
						subsql=null;
						sub=null;
						subvalues=null;
						list=null;
						dbconn=null;
						//solr引擎查重
//						String sub=solrCondition(p);
//						Boolean b = SolrUtils.getone(Const.solrUrl, Const.solrCore_policylib, sub);
//						if (b) {
//							SolrUtils.deleted(Const.solrUrl, Const.solrCore_policylib, p.getId());
//						}
						//mysql数据库查重
//						QueryWrapper<Policylib> qw=new QueryWrapper<Policylib>();
//				    	if(p.getProjecname()==null){
//				    		qw.isNull("projecname");
//				    	}else{
//				    		qw.eq("projecname", p.getProjecname());
//				    	}
//				    	if(p.getSpecialmum()==null){
//				    		qw.isNull("specialmum");
//				    	}else{
//				    		qw.eq("specialmum", p.getSpecialmum());
//				    	}
//				    	if(p.getManagerway()==null){
//				    		qw.isNull("managerway");
//				    	}else{
//				    		qw.eq("managerway", p.getManagerway());
//				    	}
//				    	if(p.getSupportobj()==null){
//				    		qw.isNull("supportobj");
//				    	}else{
//				    		qw.eq("supportobj", p.getSupportobj());
//				    	}
//				    	Policylib pl=policylibService.getOne(qw, false);
//				    	if(pl==null){
//				    		policylibService.save(p);
//				    	}else{
//				    		SolrUtils.deleted(Const.solrUrl, Const.solrCore_policylib, p.getId());
//				    	}
					}catch(Exception e) {
						e.printStackTrace();
					}
//			}
//		}).start();
	}
	
//	private int[] importSolr(List<Policylib> list,ImportRecord im) {
//	    int[] ret={0,0,0};
//	    List<Policylib> newlist = new ArrayList<Policylib>();
//	    //solr引擎验证去重（较快不太准确）
//	    for (Policylib p : list) {
//	    	try {
//				String sub=solrCondition(p);
//				Boolean b = SolrUtils.getone(Const.solrUrl, Const.solrCore_policylib, sub);
//				if (!b) {
//					p.setId(UuidUtil.get32UUID());
//					if (StringUtils.isNotBlank(p.getYear())) {
//						p.setYear(p.getYear().replace(".0", ""));
//					}
//					p.setCreatedate(new Date());
//					p.setCreateid(im.getCreateid());
//					p.setRegionid(im.getRegionid());
//					p.setHisImportId(im.getId());
//					//projectlibService.save(p);
//					//SolrUtils.add(Const.solrUrl, Const.solrCore_policylib, p);
//					//c++;// 成功数据
//					ret[0]=ret[0]+1;
//					newlist.add(p);
//				} else {
//					// 重复数据
//					ret[1]=ret[1]+1;
//					//a++;
//				}
//		   }catch(Exception e) {
//			   e.printStackTrace();
//			   ret[2]=ret[2]+1;//失败数据
//		   }
//	    }
//	    //数据库再次验证（较慢比较准确）
//	    List<Policylib> noreset=new ArrayList<Policylib>();
//	    for (Policylib p : newlist) {
//	    	QueryWrapper<Policylib> qw=new QueryWrapper<Policylib>();
//	    	if(p.getProjecname()==null){
//	    		qw.isNull("projecname");
//	    	}else{
//	    		qw.eq("projecname", p.getProjecname());
//	    	}
//	    	if(p.getSpecialmum()==null){
//	    		qw.isNull("specialmum");
//	    	}else{
//	    		qw.eq("specialmum", p.getSpecialmum());
//	    	}
//	    	if(p.getManagerway()==null){
//	    		qw.isNull("managerway");
//	    	}else{
//	    		qw.eq("managerway", p.getManagerway());
//	    	}
//	    	if(p.getSupportobj()==null){
//	    		qw.isNull("supportobj");
//	    	}else{
//	    		qw.eq("supportobj", p.getSupportobj());
//	    	}
//	    	Policylib pl=policylibService.getOne(qw, false);
//	    	if(pl==null){
//				noreset.add(p);
//			}
//	    	ret[0]=noreset.size();
//			ret[1]=list.size()-noreset.size();
//	    }
//	    try {
//			SolrUtils.addList(Const.solrUrl, Const.solrCore_policylib, noreset);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    importProjectLib(noreset);
//		return ret;
//	}
}
