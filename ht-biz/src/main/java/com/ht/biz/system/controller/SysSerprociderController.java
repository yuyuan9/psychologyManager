package com.ht.biz.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

//import com.ht.biz.base.TreeBuilder;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ht.biz.service.LevelService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.SerprociderService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.SpringContextUtil;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.biz.sys.Level;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller/*/sys/*/
@RequestMapping(value = "/sys/ht-biz/servicer")
@Api(value = "SysSerprociderController", description = "后台服务商")
@RestController
public class SysSerprociderController extends BaseController {

    @Autowired
    private SerprociderService serprociderService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private PointRedemptionService pointRedemptionService;
    @Autowired
    private LevelService levelService;

    @PostMapping("findall")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "stats", value = "审核状态 1:待审核2:正常3:禁用4:审核拒绝" ,dataType = "int"),
            @ApiImplicitParam(name = "banme", value = "服务商名称" ,dataType = "String")
    })
    @ApiOperation(value="查询服务商列表",notes="查询服务商列表")
    public Respon findallservice(@RequestBody PageData pd) {
        Respon respon = new Respon();
        MyPage page = this.getMyPage( pd );
        try {
            List<PageData> list = serprociderService.sysfindByPage(page);
            respon.success(list,page  );
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        return respon;
    }

    @GetMapping ("findeById")
    @ApiOperation(value="查询服务商详细信息",notes="查询服务商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",  dataType = "String"),

    })
    public Respon findallservice(String id) {
        Respon respon = new Respon(  );
        try {
            PageData pd = new PageData(  );
            pd.put( "serId" ,id);
            PageData serviceProvider = serprociderService.findById( pd );
            Double honeytotal=pointRedemptionService.getWaterTotal(String.valueOf(LoginUserUtils.getUserId()));
    		honeytotal=honeytotal==null?0:honeytotal;
    		QueryWrapper<Level> qw=new QueryWrapper<Level>();
    		qw.eq("type", 2);
    		qw.le("point", honeytotal);
    		qw.orderByDesc("point");
    		Level level=levelService.getOne(qw, false);
            respon.success(serviceProvider  );
            respon.setReserveData(level);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        return respon;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id" ,dataType = "String"),
            @ApiImplicitParam(name = "stats", value = "审核状态 1:待审核2:正常3:审核拒绝" ,dataType = "int"),
            @ApiImplicitParam(name = "reason", value = "备注" ,dataType = "String"),
            @ApiImplicitParam(name = "grade", value = "评分" ,dataType = "String"),
            @ApiImplicitParam(name = "browse", value = "浏览" ,dataType = "String")
    })
    @PostMapping("updata")
    @ApiOperation(value="修改",notes="修改")
    public Respon updata(@RequestBody ServiceProvider serviceProvider) {
        Respon respon = new Respon();
        try {

            ServiceProvider oldservice =  serprociderService.getById(serviceProvider.getId() );
            if(null!=serviceProvider.getStats()){
                oldservice.setStats(serviceProvider.getStats()  );
            }
            if(serviceProvider.getGrade()>0){
                oldservice.setGrade(serviceProvider.getGrade()  );
            }
            if(serviceProvider.getBrowse()>0){
                oldservice.setBrowse(serviceProvider.getBrowse()  );
            }
            if(StringUtils.isNotBlank( serviceProvider.getReason() )){
                oldservice.setReason( serviceProvider.getReason() );
            }
            if(oldservice.getStats()==1 ||oldservice.getStats()==4 ){
                if(null!=serviceProvider.getStats()&& serviceProvider.getStats()==2){
                    SysUser user = (SysUser) ((IService)SpringContextUtil.getBean("sysUserService")).getById(  oldservice.getCreateid());
                    user.setUserType( UserType.SERVICE_PROVIDER.name());
                    ((IService)SpringContextUtil.getBean("sysUserService")).saveOrUpdate(user);
                    //加入redis
                    redisTemplate.opsForValue().set( Const.redisusertype+LoginUserUtils.getLoginUser().getUserId() ,"true");
                    MsgUtil.addMsg( WorkReminder.Work.server_check_success.getName(),"/ht-biz/service/index/shop_edit",  oldservice.getCreateid(), LoginUserUtils.getLoginUser().getUserId(), LoginUserUtils.getLoginUser().getCompanyid());
                }
            }if(oldservice.getStats()==1 ){ //拒绝
                if(null!=serviceProvider.getStats()&& serviceProvider.getStats()==4){
                    MsgUtil.addMsg( WorkReminder.Work.server_check_fail.getName(),"",  oldservice.getCreateid(), LoginUserUtils.getLoginUser().getUserId(), serviceProvider.getReason());
                }
            }
            if(serprociderService.updateById( oldservice )){
                respon.success("修改成功" );
            }


        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        return respon;
    }

}
