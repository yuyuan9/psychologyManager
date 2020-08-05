package com.ht.biz.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.WatermarkService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.biz.sys.Watermark;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/sys/ht-biz/watermark")
@Api(value="SysWatermarkController",description = "后台水印管理")
public class SysWatermarkController extends BaseController {

    @Autowired
    WatermarkService watermarkService;
    //查询所有的水印列表
    @PostMapping("sysfindall")
    @ApiOperation(value="查询水印列表",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "context", value = "水印内容", dataType = "String"),
            @ApiImplicitParam(name = "isenable", value = "使用启用", dataType = "Integer")

    })
    public Respon syslistall(@RequestBody PageData pd){
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            List<PageData> list = watermarkService.sysfindByPage(page);
            respon.success( list,page);
        }catch (Exception e){
        	e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }

    @PostMapping("sysfindbyisenable")
    @ApiOperation(value="查询启用水印列表",notes="")
    public Respon syslistall(){
        Respon respon = new Respon();
        try {
            QueryWrapper<Watermark> queryWrapper = new QueryWrapper<>(  );
            queryWrapper.eq( "isenable",1 );
            List<Watermark> list = watermarkService.list( queryWrapper );
            respon.success( list);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }
    @ApiOperation(value="添加",notes="")
    @PostMapping("save")
    public Respon savaorupdata(@RequestBody Watermark watermark){
        Respon respon = new Respon();
        try {
            watermark.setCreateid( LoginUserUtils.getUserId() );
            watermark.setCreatedate( new Date(  ) );
           if(watermarkService.save( watermark)) {
               respon.success("成功");
           }else{
               respon.error( "失败" );
           }
        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }
    @ApiOperation(value="修改",notes="")
    @PostMapping("update")
    public Respon updata(@RequestBody Watermark watermark){
        Respon respon = new Respon();
        try {
           if(null!= watermark.getId() ){
              Watermark info = watermarkService.getById(  watermark.getId() );
              if(null!=info){
                  if(StringUtils.isNotBlank(watermark.getContext()  )){
                      info.setContext( watermark.getContext()  );
                  }
                  if(null!=watermark.getIsenable()){
                      info.setIsenable( watermark.getIsenable() );
                  }
                  if(watermarkService.saveOrUpdate( info )){
                      respon.success( "修改成功" );
                  }
              }

           }else{
               respon.error( "该对象不存在" );
           }

        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }
    @ApiOperation(value="获取详细信息",notes="")
    @GetMapping("findbyId")
    public Respon findbyId(@ApiParam(name="id" ,value = "Watermark 的 id") String id){
        Respon respon = new Respon();
        try {
           Watermark watermark = watermarkService.getById( id );
           respon.success(  watermark);
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
                boolean flas = watermarkService.removeByIds( idlists );
                if (flas) {
                    respon.success( "修改成功" );
                } else {
                    respon.error( "修改失败" );
                }
            } else {
                respon.error( "请选择要删除的水印" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

}
