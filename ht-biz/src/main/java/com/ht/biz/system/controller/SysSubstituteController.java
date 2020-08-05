package com.ht.biz.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.GoldcoinRuleRecordService;
import com.ht.biz.service.SubstituteService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.excel.Excel;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.honey.Substitute;
import com.ht.entity.biz.honey.SubstituteType;
import com.ht.entity.biz.honeymanager.GoldcoinRule;
import com.ht.entity.biz.honeymanager.GoldcoinRuleRecord;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value="/sys/ht-biz/substitute")
@Api(value="SysSolrSearchRecordController",description = "后端金币提现及置换honey")
public class SysSubstituteController  extends BaseController {
    @Autowired
    private SubstituteService substituteService;

    @Autowired
    private GoldcoinRuleRecordService goldcoinRuleRecordService;

    public static final String strings[]={"费用类型","支付原因","支付方式",
            "付款公司全称","费用归属部分","数据ID",
            "收款单位银行开户支行","收款单位银行账号","收款人全称","手机号","税前金额","实际转款金额","综合费用税费"};


    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "账户", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "int"),
            @ApiImplicitParam(name = "applytype", value = "置换类型 1：换honey 2:提现", dataType = "int")

    })
    @ApiOperation(value = "查询置换列表", notes = "")
    @RequestMapping(value = "findsubhoneybytype", method = RequestMethod.POST)
    public Respon findsubhoneybytype(@RequestBody PageData pd) {
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon(  );
        try {
            List<PageData> subs = substituteService.findsubhoneybytype(page);
            respon.success(  subs,page);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon ;
    }

    //
    @ApiOperation(value = "审批", notes = "")
    @PostMapping(value = "/examine")
    public Respon examine (@RequestBody Substitute substitute) {
        Respon respon = new Respon(  );
        try {
            Substitute endsub = substituteService.getById( substitute.getId() );
            endsub.setFlownumber( substitute.getFlownumber() );
            endsub.setType( substitute.getType() );
            endsub.setRefuseresion( substitute.getRefuseresion() );
             if(substituteService.saveOrUpdate(endsub)){
                //失败返回金币
                if(SubstituteType.EXAMINE_FAIL.getCode() == endsub.getType()){
                    //退回金币
                    RewardUtil.disGoldcoin( GoldcoinRule.Gold.honey_cash.name(),endsub.getApplygold(), endsub.getCreateid(), LoginUserUtils.getUserId(),null,"提现失败退回金币："+endsub.getApplygold());
                    //退回金币的消息
                    MsgUtil.addMsg(WorkReminder.Work.gold_cash_check_fail.name()  , "#", endsub.getCreateid(),LoginUserUtils.getUserId(),endsub.getRefuseresion());
                }
                if(  SubstituteType.TRANSFER_FAIL.getCode() == endsub.getType() ){
                    //退回金币
                    RewardUtil.disGoldcoin( GoldcoinRule.Gold.honey_cash.name(),endsub.getApplygold(), endsub.getCreateid(), LoginUserUtils.getUserId(),null,"提现失败退回金币："+endsub.getApplygold());
                    //退回金币的消息
                    MsgUtil.addMsg(WorkReminder.Work.gold_cash_accounts_fail.name()  , "#", endsub.getCreateid(),LoginUserUtils.getUserId(),endsub.getRefuseresion());
                }
                if(SubstituteType.TRANSFER_SUCESS.getCode() == endsub.getType()){
                    QueryWrapper queryWrapper = new QueryWrapper(  );
                    queryWrapper.eq( "targetid",substitute.getId() );
                    GoldcoinRuleRecord goldcoinRuleRecord =  goldcoinRuleRecordService.getOne( queryWrapper );
                    goldcoinRuleRecord.setFlowcode(substitute.getFlownumber());
                    goldcoinRuleRecordService.saveOrUpdate( goldcoinRuleRecord );
                    //转账成功消息通知
                    MsgUtil.addMsg(WorkReminder.Work.gold_cash_accounts_success.name()  ,substitute.getRefuseresion(), endsub.getCreateid(),LoginUserUtils.getUserId(),null);

                }

                respon.success("审批成功");
            }else{
                respon.error( "审批失败" );
            }
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon ;
    }



    //将金币提现申请导入到excel中
    @ApiOperation(value = "导出excel", notes = "")
    @GetMapping(value = "/export")
    public Respon examine (HttpServletResponse response,String[] ids) {
        Respon respon = new Respon(  );
            try {
            QueryWrapper<Substitute> queryWrapper = new QueryWrapper(  );
            queryWrapper.in( "id", ids);
            List<Substitute> list = substituteService.list(  queryWrapper);
           Excel.excelExport(response, list,new Substitute(), ".xml", strings, "test","金币提现");
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon ;
    }

}
