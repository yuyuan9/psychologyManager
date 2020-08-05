package com.ht.biz.controller.xwController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.CollectionDownService;
import com.ht.biz.service.LibraryService;
import com.ht.biz.service.PoliProRecordService;
import com.ht.biz.service.PolicyDigService;
import com.ht.biz.service.PolicylibService;
import com.ht.biz.service.ProjectlibService;
import com.ht.biz.service.WxPushService;
import com.ht.biz.util.SolrUtils;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.biz.library.Library;
import com.ht.entity.biz.solr.policylib.PoliProRecord;
import com.ht.entity.biz.solr.policylib.Policylib;
import com.ht.entity.biz.solr.projectlib.Projectlib;
import com.ht.entity.biz.solr.recore.SolrSearchRecord;
import com.ht.entity.biz.wx.Push;
import com.ht.entity.policydig.PolicyDig;
import com.ht.utils.CommonsUtil;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "XcxController", description = "微信小程序接口")
@Controller
@RestController
@RequestMapping(value="/ht-biz/xcx")
public class XcxController  extends BaseController {
    @Autowired
    private CollectionDownService collectionDownService;

    @Autowired
    private PolicyDigService policyDigService;

    @Autowired
    private WxPushService wxPushService;
    
    @Autowired
	private PolicylibService policylibService;
	
	@Autowired
	private PoliProRecordService poliProRecordService;
	
	@Autowired
	private ProjectlibService projectlibService;
	
	@Autowired
	private LibraryService libraryService;
	
    @RequestMapping(value = "SaveColletion", method = RequestMethod.POST)
    @ApiOperation(value="添加收藏或取消收藏",notes="添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "收藏对象id",  dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id",  dataType = "String")
    })
    public Respon SaveColletion(CollectionDown collection,String userId){
        Respon respon = this.getRespon();
            //判断是否收藏过
            CollectionDown collectionDown = collectionDownService.getColletionByCreateIdAndTagerId(userId,collection.getTargetId(),2);
            if(null!=collectionDown){ //存在已收藏//执行取消收藏
                if(collectionDownService.removeById(  collectionDown.getId())){
                    respon.success( "取消成功" );
                }else{
                    respon.success( "取消失败" );
                }
            }else{
                //收藏
                collection.setType( 2 );
                collection.setCreateid( userId);
                collection.setCreatedate( new Date(  ) );
                collection.setClazzName("com.fh.entity.system.PolicyDig");
                if( collectionDownService.save( collection )){
                    respon.success( "收藏成功" );
                }else{
                    respon.success( "收藏失败" );
                }
            }
        return respon;
    }


    @RequestMapping(value = "iscollection", method = RequestMethod.POST)
    @ApiOperation(value="判断是否收藏过",notes="判断是否收藏过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "收藏对象id",  dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户id",  dataType = "String")
    })
    public Respon iscollection(String  targetId,String userId){
        Respon respon = this.getRespon();
        //判断是否收藏过
        CollectionDown collectionDown = collectionDownService.getColletionByCreateIdAndTagerId(userId,targetId,2);
        if(null!=collectionDown){
          respon.success( "已收藏" );
        }else{
            respon.error( "未收藏" );
        }
        return respon;
    }

    @RequestMapping(value = "myColletion", method = RequestMethod.POST)
    @ApiOperation(value="我的收藏",notes="我的收藏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id",  dataType = "String")
    })
    public Respon myColletion(String userId){
        Respon respon = this.getRespon();
        try {
            QueryWrapper<CollectionDown> que = new QueryWrapper<>(  );
            //我的收藏，小程的收藏
            que.eq( "createId",userId ).eq( "type" ,2);
            List<CollectionDown> collectionDown = collectionDownService.list( que);
            List<String> list=new ArrayList<String>();
            for(CollectionDown ids : collectionDown){
            	list.add( ids.getTargetId());
            }
            if( list.size()>0){
                List<PolicyDig>  pig = (List<PolicyDig>) policyDigService.listByIds(list  );
                for(PolicyDig p:pig){
    				if(p.getDatetime()!=null){
    					p.setDate(DateUtil.dateToStr(p.getDatetime(), 12));
    				}
    				if(p.getBeginDate()!=null){
    					p.setBeginDates(DateUtil.dateToStr(p.getBeginDate(), 12));
    				}
    				if(p.getEndDate()!=null){
    					p.setEndDates(DateUtil.dateToStr(p.getEndDate(), 12));
    				}
    			}
                respon.success(pig  );
            }else {
            	respon.success(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            respon.error(e);
        }
        return respon;
    }

    /**
     *
     */
    @ApiOperation(value="添加或修改智能推送",notes="添加或修改智能推送")
    @PostMapping(value="/addAnddelPush")
    @ResponseBody
    public Respon  addAnddelPush(Push push) throws Exception{
        Respon respon=this.getRespon();
            QueryWrapper que = new QueryWrapper();
            que.eq( "createId" , push.getCreateId());
            Push  cd1 = wxPushService.getOne( que );
            if (cd1 == null) {
                cd1.setCreateData( new Date() );
                if (wxPushService.save(cd1)){
                    respon.success(null);
                }else{
                    respon.error("添加失败");
                }
            } else { //已收藏
            	push.setId(cd1.getId());
                if(wxPushService.updateById(push)){
                    respon.success(null);
                }else{
                    respon.error("修改失败");
                }
            }
        return respon;
    }

    /**
     * 智能推送
     */
//
//    @RequestMapping(value="/PushlistPage")
//    @ResponseBody
//    public Respon PushlistPage(Page page) throws Exception{
//        Respon respon = this.getRespon();
//        PageData pd = this.getPageData();
//        PageData push = wxPushService.PushByPhoneAndId(pd);
//        if(push!=null){
//            StringBuffer sql = new StringBuffer();
//            String sqlname ="select t.name ,t.sign,t.productType,t.nature,t.province,t.city,t.area ,t.id,t.top,t.endDate,t.title ,t.datetime ,t.beginDate,t.lastmodified from t_policy_dig t  where  t.status=1  ";
//            sql.append(sqlname+"  and t.province LIKE CONCAT(CONCAT('%', '"+push.getString("province")+"'),'%') and ifnull(t.city,'')='' and ifnull(t.area,'')=''");
//            sql.append("union  "+sqlname+"  and  t.province LIKE CONCAT(CONCAT('%', '"+push.getString("province")+"'),'%') and t.city='"+push.getString("city")+"' and ifnull(t.area,'')=''");
//            sql.append("union  "+sqlname+"  and  t.province LIKE CONCAT(CONCAT('%', '"+push.getString("province")+"'),'%') and t.city='"+push.getString("city")+"' and t.area= '"+push.getString("area")+"'");
//			/*	if(StringUtils.isNotBlank(push.getString("regType"))){
//					pd.add("keyword", push.getString("regType").split(" ")[0]);//todo
//				}	*/
//            pd.put("type", "10");
//            pd.put("sql", sql);
//            if(StringUtils.isBlank(push.getString("interesteContext"))){
//                pd.put("nature", "10");
//            }else{
//                String arry =push.getString("interesteContext");
//                String [] nature=arry.split(",");
//                pd.put("nature", nature);
//            }
//            page.setPd(pd);
//            List<PageData> policyDigList=policyDigService.fingPolicyByNatureAndArealistPage(page);
//            if(policyDigList.size()>0){
//                respon.success(policyDigList, page);
//            }else{
//                respon.setCode(1);
//                respon.setMsg("未查询到相关的数据");
//            }
//        }else{
//            respon.error("未开启智能推送");
//        }
//
//        return respon;
//    }
    @ApiOperation(value="判断是否添加过推送",notes="判断是否添加过推送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "createId", value = "用户id",  dataType = "String"),
    })
    @GetMapping(value="/findpushBycreate")
    @ResponseBody
    public Respon findpushBycreate() throws Exception{
        PageData pd = this.getPageData();
        Respon respon = this.getRespon();
        if(StringUtils.isNoneBlank(pd.getString("createId"))){
            QueryWrapper que = new QueryWrapper();
            que.eq( "createId" , pd.getString("createId"));
            Push  cd1 = wxPushService.getOne( que );
            if(cd1!=null){
                respon.success(cd1);
            }else{
                respon.error("未添加");
            }
        }else{
            respon.error("传参有误");
        }
        return respon;
    }
    
    //===============================政策速递===============================//
    @ApiOperation(value="小程序政策速递列表")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省份" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "城市", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regions", value = "（0表示用户点击页面省市区复选框，1表示没有点击）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "countrys", value = "前端国家复选框（值为国家）" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "provinces", value = "前端省份复选框（值为省）" ,dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "citys", value = "前端城市复选框（值为市）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "areas", value = "前端区级复选框（值为区）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "datetime1", value = "开始发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "datetime2", value = "结束发布时间", dataType = "date"),
        @ApiImplicitParam(paramType="query",name = "nature", value = "性质分类", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "productType", value = "政策类型", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "issueCompany", value = "发布单位", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "industry", value = "行业领域", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "apply", value = "申报通知中状态查询（apply==1申报中apply==0申报终止）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "browsecount", value = "决定数据排序（随意传递一个值）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "timetype", value = "小程序范围（0全部，1近一天，2近一周，3近十天，4近一月）", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "total", value = "查询总数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "memory", value = "开启记忆搜索（值为1时寻找cookies）", dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "size", value = "每页条数", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "current", value = "当前页", dataType = "int"),
        @ApiImplicitParam(paramType="query",name = "pages", value = "总页数", dataType = "int"),
	})
	@PostMapping(value="/policyDiglist")
	public Respon list(MyPage<Library> page2,MyPage<PolicyDig> page,String memory,HttpServletRequest request,HttpServletResponse response){
		Respon respon = this.getRespon();
		PageData pd = this.getPageData();
		try {
			
			policyDigService.getCookies(pd,request,memory,response);
			String sql=policyDigService.getSpStr(pd.getString("province"), pd.getString("city"), pd.getString("area"));
			//System.out.println("sql="+sql);
			pd.add("sql", sql);
			String timetype=pd.get("timetype")==null?"":String.valueOf(pd.get("timetype"));
			//小程序范围查询
			Date today=new Date();
			pd.add("today", DateUtil.dateToStr(today, 11)+" 23:59:59");
			if(StringUtils.equals(timetype, "1")){
				Date date=DateUtil.addDay(today, -1);
				pd.add("date", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}else if(StringUtils.equals(timetype, "2")){
				Date date=DateUtil.addDay(today, -7);
				pd.add("date", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}else if(StringUtils.equals(timetype, "3")){
				Date date=DateUtil.addDay(today, -10);
				pd.add("date", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}else if(StringUtils.equals(timetype, "4")){
				Date date=DateUtil.addDay(today, -30);
				pd.add("date", DateUtil.dateToStr(date, 11)+" 00:00:00");
			}
			page=getMyPage(pd);
			List<PolicyDig> list=policyDigService.findV3listPage(page);
			for(PolicyDig p:list){
				if(p.getDatetime()!=null){
					p.setDate(DateUtil.dateToStr(p.getDatetime(), 12));
				}
				if(p.getBeginDate()!=null){
					p.setBeginDates(DateUtil.dateToStr(p.getBeginDate(), 12));
				}
				if(p.getEndDate()!=null){
					p.setEndDates(DateUtil.dateToStr(p.getEndDate(), 12));
				}
			}
			CommonsUtil.filterDate(list);
			//小程序通过后注释掉
//			list=new ArrayList<PolicyDig>();
//			libraryService.page(page2);
//			List<Library> lists=page2.getRecords();
//			for(Library l:lists){
//				PolicyDig p=new PolicyDig();
//				p.setId(l.getId());
//				p.setProvince(l.getProvince());
//				p.setDatetime(l.getCreatedate());
//				if(p.getDatetime()!=null){
//					p.setDate(DateUtil.dateToStr(p.getDatetime(), 12));
//				}
//				p.setCity(l.getCity());
//				p.setArea(l.getArea());
//				String content=l.getContent();
//				content=content.replace("政策", "").replace("政府", "");
//				p.setContent(content);
//				String title=l.getTitle();
//				title=title.replace("政策", "").replace("政府", "");
//				p.setTitle(title);
//				p.setBrowsecount(l.getBrowsecount());
//				list.add(p);
//			}
			
			//小程序通过后注释掉
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
    @ApiOperation(value="小程序政策速递单条数据查询接口")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "id", value = "正速递id",  dataType = "String"),
	})
	@GetMapping(value="/findById")
	public Respon findById(String id){
		Respon respon = this.getRespon();
		PolicyDig policyDig=new PolicyDig();
		try {
			if(!StringUtils.isBlank(id)){
				policyDig=policyDigService.findById(id);
				//小程序通过后注释掉
//				Library l=libraryService.getById(id);
//				if(policyDig==null){
//					policyDig=new PolicyDig();
//				}
//				policyDig.setId(l.getId());
//				policyDig.setProvince(l.getProvince());
//				policyDig.setDatetime(l.getCreatedate());
//				if(policyDig.getDatetime()!=null){
//					policyDig.setDate(DateUtil.dateToStr(policyDig.getDatetime(), 12));
//				}
//				policyDig.setCity(l.getCity());
//				policyDig.setArea(l.getArea());
//				String content=l.getContent();
//				content=content.replace("政策", "").replace("政府", "");
//				policyDig.setContent(content);
//				String title=l.getTitle();
//				title=title.replace("政策", "").replace("政府", "");
//				policyDig.setTitle(title);
//				policyDig.setBrowsecount(l.getBrowsecount());
				//小程序通过后注释掉
				if(policyDig.getDatetime()!=null){
					policyDig.setDate(DateUtil.dateToStr(policyDig.getDatetime(), 12));
				}
				if(policyDig.getBeginDate()!=null){
					policyDig.setBeginDates(DateUtil.dateToStr(policyDig.getBeginDate(), 12));
				}
				if(policyDig.getEndDate()!=null){
					policyDig.setEndDates(DateUtil.dateToStr(policyDig.getEndDate(), 12));
				}
				policyDig.setBrowsecount(policyDig.getBrowsecount()+1);
				policyDigService.edit(policyDig);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error();
		}
		respon.success(policyDig);
		return respon;
	}
  //===============================政策速递===============================//
  //===============================政策库===============================//
    /*
	 * 微信小程序接口
	 */
    @ApiOperation(value="小程序政策库数据接口")
    @ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year1", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year2", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "projecname", value = "项目名",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "chargedept", value = "主管部门",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "technology", value = "技术领域",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regions", value = "0表示点击框地区，1表示正常地区选择",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "countrys", value = "点击框国家",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "provinces", value = "点击框省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "citys", value = "点击框市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "areas", value = "点击框区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "currcord", value = "点击翻页记录",  dataType = "String"),
	})
	@PostMapping("policyliblist")
	public Respon policyliblist(String userId,MyPage page,String companyid){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			if(!StringUtils.isBlank(userId)){
				page=getMyPage(pd);
				//String keyword=pd.get("keyword");
				String fq=null;
				if(StringUtils.isNotBlank(pd.getString("id"))){
					fq=policylibService.getQueryById(pd.getString("id"));
				}else{
					fq=policylibService.getSpQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year"));
				}
				//System.out.println("fq="+fq);
				page=SolrUtils.getList(page, new Policylib(), false,Const.solrCore_policylib,"*:*",fq,"year desc",pd.getString("keyword"),false);
				//-----------------记录用户足迹----------------//
				PoliProRecord p=new PoliProRecord();
				p.setCounts(Double.valueOf(page.getTotal()));
				p.setIp(RequestUtils.getIp());
				p.setInterfaceName("/ht-biz/policylib/winxinlist");
				p.setLocal("政策库");
				p.setCreatedate(new Date());
				p.setCreateid(userId);
				p.setRegionid(companyid);
				poliProRecordService.save(p);
				//-----------------记录用户足迹----------------//
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
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
  //===============================政策库===============================//
  //===============================立项库===============================//
	/*
	 * 微信小程序接口
	 */
    @ApiOperation(value="小程序立项库数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "String"),
		@ApiImplicitParam(paramType="query",name = "keyword", value = "关键字",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year1", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "year2", value = "年份",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "name", value = "项目名称",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "special", value = "专题",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "directorUnit", value = "主管单位",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "province", value = "省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "city", value = "市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "area", value = "区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "regions", value = "0表示点击框地区，1表示正常地区选择",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "countrys", value = "点击框国家",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "provinces", value = "点击框省",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "citys", value = "点击框市",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "areas", value = "点击框区",  dataType = "String"),
        @ApiImplicitParam(paramType="query",name = "currcord", value = "点击翻页记录",  dataType = "String"),
	})
	@PostMapping("projectliblist")
	public Respon projectliblist(String userId,MyPage page,String companyid){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			if(!StringUtils.isBlank(userId)){
				page=getMyPage(pd);
				String fq=null;
				if(StringUtils.isNotBlank(pd.getString("id"))){
					fq=projectlibService.getQueryById(pd.getString("id"));
				}else{
					fq=projectlibService.getSpQueryStr(pd.getString("keyword"), pd.getString("province"), pd.getString("city"), pd.getString("area"), pd.getString("year"));
				}
				//System.out.println("fq="+fq);
				page=SolrUtils.getList(page, new Projectlib(), false,Const.solrCore_projectlib,"*:*",fq,"yearProject desc",pd.getString("keyword"),false);
				//-----------------记录用户足迹----------------//
				PoliProRecord p=new PoliProRecord();
				p.setCounts(Double.valueOf(page.getTotal()));
				p.setIp(RequestUtils.getIp());
				p.setInterfaceName("/ht-biz/policylib/projectlib");
				p.setLocal("立项库");
				p.setCreatedate(new Date());
				p.setCreateid(userId);
				p.setRegionid(companyid);
				poliProRecordService.save(p);
				//-----------------记录用户足迹----------------//
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
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
  //===============================立项库===============================//
}
