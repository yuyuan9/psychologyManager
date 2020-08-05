package com.ht.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.*;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Userbank;
import com.ht.entity.biz.collection.CollectionDown;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value = "MyController", description = "前端个人中心")
@RestController
@RequestMapping(value="/ht-biz/myinfo")
public class MyController extends BaseController {

    @Autowired
    private CollectionDownService collectionDownService;

    @Autowired
    UserbankService userbanKservice;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private GoldcoinRuleRecordService goldcoinRuleRecordService;
    @Autowired
    private SerprociderService serprociderService;
    //1添加/取消 收藏
    @RequestMapping(value = "SaveColletion", method = RequestMethod.POST)
    @ApiOperation(value="添加收藏或取消收藏",notes="添加收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "收藏对象id",  dataType = "String"),
            @ApiImplicitParam(name = "clazzName", value = "类型 文库：com.fh.entity.system.Library 资源包：com.ht.entity.biz.library.ResourcePage",  dataType = "String")
    })
    public Respon SaveColletion( CollectionDown collection){
        Respon respon = this.getRespon();
        if(null!=LoginUserUtils.getLoginUser()){
            //判断是否收藏过
            CollectionDown collectionDown = collectionDownService.getColletionByCreateIdAndTagerId(LoginUserUtils.getLoginUser().getUserId(),collection.getTargetId(),0);
            if(null!=collectionDown){ //存在已收藏//执行取消收藏
                if(collectionDownService.removeById(  collectionDown.getId())){
                    respon.success( "取消成功" );
                }else{
                    respon.success( "取消失败" );
                }
            }else{
                //收藏
                collection.setType( 0 );
                collection.setCreateid( LoginUserUtils.getLoginUser().getUserId());
                collection.setCreatedate( new Date(  ) );
               if( collectionDownService.save( collection )){
                   respon.success( "收藏成功" );
               }else{
                   respon.success( "收藏失败" );
               }
            }
        }else{
            respon.loginerror( "登录信息失效请重新登录" );
        }
        return respon;
    }

    @RequestMapping(value = "iscollection", method = RequestMethod.POST)
    @ApiOperation(value="判断是否收藏过",notes="判断是否收藏过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "收藏对象id",  dataType = "String")
   /*         @ApiImplicitParam(name = "userId", value = "用户id",  dataType = "String")*/
    })
    public Respon iscollection(String  targetId){
        Respon respon = this.getRespon();
        //判断是否收藏过
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            CollectionDown collectionDown = collectionDownService.getColletionByCreateIdAndTagerId(user.getUserId(),targetId,0);
            if(null!=collectionDown){
                respon.success("已收藏");
            }else{
                respon.error("未收藏");
            }
        }else{
            return null;
        }

        return respon;
    }


    @RequestMapping(value = "delect", method = RequestMethod.POST)
    @ApiOperation(value="删除收藏",notes="删除收藏")
    public Respon delectcolletion(@ApiParam(name="id" ,value="id") String id){
        Respon  respon = new Respon(  );
       if( collectionDownService.removeById( id )){
           respon.success( "删除成功" );
       }else{
           respon.error( "删除失败" );
       }
       return respon;
    }

    //查询我的收藏 /下载   //收藏包含文库，资源包
    @RequestMapping(value = "findcolletionbyUserId", method = RequestMethod.POST)
    @ApiOperation(value="查询我的收藏",notes="查询我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型 0：收藏 1：下载", dataType = "int")
    })
    public Respon findcolletionbyUserId(MyPage page){
        Respon  respon = new Respon(  );
        if(null!=LoginUserUtils.getLoginUser()){
            PageData pd = this.getPageData();
            if( pd.get( "type" )==null||pd.get("type")==""){
                pd.put("type",null );
            }
            pd.put( "createid" ,LoginUserUtils.getLoginUser().getUserId());
            page.setPd(pd);
            List<PageData> list =  collectionDownService.findcolletionbyUserId(page);
            respon.success( list,page);
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "status", value = "类型 0：待审核 1：审核通过 2：不通过", dataType = "int")
    })
    @PostMapping(value = "myload")
    @ApiOperation(value = "我的文档",notes="我的文档")
    public Respon myload(MyPage page){
        Respon respon = this.getRespon();
        if(null!=LoginUserUtils.getLoginUser()){
            PageData pd = this.getPageData();
            pd.put( "createId" ,LoginUserUtils.getLoginUser().getUserId());
            page.setPd(pd);
            List<PageData> list =  libraryService.myload(page);
            respon.success( list,page);
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }


    @RequestMapping(value = "myserviceinfo", method = RequestMethod.POST)
    @ApiOperation(value="我的基本信息服务商",notes="我的基本信息服务商")
    public Respon delectcolletion(){
        Respon  respon = new Respon(  );
            SysUser user = LoginUserUtils.getLoginUser();
            if(null!=user){
                if(UserType.SERVICE_PROVIDER.name().equals( user.getUserType() )){
                    PageData pd = new PageData(  );
                    pd.put( "userId",user.getUserId() );
                    PageData    userinfo =  serprociderService.findById(pd);
                    respon.success( userinfo );
                }else{
                    respon.error( "参数有误" );
                }
            }else{
                respon.loginerror( "请登录" );
            }

        return respon;
    }

    /**
     * 我的金币
     * @return
     */
    @RequestMapping(value = "mycole", method = RequestMethod.POST)
    @ApiOperation(value="我的金币",notes="我的金币")
    public Respon getcole(){
        Respon  respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){

            Double d1=goldcoinRuleRecordService.mygolds(user.getUserId());
            d1= d1==null?0d:d1;
            respon.success( d1 );
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;

    }


  /*  @RequestMapping(value = "mycolltion", method = RequestMethod.GET)
    @ApiOperation(value="我的收藏",notes="我的收藏")
    public Respon mycolltion(MyPage page){
        Respon respon = this.getRespon();
        if(null!=LoginUserUtils.getLoginUser()){
            PageData pd = new PageData(  );
            pd.put( "createId", LoginUserUtils.getLoginUser().getUserId());
            if( pd.get( "libtype" )==null||pd.get("libtype")==""){
                pd.put("libtype",null );
            }
            page.setPd( pd );
            List<PageData> list = libraryService.findByPage(page);
            respon.success(list  );
        }else{
            respon.loginerror( "登录信息失效请重新登录" );
        }
        return respon;
    }*/

    //我的银行卡列表
    @ApiOperation(value="我的银行卡列表",notes="我的银行卡列表")
    @RequestMapping(value = "getmybankcar", method = RequestMethod.GET)
    public Respon getmybankcar(){
        Respon  respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            QueryWrapper<Userbank> queryWrapper = new QueryWrapper<Userbank>(  );
            queryWrapper.eq( "createid",user.getUserId() );
            List<Userbank>  userbanks = userbanKservice.list( queryWrapper );
            respon.success( userbanks );
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }
    @ApiOperation(value="添加银行卡",notes="添加银行卡")
    @RequestMapping(value = "insertbankcar", method = RequestMethod.POST)
    public Respon insertbankcar(Userbank userbank){
        Respon  respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
          userbank.setCreateid( user.getUserId() );
          if(userbanKservice.save(  userbank)){
              return respon.success( "添加成功" );
          }
          return  respon.error( "添加失败" );
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }
    @ApiOperation(value="删除银行卡",notes="删除银行卡")
    @RequestMapping(value = "removebyId", method = RequestMethod.POST)
    public Respon removebyId(String id){
        Respon  respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            if(userbanKservice.removeById(  id)){
                return respon.success( "删除成功" );
            }
            return  respon.error( "删除成功" );
        }else{
            respon.loginerror( "请登录" );
        }
        return respon;
    }
}
