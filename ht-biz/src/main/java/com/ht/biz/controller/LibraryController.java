package com.ht.biz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ht.biz.service.*;
import com.ht.biz.util.SearchRecordUtil;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.geelink.entity.GeelinkResoupage;
import com.ht.commons.support.geelink.entity.Librarygelink;
import com.ht.commons.support.geelink.jsonquery.Geelinkdatacreate;
import com.ht.commons.utils.*;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.library.Library;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.*;


import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.*;

import com.ht.commons.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ht.entity.shiro.constants.UserType.SERVICE_PROVIDER;

/**
 * 文库控制器
 * @author jied
 *
 */

@Api(value = "LibraryController", description = "前端文库管理管理")
@RestController
@RequestMapping(value="/ht-biz/library")
public class LibraryController extends BaseController{
	
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private CollectionDownService collectionDownService;
    @Autowired
    private PointRedemptionService pointRedemptionService;
    @Autowired
    private SerprociderService serprociderService;
    @Autowired
    private ResoucePageService resoucePageService;
    @ApiOperation(value="跳转到文库页面",notes="")
    @RequestMapping(value="/{page}")
    public ModelAndView page(@PathVariable String page) {
        return this.setViewName("library/"+page);
    }

    @Value("${api.devproduct}")
    private boolean devproduct;






    //推送资源包
    @RequestMapping(value={"addgeelinkpage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String addpage() throws IOException {
        List<GeelinkResoupage> list = resoucePageService.geelinkfindall();
        for (GeelinkResoupage info : list) {
            info.setLibtype( 9 );
            info.setUploadusername("高企云");
        }
        for (GeelinkResoupage info : list) {
            List<Object> listk = new ArrayList<>(  );
            listk.add(info);
            Geelinkdatacreate cd = new Geelinkdatacreate();
            String str = cd.jsondataPolicyDig(listk);
         /*   System.out.println(str  );*/
        }

        return null;
    }
    //推送文库
    @RequestMapping(value={"addgeelinklibrary"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String addgeelinklibrary() throws IOException {
        List<Librarygelink> list = libraryService.geelinkfindall();

        for (Librarygelink info : list) {
            List<Object> listk = new ArrayList<>(  );
            listk.add(info);
            Geelinkdatacreate cd = new Geelinkdatacreate();
            String str = cd.jsondataPolicyDig(listk);
//            System.out.println(str  );
        }

        return null;
    }
    @RequestMapping(value={"updategeelinklibrary"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String pushgeelink()
    {
        List<Librarygelink> list = libraryService.geelinkfindall();
        for (Librarygelink info : list) {
            List object = new ArrayList();
            object.add(info);
            Geelinkdatacreate cd = new Geelinkdatacreate();
            String str = cd.updatedata("ADD", "library", object);
        }
        return null;
    }
    //查询所有的文库
    @RequestMapping(value = "findlibraryall", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "keywords", value = "关键字查询", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省份", dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String"),
            @ApiImplicitParam(name = "libtype", value = "政策类型", dataType = "int",example ="公示名录0 培训资料1 模板范文2  政府文件5 政策解读4 其他3 申报通知6" ),
            @ApiImplicitParam(name = "type", value = "项目类型", dataType = "String")
    })
    @ApiOperation(value="文库列表",notes="")

    public Respon findlibraryall(MyPage page, Integer current, Integer size, String keywords, String province, String city, String area, Integer libtype, String type)
    {
        PageData  pd = this.getPageData();
        page.setPd( pd );
        Respon respon = new  Respon();
        if(!devproduct){
            try {
                if(null==current){
                    current =1;
                }
                if(null==size){
                    size=10;
                }
                String  ltype ="";
                if(null!=libtype){
                    ltype =   getltyp(libtype);
                }
                String  list = Geelinkdatacreate.querylibrary(keywords,current,size, province,city,area,type,ltype );
                JSONObject object = JSON.parseObject(list);
                String response = object.getString( "response" );
                JSONObject oresponse = JSON.parseObject(response);
                double total = oresponse.getDouble( "numFound" );
              /*  String list3 = oresponse.getString( "docs" );
                JSONObject docs = JSON.parseObject(list3);*/
                JSONArray jons = oresponse.getJSONArray( "docs" );
                double pagesize =  total/size;
                page.setPages(new Double(Math.ceil(pagesize)).intValue());
                page.setCurrent( current );
                page.setTotal( new Double(Math.ceil(total)).intValue() );
                return respon.success( jons ,page);
            }catch (Exception e){
                e.printStackTrace();
                return respon.error( e );
            }
        }

        if( pd.get( "libtype" )==null||StringUtils.isBlank(String.valueOf(pd.get( "libtype" )))){
            //pd.put("libtype",null );
            pd.remove("libtype");
        }
        /*if(StringUtils.isNotBlank( pd.getString( "keywords" ) )){
            pd.put( "keywords", RequestUtils.getParticiple(pd.getString( "keywords" ), "%"));
        }*/
        try {
            List<PageData> list = libraryService.findByPage(page);
            if(StringUtils.isNotBlank( pd.getString( "keywords" ) )){
            	SearchRecordUtil.searchRecord(pd.getString( "keywords" ), (int)page.getTotal(),"t_library");
            }
            respon.success( list,page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respon;
    }

    public String getltyp(int type)
    {
        String ltype = "";
        switch (type)
        {
            case 0:
                ltype = "分类/公示名录";
                break;
            case 1:
                ltype = "分类/培训资料";
                break;
            case 2:
                ltype = "分类/模板范文";
                break;
            case 3:
                ltype = "分类/其他";
                break;
            case 4:
                ltype = "分类/政策解读";
                break;
            case 5:
                ltype = "分类/政府文件";
                break;
            case 6:
                ltype = "分类/申报通知";
                break;
            case 9:
                ltype = "分类/资源包";
                break;
            default:
                ltype = "";
        }

        return ltype;
    }

    //根据id查询相关的文库并更新浏览量
    @GetMapping("findLibrayById")
    @ApiOperation(value="根据id查询文库详情",notes="根据id查询文库详情")
    public ModelAndView findLibrayById(@ApiParam(name="id" ,value="文库id") String id, ModelMap mode,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(  );
        try {
            Library library =libraryService.findbyId(id);
            if(null!=library){
                library.setBrowsecount( (library.getBrowsecount()==null)?1:library.getBrowsecount()+1 );
                libraryService.updateById(library);
                mode.addAttribute( "libary",library );
                modelAndView.addObject(mode);
                /*

                　modelAndView.addAttribute("user",user );
                modelAndView.addObject(  library);*/
            }else{
            	return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Tools.pcphone(request)){
            return this.setViewName("library/details");
        }else{
            return this.setViewName("app/library/detail");
        }


    }
    //文库相关推荐
    @GetMapping("promotelibrary")
    @ApiOperation(value="根据id查询推荐文库",notes="根据id查询文库详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "title", value = "文库标题", dataType = "String")
    })
    public Respon promotelibrary(MyPage page){
        Respon respon = new Respon();
        try {
            PageData pd = this.getPageData();
            if(StringUtils.isNotBlank( pd.getString( "title" ))){
               String keywords = RequestUtils.getParticiple(pd.getString( "title" ), ",");
               String word = keywords.substring(1,keywords.length()-1);
               String[] strings = word.split( "," );
                StringBuffer sql = new StringBuffer();
               for(int i=0;i< strings.length; i++){
                   if(i==0){
                       sql.append( "and ( lib.title LIKE CONCAT(CONCAT('%', '"+strings[i]+"'),'%') " );
                   }else{
                       sql.append( "or lib.title LIKE CONCAT(CONCAT('%', '"+strings[i]+"'),'%') " );

                   }
                   if(i==strings.length-1){
                       sql.append(")");
                   }
               }
               pd.put( "keyword" ,sql.toString());
            }
            page.setPd( pd );
            List<PageData> list = libraryService.promotelibrary(page);
            respon.success(list  );
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }

    //删除
    @PostMapping("deleteById")
    @ApiOperation(value="根据id删除审核没通过文库",notes="根据id查询文库详情")
    public Respon deleteById(@ApiParam(name="id" ,value="文库id")String id){
        Respon respon = new Respon();
        if(libraryService.removeById( id )){
            respon.success("删除成功"  );
        }else{
            respon.error( "删除失败" );
        }
        return respon;
    }

    //查询单点资源包的价格
    @PostMapping ("getdownfilehoney")
    @ApiOperation(value="获取要下载的文库的honey",notes="根据id查询String[]")
    public Respon getdownfilehoney(@ApiParam(name="ids" ,value="文库id")String[] ids, MyPage page){
        Respon respon = this.getRespon();
        PageData pd = this.getPageData();
        page.setPd( pd );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
        	  double myhoney = pointRedemptionService.getWaterTotal( user.getUserId() );
              int money = 0;
              if(null!=ids && ids.length>0){
                  List<String > id = new ArrayList<>(  );
                  for(String ida:ids){
                          id.add( ida );
                  }
                  try {
                    double[] moneyt =new double[2];
                      double honey ;
                  if(null!=collectionDownService.getColletionByCreateIdAndTagerId(  user.getUserId(),ids[0] ,1)){
                      honey=0;
                  }else{
                      honey = gethoney(id);
                  }
                  	moneyt[0]= myhoney;
                  	moneyt[1]= honey;
                    respon.success( moneyt,page);
                  }catch (Exception e){
                      e.printStackTrace();
                      respon.error( "系统异常" );
                  }

              }else{
                  respon.error( "请选你要下载的资源" );
              }
        }else{
        	respon.error("请先登录");
        }

        return respon;
    }
    public int gethoney(List<String> id){
        return   libraryService.getdownfilehoney(id);
    }
    //下载文库文件
    @GetMapping ("downfile")
    @ApiOperation(value="下载文件",notes="根据id查询String[]")
    public void downfile(@ApiParam(name="ids" ,value="文库id")String[] ids,HttpServletResponse response){
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=ids && ids.length>0 && null!=user){
            List<String> paths = new ArrayList<>(  );
            for(String id:ids){
                paths.add(id);
            }
            //获取下载文件需要的honey;
            double honey = gethoney(paths);
            //获取用的honey
            double myhoney = pointRedemptionService.getWaterTotal( user.getUserId() );

            if(myhoney>=honey){
                for(String id:ids){
                    Library library=  libraryService.findbyId(  id);
                    if(null!=library){
                    	 String  path ="";
                    	 path = library.getOriginalfile();
                        if(StringUtils.isBlank(path)){
                        	path =library.getPdfpath();
                        }
                        UploadhttpClient uploadhttpClient = new UploadhttpClient();
                        try {
                            uploadhttpClient.downfile(path ,response );
                            int down = 0;
                            if(library.getDowncount()!=null){
                                down =library.getDowncount();
                            }
                            library.setDowncount( down +1); //加载下载量
                            libraryService.saveOrUpdate( library );
                            //添加我下载记录
                            if(null==collectionDownService.getColletionByCreateIdAndTagerId(  user.getUserId(),id ,1)){
                                CollectionDown collection = new CollectionDown();
                                collection.setType( 1 );//下载
                                collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
                                collection.setTargetId( id );
                                collection.setClazzName("com.fh.entity.system.Library");
                                collection.setCreatedate( new Date(  ) );
                                collectionDownService.save(  collection);
                                RewardUtil.disHoney(String.valueOf(RewardRule.Code.download_file), Double.valueOf(-honey), user.getUserId(), user.getUserId(),user.getCompanyid());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
    //根据idcha查询list集合
    @GetMapping("getlistByids")
    @ApiOperation(value = "根据id查询list集合", notes = "")
    public Respon getlistByids(@ApiParam(name = "ids") String ids) {
        Respon respon = new Respon();
        PageData pd =this.getPageData();
        try {
            if (org.apache.commons.lang3.StringUtils.isNoneBlank( ids )) {
                String[] idsl = ids.split( "," );
                List<String> idlists = new ArrayList<>();
                for (String idlist : idsl) {
                    idlists.add( idlist );
                }
                pd.put( "list" ,idsl);
                MyPage page =this.getMyPage( pd );
                List<PageData> list = libraryService.getlistByids(page);
                respon.success( list ,page);
            } else {
                respon.success( "请选择要添加的文库" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

    @GetMapping(value = "/seekExperts")
    @ApiOperation(value="访问文件接口",notes="根据id查询String[]")
    public void createFolw(@ApiParam(name="id" ,value="文库id")String id, String sessionvalue , HttpServletRequest request, HttpServletResponse response) throws IOException {
        Library library = libraryService.findbyId(id);
        if(null!=library) {
            URL url;
            try {
                if (StringUtils.isNotBlank( library.getOsspath() )) {
                    try {
//                        url = new URL( Const.filepaht + library.getPdfpath() );
                        url = new URL( library.getOsspath() );
                    } catch (Exception e) {
                        return;
                    }
                    HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
                    String messagePic = urlconn.getHeaderField( 0 );//文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
                    if (messagePic.startsWith( "HTTP/1.1 200" )) {
                        OutputStream os = null;
                        os = response.getOutputStream();
                        InputStream is = url.openStream();
                        // InputStream is=urlconn.getURL().openStream();
                        int count = 0;
                        //int i = is.available(); // 得到文件大小
                        int i = urlconn.getContentLength();
                        System.out.println( "i:" + i );
                        byte[] buffer = new byte[i];
                        while ((count = is.read( buffer )) != -1) {
                            os.write( buffer, 0, count );
                            os.flush();
                            //    this.setTicket(this.get32UUID()); //重新设置token
                        }
                        is.close();
                        os.close();
                    }
                } else {
                    FileUtil fileUtil = new FileUtil();
                    fileUtil.geterro(response);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //根据id查询相关的文库并更新浏览量
    @GetMapping("findbyId")
    @ApiOperation(value="根据id查询文库详情",notes="根据id查询文库详情")
    public Respon findLibrayById(@ApiParam(name="id" ,value="文库id") String id){
        Respon respon = new  Respon();
        try {
            Library library =libraryService.findbyId(id);
            if(null!=library){
                library.setBrowsecount( (library.getBrowsecount()==null)?1:library.getBrowsecount()+1 );
                libraryService.updateById(library);
                respon.success(  library);
                /*

                　modelAndView.addAttribute("user",user );
                modelAndView.addObject(  library);*/
            }else{
                respon.error();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return respon;
    }


    //添加
    //yes
    @PostMapping("savalibrary")
    @ApiOperation(value = "添加library", notes = "添加library")
    public Respon savalibrary(@ApiParam(name = "TLibrary对象", value = "传入json格式", required = true) @RequestBody Library library) {
        Respon respon = new Respon();
        try {
            if (null != getLoginInfo()) {
                library.setCreateid( LoginUserUtils.getLoginUser().getUserId() );
                library.setStatus( 0 );
                library.setCreatedate( new Date() );
                boolean flag = libraryService.saveOrUpdate( library );
                if (flag) {
                    respon.success( "添加成功" );
                    MsgUtil.addMsg( WorkReminder.Work.doc_upload_success.getName(),null,  LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getCompanyid());
                } else {
                    respon.error( "添加失败" );
                }
            } else {
                respon.loginerror( "登录信息失效请重新登录" );
            }
        } catch (Exception e) {
            respon.error( "添加失败" );
        }
        return respon;
    }

    //获取上传文件用户的信息
    @GetMapping ("getuserinfo")
    @ApiOperation(value = "获取上传用户的名称", notes = "获取上传用户的名称")
    public Respon getuploaduserinfo(){
        Respon respon = this.getRespon();
        SysUser user = LoginUserUtils.getLoginUser();
        String username ="";
        if(null!=user){
            //服务商上传
            if(SERVICE_PROVIDER.name().equals( user.getUserType() )){
                PageData pd = new PageData(  );
                pd.put( "userId",user.getUserId() );
                PageData serpro =   serprociderService.findById( pd);
                username = serpro.getString( "banme" );
            } else if(UserType.ADMIN.name().equals( user.getUserType() )){ //管理员

                username= Const.adminupload ;
            }else{
                username =user.getTrueName();
            }
            if(StringUtils.isNotBlank( username )){
                respon.success( username );
            }else{
                String phone = user.getPhone();
                respon.success( phone.substring(0, 3) + "****" + phone.substring(7, phone.length()) );
            }
        }else{

            respon.error( "请登录");
        }
        return respon;
    }
}
