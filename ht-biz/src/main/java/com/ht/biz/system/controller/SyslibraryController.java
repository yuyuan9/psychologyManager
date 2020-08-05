package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.biz.service.LibraryService;
import com.ht.biz.service.ResoucePageService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;

import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UuidUtil;
import com.ht.entity.biz.honeymanager.GoldcoinRule;
import com.ht.entity.biz.library.Library;
import com.ht.entity.biz.library.ResourcePage;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ht.entity.shiro.constants.UserType.SERVICE_PROVIDER;


@Controller
@RestController
@RequestMapping(value = "/sys/ht-biz/syslibrary")
@Api(value = "SyslibraryController", description = "后台文库")
public class SyslibraryController extends BaseController {
    @Autowired
    private LibraryService libraryService;
    ResoucePageService resoucePageService;
    @Autowired
    private SerprociderService serprociderService;
    //查询所有的文库
    @PostMapping(value = "findlibraryall")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "keywords", value = "关键字查询", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省份", dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市", dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区", dataType = "String"),
            @ApiImplicitParam(name = "libtype", value = "政策类型", dataType = "int", example = "公示名录0 培训资料1 模板范文2  政府文件5 政策解读4 其他3 申报通知6"),
            @ApiImplicitParam(name = "type", value = "项目类型", dataType = "int"),
            @ApiImplicitParam(name = "person", value = "上传类型", dataType = "int", example = "  0：平台维护人员上传   1：用户上传"),
            @ApiImplicitParam(name = "status", value = "审核 待审核或拒绝传递1", dataType = "int")

    })
    @ApiOperation(value = "文库列表", notes = "")
    public Respon findlibraryall(@RequestBody PageData pd) {
        if( pd.get( "libtype" )==null||pd.get("libtype")==""){
            pd.put("libtype",null );
        }
        SysUser user = LoginUserUtils.getLoginUser();
        if(null!=user){
            if(UserType.REGION_COMPANY.name().equals(user.getUserType()  )){
                pd.put( "regionid" ,user.getCompanyid());
            }
        }
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
        if(StringUtils.isBlank(pd.getString("status"))){
            List<PageData> list = libraryService.sysfindByPage( page );
            respon.success( list, page );
        }else{
            List<PageData> list = libraryService.sysfindByPagenopass( page );
            respon.success( list, page );
        }
        } catch (Exception e) {
            respon.error(  );
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
            SysUser user = LoginUserUtils.getLoginUser();
            if (null != user) {
                library.setCreateid( user.getUserId() );
                library.setStatus( 1 );
                library.setCreatedate( new Date() );
                String username="" ;
                if(UserType.ADMIN.name().equals( user.getUserType() )){ //管理员
                    username= Const.adminupload ;
                }else if(UserType.REGION_COMPANY.name().equals( user.getUserType() )){//区域管理上传的文件默认为自己的区域
                    library.setRegionid( user.getCompanyid() );
                    username =user.getTrueName();
                }else{
                    username =user.getTrueName();
                }
                if(org.apache.commons.lang.StringUtils.isNotBlank( username )){
                    library.setUploadusername(  username);
                }else{
                    String phone = user.getPhone();
                    library.setUploadusername( phone.substring(0, 3) + "****" + phone.substring(7, phone.length()) );
                }
                boolean flag = libraryService.saveOrUpdate( library );
                if (flag) {
                    respon.success( "添加成功" );
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


    //修改yes
    @PostMapping("updatelibrary")
    @ApiOperation(value = "修改library", notes = "修改library")
    public Respon updatelibrary(@ApiParam(name = "TLibrary对象", value = "传入json格式", required = true) @RequestBody Library library) {
        Respon respon = new Respon();
        try {
//
            PageData  old = libraryService.findlibrarybyid( library.getId() );
            boolean flag = libraryService.updateById(library);
            if (flag) {
            	if(null!=library.getStatus() && null!=String.valueOf(old.get( "STATUS" ))) {
            		  if(0 ==Integer.parseInt( String.valueOf(old.get( "STATUS" ))) && 1==library.getStatus()){//审核通过
            		      //通知
                          MsgUtil.addMsg( WorkReminder.Work.doc_check_success.getName(),null,   old.getString("createid"), LoginUserUtils.getLoginUser().getUserId(), null);
                          // 添加金币
                          RewardUtil.disGoldcoin (String.valueOf( GoldcoinRule.Gold.doc_check_success ),Double.valueOf(library.getCold()),old.getString( "createid" ),LoginUserUtils.getUserId(),null, null);
                      }
                    if(0 ==Integer.parseInt(String.valueOf(old.get( "STATUS" ))) && -1==library.getStatus()){//审核拒接
                        //通知
                        MsgUtil.addMsg( WorkReminder.Work.doc_check_fail.getName(),null,  old.getString("createid"), LoginUserUtils.getLoginUser().getUserId(), library.getReason());
                    }
            	}
                respon.success( "修改成功" );
            } else {
                respon.error( "修改失败" );
            }
        } catch (Exception e) {
            respon.error( "修改失败" );
        }
        return respon;
    }

    //yes
    @GetMapping("findlibrarybyid")
    @ApiOperation(value = "根据id获取详细信息", notes = "获取详细信息 data 获取library ReserveData 获取title模糊匹配的文库")
    public Respon updatelibrary(@ApiParam(name = "id", value = "Library id") String id) {
        Respon respon = new Respon();
        try {
            PageData  library= libraryService.findlibrarybyid( id );
            if (null != library) {
                library =  getlibray(library);
                PageData pd = new PageData();
                pd.put("title",library.getString( "title" ));
                pd.put("id", id);
                List<Library> librarys = libraryService.findbytitle(pd);
                respon.setReserveData((librarys));
                respon.setData( library );
                respon.setCode( "10000" );
            } else {
                respon.error( "没查询到" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

    public  PageData  getlibray(PageData  library){
        String industrialfield =  library.getString( "industrialfield" );
        if (StringUtils.isNotBlank( industrialfield )) {
            String[] indexs =  industrialfield.split( "@" );
            library.put("industrialfields",indexs);
        }
        String technicalfield =  library.getString( "technicalfield" );
        if (StringUtils.isNotBlank( technicalfield )) {
            String[] index =  technicalfield.split( "@" );
            library.put("technicalfields",index);
        }
        return library;
    }

   // findbytitle
    //修改审核状态   * 0 没有审核
    //     * 1 审核通过
    //     * -1 审核不通过
    //yes
//    @PostMapping("updatereview")
//    @ApiOperation(value = "修改审核状态", notes = "")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "Library id", dataType = "String"), @ApiImplicitParam(name = "status", value = "0 没有审核，1 审核通过，-1拒接", dataType = "String"), @ApiImplicitParam(name = "reason", value = "拒绝时填写", dataType = "String")})
//    public Respon updatereview(@RequestBody PageData pd) {
//        Respon respon = new Respon();
//        try {
//            Boolean flas = libraryService.updatereview( pd );
//            if (flas) {
//                //审核通过
//                if(Integer.parseInt( pd.getString( "status" ))==1){
//                    //todo发送honney
//                }
//                respon.success( "修改成功" );
//            } else {
//                respon.error( "修改失败" );
//            }
//        } catch (Exception e) {
//            respon.error( "系统异常" );
//        }
//        return respon;
//    }

    //批量删除
    //yes
    @GetMapping("batchdelete")
    @ApiOperation(value = "批量删除", notes = "传递id多个用, 隔开")
    public Respon batchdelete(@ApiParam(name = "ids", value = "1,2,3") String ids) {
        Respon respon = new Respon();
        try {
            if (StringUtils.isNoneBlank( ids )) {
                String[] idsl = ids.split( "," );
                List<String> idlists = new ArrayList<>();
                for (String idlist : idsl) {
                    idlists.add( idlist );
                }
                boolean flas = libraryService.removeByIds( idlists );
                if (flas) {
                    respon.success( "修改成功" );
                } else {
                    respon.error( "修改失败" );
                }
            } else {
                respon.error( "请选择要删除的文库" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

    //根据idcha查询list集合
    @GetMapping("getlistByids")
    @ApiOperation(value = "根据id查询list集合", notes = "")
    public Respon getlistByids(@ApiParam(name = "ids") String ids) {
        Respon respon = new Respon();
        PageData pd =this.getPageData();
        try {
                if (StringUtils.isNoneBlank( ids )) {
                    String[] idsl = ids.split( "," );
                    List<String> idlists = new ArrayList<>();
                    for (String idlist : idsl) {
                        idlists.add( idlist );
                    }
                    pd.put( "list" ,idsl);
                    MyPage page =this.getMyPage( pd );
                    page.setSize(100);//todo 写死了显示条数
                    List<PageData> list = libraryService.getlistByids(page);
                    respon.success( list ,page);
                } else {
                    respon.error( "请选择要添加的文库" );
                }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

}
