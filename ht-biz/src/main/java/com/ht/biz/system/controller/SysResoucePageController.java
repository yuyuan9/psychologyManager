package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.biz.service.LibraryService;
import com.ht.biz.service.ResoucePageService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.UploadhttpClient;
import com.ht.entity.biz.library.Library;
import com.ht.entity.biz.library.ResourcePage;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/sys/ht-biz/sysResoucepage/")
@RestController
@Controller
@Api(value = "ResoucePageController", description = "后台资源包管理")
public class SysResoucePageController extends BaseController {
    @Autowired
    ResoucePageService resoucePageService;

    @Autowired
    private LibraryService libraryService;

    @PostMapping("list")
    @ApiOperation(value = "后台列表展示", notes = "后台列表展示")
    @ApiImplicitParams({@ApiImplicitParam(name = "current", value = "当前页", dataType = "int"), @ApiImplicitParam(name = "size", value = "页数", dataType = "int"), @ApiImplicitParam(name = "keyword", value = "关键字查询", dataType = "String"), @ApiImplicitParam(name = "retype", value = "资源包类型", dataType = "int"), @ApiImplicitParam(name = "classify", value = "分类", dataType = "String")})
    public Respon getResoucelistpage(@RequestBody PageData pd) {
        Object s = pd.get( "retype" );
        if (pd.get( "retype" ) == null || pd.get( "retype" ) == "") {
            pd.put( "retype", null );
        }

        if (pd.get( "classify" ) == null || pd.get( "classify" ) == "") {
            pd.put( "classify", null );
        }
        Respon respon = new Respon();
        MyPage page = getMyPage( pd );
        try {
            MyPage<PageData> list = resoucePageService.findListPage( page );
            respon.success( list );
        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }

    //sava
    @PostMapping("sava")
    @ApiOperation(value = "添加resourcePage", notes = "添加resourcePage")
    public Respon savaResoucepage(@ApiParam(name = "resourcePage对象", value = "传入json格式", required = true) @RequestBody ResourcePage resourcePage) throws IllegalAccessException {
        Respon respon = new Respon();
        if (null != getLoginInfo()) {
         /*   ResourcePage oldresoucePange = resoucePageService.getById( resourcePage.getId() );
            if (null != oldresoucePange) { //存在修改
                if (!oldresoucePange.getLibraryid().equals( resourcePage.getLibraryid() )) {
                    String result = getpath( resourcePage.getLibraryid() );
                    if (!StringUtils.isBlank( result ) && !result.equals( "false" )) {
                        resourcePage.setZippath( result );
                        boolean flag = resoucePageService.updateById( resourcePage );
                        if (flag) {
                            respon.success( "修改成功" );
                        } else {
                            respon.error( "修改失败" );
                        }
                    } else {
                        respon.error( "压缩文件失败" );
                    }
                } else {
                    boolean flag = resoucePageService.updateById( resourcePage );
                    if (flag) {
                        respon.success( "修改成功" );
                    } else {
                        respon.error( "修改失败" );
                    }
                }
            } else { //添加
                String result = getpath( resourcePage.getLibraryid() );
                if (!StringUtils.isBlank( result ) && !result.equals( "false" )) {*/
                    resourcePage.setCreateid( getLoginInfo().get( "userId" ).toString() );
                    resourcePage.setRegionid(   LoginUserUtils.getLoginUser().getCompanyid() );
                    resourcePage.setCreatedate( new Date() );
           /*         resourcePage.setZippath( result );*/
                    boolean flag = resoucePageService.saveOrUpdate( resourcePage );
                    if (flag) {
                        respon.success( "添加成功" );
                    } else {
                        respon.success( "添加失败" );
                    }
              /*  } else {
                    respon.error( "压缩文件失败" );
                }*/
            }

     /*   } else {
            resourcePage.setCreateid( getLoginInfo().get( "userId" ).toString() );
            resourcePage.setCreatedate( new Date() );
            boolean flag = resoucePageService.saveOrUpdate( resourcePage );
            if (flag) {
                respon.success( "添加成功" );
            } else {
                respon.success( "添加失败" );
            }
        }*/
        return respon;
    }


    public String getpath(String id) {
        if (StringUtils.isNotBlank( id )) {
            String pahts = "";
            String[] ids = id.split( "," );
            int j = -1;
            for (int i = 0; i < ids.length; i++) {
                Library library = libraryService.findbyId( ids[i] );
                if (null != library) {
                    String path = library.getOriginalfile();
                    if (StringUtils.isNotBlank( path )) {
                        j++;
                        if (j == 0) {
                            pahts = path;
                        } else {
                            pahts = pahts + "," + path;
                        }
                    } else if (StringUtils.isNotBlank( library.getPdfpath() )) {
                        j++;
                        if (j == 0) {
                            pahts = library.getPdfpath();
                        } else {
                            pahts = pahts + "," + library.getPdfpath();
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank( pahts )) {
                UploadhttpClient uploadhttpClient = new UploadhttpClient();
                String result = uploadhttpClient.zipfile( pahts );
                return result;
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }


    //删除
    @GetMapping("batchdelete")
    @ApiOperation(value="批量删除",notes="传递id多个用, 隔开")
    public Respon batchdelete(@ApiParam(name="ids" ,value="1,2,3")  String ids){
        Respon respon = new Respon(  );
        try {
            if(StringUtils.isNoneBlank( ids )){
                String []  idsl =  ids.split( "," );
                List<String> idlists = new ArrayList<>(  );
                for(String idlist: idsl){
                    idlists.add( idlist );
                }
                boolean  flas =  resoucePageService.removeByIds( idlists );
                if(flas){
                    respon.success( "修改成功" );
                }else {
                    respon.error( "修改失败" );
                }
            }else{
                respon.error( "请选择要删除的资源库" );
            }
        }catch (Exception e){
            respon.error( "系统异常" );
        }
        return respon;
    }

    //修改yes
    @PostMapping("updatelibrary")
    @ApiOperation(value="修改library",notes="修改library")
    public Respon updatelibrary(@ApiParam(name="TLibrary对象",value="传入json格式",required=true)@RequestBody  ResourcePage resourcePage){
        Respon respon = new Respon(  );
        try {
            //设置置顶
            if(null!=resourcePage &&resourcePage.getIstop()==1){
                resourcePage.setSort( resoucePageService.maxsort()+1);
            }
            //取消置顶
            if(null!=resourcePage &&resourcePage.getIstop()==0){
                resourcePage.setSort( 0 );
            }
            boolean flag =resoucePageService.updateById( resourcePage );
            if(flag){
                respon.success( "修改成功" );
            }else{
                respon.error( "修改失败" );
            }
        }catch (Exception e){
            respon.error( "修改失败" );
        }
        return respon;
    }
}
