package com.ht.biz.controller;

import com.ht.biz.service.CollectionDownService;
import com.ht.biz.service.LibraryService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.ResoucePageService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.*;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.library.Library;
import com.ht.entity.biz.library.ResourcePage;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文库控制器
 * @author jied
 *
 */

@Api(value = "ResoucepageController", description = "前端资源包管理")
@Controller
@RestController
@RequestMapping(value="/ht-biz/resouce")
public class ResoucepageController extends BaseController{
	
    @Autowired
    private ResoucePageService resoucePageService;
    @Autowired
    private PointRedemptionService pointRedemptionService;
    @Autowired
    private CollectionDownService collectionDownService;
    @PostMapping("listpage")
    public Respon listpage(MyPage myPage){
        Respon  respon = this.getRespon();
        PageData pd = this.getPageData();
        if( pd.get( "retype" )==null||pd.get("retype")==""){
            pd.put("retype",null );
        }

        if(pd.get( "classify" )==null||pd.get("classify ")==""){
            pd.put("classify",null );
        }
        myPage.setPd( pd );
        try {
            MyPage<PageData> list = resoucePageService.findListPage(myPage  );
            respon.success( list );
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        return  respon;
    }

    @GetMapping("findbyid")
    @ApiOperation(value="跳转到详情页面并获取相关信息",notes="根据id查询资源包详情")
    public ModelAndView findbyid(@ApiParam(name="id" ,value="文库id") String  id, ModelMap mode,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView(  );
        Respon  respon = this.getRespon();
        try {
            ResourcePage list = resoucePageService.getById(id  );
            list.setBrowsecount( (list.getBrowsecount()==null)?1:list.getBrowsecount()+1 );
            resoucePageService.saveOrUpdate(list  );
            mode.addAttribute( "list",list );
            modelAndView.addObject(mode);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        if(Tools.pcphone(request)){
            return  this.setViewName("library/package_details");
        }else{
            return this.setViewName("app/package/detail");
        }

    }

//    //下载资源包
//    @PostMapping ("downRouce")
//    @ApiOperation(value="下载资源包",notes="")
//    public Respon downfile(@ApiParam(name="资源包id" ,value="资源包id")String id,HttpServletResponse response){
//        Respon respon = new Respon(  );
//        SysUser user = LoginUserUtils.getLoginUser();
//        if(null!=user){
//            ResourcePage resourcePage = resoucePageService.getById(id  );
//            //下载资源包的需要的honey
//            double honey =  Double.valueOf(resourcePage.getBenefit());
//            //获取账号的honey
//            double myhoney = pointRedemptionService.getWaterTotal( user.getUserId() );
//            if(myhoney>honey){
//               String  path = resourcePage.getZippath();
//               UploadhttpClient uploadhttpClient = new UploadhttpClient();
//                try {
//                    uploadhttpClient.downfile(path ,response );
//                    //添加我下载记录
//                    if(null==collectionDownService.getColletionByCreateIdAndTagerId(  user.getUserId(),id )){
//                        CollectionDown collection = new CollectionDown();
//                        collection.setType( 1 );//下载
//                        collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
//                        collection.setTargetId( id );
//                        collection.setClazzName("com.ht.entity.biz.library.ResourcePage");
//                        collection.setCreatedate( new Date(  ) );
//                        collectionDownService.save(  collection);
//                    }
//                    RewardUtil.disHoney(String.valueOf(RewardRule.Code.library_resource), Double.valueOf(-honey), user.getUserId(), user.getUserId(),user.getCompanyid());
//                } catch (IOException e) {
//                    respon.error( "系统异常" );
//                    e.printStackTrace();
//                }
//            }else{
//                respon.error( "honey值不够哦《~~》" );
//            }
//        }else{
//            respon.loginerror( "请登录" );
//        }
//            return respon;
//    }

    @GetMapping(value = "/seekExperts")
    @ApiOperation(value="访问文件接口",notes="根据id查询String[]")
    public void createFolw(@ApiParam(name="id" ,value="文库id")String id, String sessionvalue , HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        ResourcePage list = resoucePageService.getById(id  );
        if(null!=list){
            URL url;
            try {
                if(StringUtils.isNotBlank(list.getIntrpage())){
                    try {
                        url = new URL(Const.filepaht+list.getIntrpage());
                    } catch (Exception e) {
                        return ;
                    }
                    HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
                    String messagePic = urlconn.getHeaderField(0);//文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
                    if (messagePic.startsWith("HTTP/1.1 200")) {
                        OutputStream os = null;
                        os = response.getOutputStream();
                        InputStream is = url.openStream();
                        // InputStream is=urlconn.getURL().openStream();
                        int count = 0;
                        //int i = is.available(); // 得到文件大小
                        int i =urlconn.getContentLength();
                        System.out.println( "i:"+i );
                        byte[] buffer = new byte[i];
                        while ((count = is.read( buffer )) != -1) {
                            os.write( buffer, 0, count );
                            os.flush();
                            //    this.setTicket(this.get32UUID()); //重新设置token
                        }
                        is.close();
                        os.close();
                    }
                }else{
                    FileUtil fileUtil = new FileUtil();
                    fileUtil.geterro(response);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    //查询单点资源包的价格
    @PostMapping ("getdownfilehoney")
    @ApiOperation(value="获取要下载的资源包的honey",notes="")
    public Respon getdownfilehoney(@ApiParam(name="ids" ,value="资源包id")String id){
        Respon respon = this.getRespon();
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            double myhoney = pointRedemptionService.getWaterTotal( user.getUserId() );
            try {
                String[] moneyt =new String [2];
                String honey ="0";
                if(null!=collectionDownService.getColletionByCreateIdAndTagerId(  user.getUserId(),id,1)){
                    honey="0";
                }else{
                    String rhoney = resoucePageService.getById(id  ).getBenefit();
                    if(rhoney.isEmpty()){
                        honey="0";
                    }else{
                        honey = rhoney ;
                    }
                }
                moneyt[0]= myhoney+"";
                moneyt[1]= honey;
                respon.success( moneyt);
            }catch (Exception e){
                e.printStackTrace();
                respon.error( "系统异常" );
            }

            }else{
            respon.error("请先登录");
        }

        return respon;
    }
    //下载资源包
    @GetMapping ("downfile")
    @ApiOperation(value="下载文件",notes="")
    public void downfile(@ApiParam(name="id" ,value="资源包id")String id,HttpServletResponse response) {
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            //获取下载文件需要的honey;
            ResourcePage resourcePage = resoucePageService.getById( id );
            if (null != resourcePage) {
                int honey;
                if (resourcePage.getBenefit().isEmpty()) {
                    honey = 0;
                } else {
                    honey = Integer.parseInt( resourcePage.getBenefit());
                }
                //获取用的honey
                double myhoney = pointRedemptionService.getWaterTotal( user.getUserId() );
                if (myhoney >= honey) {
                    String zip = resourcePage.getZippath();
                    UploadhttpClient uploadhttpClient = new UploadhttpClient();
                    try {
                        uploadhttpClient.downfile(zip ,response );
                        resourcePage.setDownloadcount( resourcePage.getDownloadcount()+1 ); //加载下载量
                        resoucePageService.saveOrUpdate( resourcePage );
                        //添加我下载记录
                        if(null==collectionDownService.getColletionByCreateIdAndTagerId(  user.getUserId(),id ,1)){
                            CollectionDown collection = new CollectionDown();
                            collection.setType( 1 );//下载
                            collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
                            collection.setTargetId( id );
                            collection.setClazzName("com.ht.entity.biz.library.ResourcePage");
                            collection.setCreatedate( new Date(  ) );
                            collectionDownService.save(  collection);
                            RewardUtil.disHoney(String.valueOf(RewardRule.Code.library_resource), Double.valueOf(-honey), user.getUserId(), user.getUserId(),user.getCompanyid());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }


}

