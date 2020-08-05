package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.EvaluateService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.comment.EvaluateWeb;
import com.ht.entity.biz.comment.EvaluateWebVO;
import com.ht.entity.tree.EntityTree;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(value = "EvaluateController", description = "后端评论管理")
@Controller
@RestController
@RequestMapping(value="/sys/ht-biz/evaluate")
public class SysEvaluateController extends BaseController {

    @Autowired
    EvaluateService evaluateService;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "评论账户", dataType = "String"),
            @ApiImplicitParam(name = "className", value = "来源", dataType = "String"),
            @ApiImplicitParam(name = "title", value = "评论目标", dataType = "String"),
            @ApiImplicitParam(name = "statrtime", value = "开始时间", dataType = "date"),
            @ApiImplicitParam(name = "endtime", value = "结束时间", dataType = "date")
    })
    @PostMapping (value = "findall")
    @ApiOperation( value = "根据targetId查询相关的评论",notes = "")
    public Respon findall(@RequestBody  PageData pd){
        Respon respon = this.getRespon();
        MyPage page=getMyPage(pd);
        try {
            List<EvaluateWeb> list = evaluateService.sysfindall(page);
            respon.success(list,page );
        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String")
    })
    @GetMapping (value = "/delete")
    @ApiOperation( value = "删除",notes = "")
    public Respon delect( String id ){
        Respon respon = this.getRespon();
        try {
         String[] ids  = id.split( "," );
         List<String > idd = new ArrayList<>(  );
         for(String idss :ids){
             idd.add( idss );
         }
            if( evaluateService.removeByIds( idd )){
                respon.success( "成功" );
            }else{
                respon.success( "失败" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }


    @GetMapping (value = "/getclass")
    @ApiOperation( value = "获取来源",notes = "")
    public Respon getclass(   ){
        Respon respon = this.getRespon();
        try {
         List<PageData>  list = evaluateService.getgroup();
            respon.success( list );
        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String"),
            @ApiImplicitParam(name = "exception", value = "禁用或启用 ,false :启用  true:禁用", dataType = "boolean")
    })
    @GetMapping (value = "/updatestatue")
    @ApiOperation( value = "修改",notes = "")
    public Respon updatestatue(   ){
        Respon respon = this.getRespon();
        PageData pd = this.getPageData();
        try {

            if(evaluateService.updatebyId(pd)){
                respon.success( "修改成功" );
            }else{
                respon.success( "修改失败" );
            }

        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }
}
