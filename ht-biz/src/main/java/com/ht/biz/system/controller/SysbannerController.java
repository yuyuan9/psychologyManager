package com.ht.biz.system.controller;


import com.ht.biz.service.BannerService;
import com.ht.biz.service.KeyworkService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Indexbanner;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/sys/ht-biz/banner")
@Api(value="SysbannerController",description = "后台banner")
public class SysbannerController  extends BaseController {

    @Autowired
    BannerService bannerService;
    //查询所有的banner
    @PostMapping("sysfindall")
    @ApiOperation(value="查询banner列表",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String")

    })
    public Respon syslistall(@RequestBody PageData pd){
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            List<PageData> list = bannerService.sysfindByPage(page);
            respon.success( list,page);
        }catch (Exception e){
        	e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }

    @ApiOperation(value="修改或添加",notes="")
    @PostMapping("save")
    public Respon savaorupdata(@RequestBody Indexbanner indexbanner){
        Respon respon = new Respon();
        try {
           if(bannerService.saveOrUpdate( indexbanner)) {
               respon.success("成功");
           }else{
               respon.error( "失败" );
           }
        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }

    @ApiOperation(value="获取详细信息",notes="")
    @GetMapping("findbyId")
    public Respon findbyId(@ApiParam(name="id" ,value = "banner 的 id") String id){
        Respon respon = new Respon();
        try {
           PageData indexbanner = bannerService.findbyId( id );
           respon.success(  indexbanner);
        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }
    //批量删除
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
                boolean flas = bannerService.removeByIds( idlists );
                if (flas) {
                    respon.success( "修改成功" );
                } else {
                    respon.error( "修改失败" );
                }
            } else {
                respon.error( "请选择要删除的banner" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

}
