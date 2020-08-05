package com.ht.biz.controller;


import com.ht.biz.service.GoldcoinRuleRecordService;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.SubstituteService;
import com.ht.commons.constants.Const;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honey.Substitute;
import com.ht.entity.biz.honeymanager.GoldcoinRule;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.shiro.constants.UserType;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/ht-biz/Substitute")
@Api(value="SubstituteController",description = "前端金币提现及置换honey")
public class SubstituteController extends BaseController {
    @Autowired
    private SubstituteService substituteService;

    @Autowired
    private PointRedemptionService pointRedemptionService;

    @Autowired
    private GoldcoinRuleRecordService goldcoinRuleRecordService;

    private final static  double rate = 0.1;
    private final static  double zrate = 0.09;
    private final static  String  flag = "E2622";
    private final static Integer start = 100000;
    private final static int add =1;

    @ApiOperation(value = "置换honey", notes = "")
    @PostMapping(value="/gethoney")
    public  synchronized Respon cash ( Substitute  substitute) {
        Respon respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        try {

                //查询用户金币（判断金币是否足够）
                Double d1=goldcoinRuleRecordService.mygolds(user.getUserId());
                if(d1>=substitute.getApplygold()){
                    //添加一个置换honey记录
                    substitute.setGethoney(substitute.getApplygold() );
                    substitute.setCreateid( user.getUserId() );
                    substitute.setCreatedate( user.getCreatedate() );
                    substitute.setApplytype(1 );//金币换honey
                    substituteService.save( substitute );
                    //给用户添加honey
                    RewardUtil.disHoney(String.valueOf(RewardRule.Code.add_honey), substitute.getApplygold(), user.getUserId(), user.getUserId(),user.getCompanyid());
                    //扣除金币
                    RewardUtil.disGoldcoin( GoldcoinRule.Gold.honey_exchange.name(),-substitute.getApplygold(), user.getUserId(), user.getUserId(),user.getCompanyid(),"置换honey:"+substitute.getApplygold()+"扣除金币："+substitute.getApplygold());
                    //发送消息通知
                    MsgUtil.addMsg(WorkReminder.Work.honey_exchange.name()  ,substitute.getApplygold()+"",user.getUserId(),user.getUserId(),null);
                    respon.success( "置换成功");
                }else{
                    respon.error( "金币不够，请先获取金币" );
                }


        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon ;
    }

    //提现
    @ApiOperation(value = "提现", notes = "")
    @PostMapping(value="/cash")
    public  Respon examine ( Substitute  substitute) {
        Respon respon = new Respon(  );
        SysUser user = LoginUserUtils.getLoginUser();
        try {
            synchronized (user) {
                //判断是不是服务商
                if (UserType.SERVICE_PROVIDER.name().equals( user.getUserType() )) {
                    //查询用户金币（判断金币是否足够）
                    Double d1 = goldcoinRuleRecordService.mygolds( user.getUserId() );
                    if (d1 >= substitute.getApplygold()) {
                        //添加一个提现记录
                        substitute.setCreateid( user.getUserId() );
                        substitute.setCreatedate( user.getCreatedate() );
                        substitute.setApplytype( 2 ); //金币提现
                        substitute.setType( 1 ); //待审核
                        substitute.setBeforetax( substitute.getApplygold() * rate );//税前
                        substitute.setAftertax( substitute.getApplygold() * zrate );//税后
                        substitute.setTaxes( substitute.getApplygold() * rate * rate );//数额
                        substitute =   setSubstitute(substitute);
                        Integer numberadd = substituteService.maxnumberadd();
                        if ( null!=numberadd && numberadd>0) {
                            int num = numberadd+add;
                            substitute.setNumberadd( num );
                            substitute.setNumber( num + flag );
                        } else {
                            substitute.setNumberadd(start  );
                            substitute.setNumber( start + flag );
                        }
                        substituteService.save( substitute );

                        //扣除金币
                        RewardUtil.disGoldcoin( GoldcoinRule.Gold.honey_cash.name(), -substitute.getApplygold(), user.getUserId(), user.getUserId(), user.getCompanyid(), "置换honey:" + substitute.getApplygold() + "扣除金币：" + substitute.getApplygold(), substitute.getId(),null);
                        //发送消息通知
                        MsgUtil.addMsg( WorkReminder.Work.honey_cash.name(), substitute.getApplygold() + "", user.getUserId(), user.getUserId(), null );
                        respon.success( "提交成请耐心等待" );
                    } else {
                        respon.error( "金币不够，请先获取金币" );
                    }
                } else {
                    respon.error( "请先申请成为服务商" );
                }

            }
            Thread.sleep(1000);//1秒
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon ;
    }

    public Substitute setSubstitute(Substitute substitute) {
        substitute.setCosttyoe( "高企云作者提现");
        substitute.setPayresion( "高企云平台作者上传文档,累积到一定金币进行提现申请" );
        substitute.setPaymethod("银行转账" );
        substitute.setPaycompanyname( "广州高企云信息科技有限公司" );
        substitute.setCostattribution(  "广州高企云信息科技有限公司");
        substitute.setTaxrate( "10%");
        return substitute;
    }

}
