package com.ht.biz.controller;


import com.ht.biz.service.BannerService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.Indexbanner;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/ht-biz/banner")
@Api(value="bannerController",description = "前端banner管理")
public class bannerController extends BaseController {

    @Autowired
    BannerService bannerService;


    @PostMapping("getbannerbytype")
    @ApiOperation(value="获取banner",notes="获取banner")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1:首页",  dataType = "int"),
            @ApiImplicitParam(name = "proid", value = "省份id,如果没绑定不传",  dataType = "String")
    })
    public Respon syslistall(){
        PageData pd = this.getPageData();
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            List<PageData> list = bannerService.findByPage(page);
            respon.success( list,page);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }

}
