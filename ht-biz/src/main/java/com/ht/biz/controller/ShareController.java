package com.ht.biz.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ht.biz.service.ShareService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Share;
import com.ht.entity.policydig.Subscribe;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/ht-biz/share")
@Api(value="ShareController",description = "前台分享注册")
public class ShareController extends BaseController {
    @Autowired
    private ShareService shareService;

    @ApiOperation(value="查询我的分享")
    @PostMapping (value="/getmyshare")
    public Respon getSubscribe(MyPage page) throws Exception{
        Respon respon = this.getRespon();
        PageData pd = this.getPageData();
        page.setPd( pd );
        SysUser user = LoginUserUtils.getLoginUser();
        if(user!=null){
            QueryWrapper<Share> qw=new QueryWrapper<Share>();
            qw.eq("fromuserid", user.getUserId());
            qw.orderByDesc("createdate");
            IPage<Share> sharelsit = shareService.page( page, qw );
            respon.success(sharelsit);
        }else{
            respon.loginerror(null);
        }
        return respon;
    }

}
