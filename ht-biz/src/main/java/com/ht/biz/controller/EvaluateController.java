package com.ht.biz.controller;

import com.ht.biz.service.EvaluateService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.comment.EvaluateWeb;
import com.ht.entity.biz.comment.EvaluateWebVO;

import com.ht.entity.tree.EntityTree;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Api(value = "EvaluateController", description = "前端评论管理")
@Controller
@RestController
@RequestMapping(value="/ht-biz/evaluate")
public class EvaluateController  extends BaseController {

    @Autowired 
    EvaluateService evaluateService;

    @GetMapping(value = "findEvaluatByTargetId")
    @ApiOperation( value = "根据targetId查询相关的评论",notes = "")
   // targetId
    public Respon findEvaluatByTargetId(MyPage page){
        Respon respon = this.getRespon();
        PageData pd = this.getPageData();
        try {
            page.setPd(pd);
            List<EvaluateWeb> list = evaluateService.findEvaluatByTargetId(page);
            if(null!=list){
                respon.success(firstEvaluateweb(list),page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }

    public List<EvaluateWeb> firstEvaluateweb(List<EvaluateWeb> list) {
        List<EvaluateWeb> rlist = new ArrayList<EvaluateWeb>();
        for(EvaluateWeb info: list){
            if(StringUtils.isBlank( info.getPid() )){
                List<EntityTree> rlist2 = new ArrayList<EntityTree>();
                for(EvaluateWeb info2: list){
                    if(StringUtils.isNoneBlank( info2.getPid() )&&info2.getPid().equals( info.getId() )){
                        rlist2.add(info2  );
                    }
                    info.setChildren(rlist2);
                }
                rlist.add(info  );
            }
        }
       return rlist;
    }

    @ApiOperation( value = "评价或者回复",notes = "回复需要添加pid，添加不需要添加pid")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "评价内容id",  dataType = "String"),
            @ApiImplicitParam(name = "content", value = "评价内容" ,dataType = "String"),
            @ApiImplicitParam(name = "pid", value = "被回复人id", dataType = "String"),
    })
    public Respon save(EvaluateWebVO evaluateweb) throws Exception {
        Respon respon = this.getRespon();
        try {
            if(LoginUserUtils.getLoginUser() !=null){
                evaluateweb.setUserId(LoginUserUtils.getLoginUser().getUserId() );
                evaluateweb.setCreatedate(new Date());
                boolean flag = evaluateService.save(evaluateweb);
                if(flag){
                    respon.success("评论成功");
                }else{
                    respon.error( "评论失败" );
                }
            }else{
                respon.error( "用户信息失效请重新登录" );
            }
        } catch (Exception e) {
            e.printStackTrace();
            respon.error();
        }
        return respon;
    }
}
