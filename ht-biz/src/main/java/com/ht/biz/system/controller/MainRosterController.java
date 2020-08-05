package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.MainRosterService;
import com.ht.biz.service.RosterinfoService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;

import com.ht.entity.biz.sys.MainRoster;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/sys/ht-biz/mainroster")
@Api(value = "mainroster", description = "科技型中小企业名单主表")
public class MainRosterController extends BaseController {

    @Autowired
    private MainRosterService mainRosterService;
    @Autowired
    private RosterinfoService rosterinfoService;


    @ApiOperation(value="修改或添加",notes="")
    @PostMapping("savemainroster")
    public Respon savaorupdata(@RequestBody MainRoster mainRoster){
        Respon respon = new Respon();
        try {
            if(mainRosterService.saveOrUpdate( mainRoster)) {
                respon.success("成功");
            }else{
                respon.error( "失败" );
            }
        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }
    @PostMapping("sysfindallmainroster")
    @ApiOperation(value="查询banner列表",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String"),
            @ApiImplicitParam(name = "year", value = "年", dataType = "String"),
            @ApiImplicitParam(name = "batch", value = "批次", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String")
    })
    public Respon sysfindallmainroster(@RequestBody PageData pd){
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            List<PageData> list = mainRosterService.sysfindallmainroster(page);
            respon.success( list,page);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }
    @ApiOperation(value="获取详细信息",notes="")
    @GetMapping("findbyIdmainroster")
    public Respon findbyId(@ApiParam(name="id" ,value = "mainRoster 的 id") int id){
        Respon respon = new Respon();
        try {
            MainRoster mainRoster =  mainRosterService.getById(id);
            respon.success(  mainRoster);
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
                for(String info : idsl){
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("roster_id",info);
                    int num = rosterinfoService.count(queryWrapper);
                    if(num>0){
                        return  respon.error("请先删除id:"+info +"下面所有的相关联信息");
                    }
                }
                List<String> idlists = new ArrayList<>();
                for (String idlist : idsl) {
                    idlists.add( idlist );
                }
                boolean flas = mainRosterService.removeByIds( idlists );
                if (flas) {
                    respon.success( "删除成功" );
                } else {
                    respon.error( "删除失败" );
                }
            } else {
                respon.error( "请选择要删除的一项" );
            }
        } catch (Exception e) {
            respon.error( "系统异常" );
        }
        return respon;
    }

    @GetMapping("findall")
    @ApiOperation(value = "查询所有不分页", notes = "")
    public Respon findall(){
        Respon respon = new Respon();
        try {
            List<MainRoster> list = mainRosterService.list();
            respon.success( list);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }
}
