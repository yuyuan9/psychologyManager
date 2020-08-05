package com.ht.biz.system.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.PointRedemptionService;
import com.ht.biz.service.PoliProRecordService;
import com.ht.biz.service.RosterinfoService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.RequestUtils;
import com.ht.entity.biz.honeymanager.RewardRule;
import com.ht.entity.biz.solr.policylib.PoliProRecord;
import com.ht.entity.biz.sys.Rosterinfo;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.RewardUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/sys/ht-biz/rosterinfo")
@Api(value = "RosterInfoController", description = "科技型中小企业名单详细信息登记表")
public class RosterInfoController extends BaseController {

    @Autowired
    private RosterinfoService rosterinfoService;
    @Autowired
    private PoliProRecordService poliProRecordService;

    @Autowired
    private PointRedemptionService pointRedemptionService;
    @ApiOperation(value="修改或添加",notes="")
    @PostMapping("savemainroster")
    public Respon savaorupdata(@RequestBody Rosterinfo rosterinfo){
        Respon respon = new Respon();
        try {
            if(rosterinfoService.saveOrUpdate( rosterinfo)) {
                respon.success("成功");
            }else{
                respon.error( "失败" );
            }
        }catch (Exception e){
            respon.error( e );
        }
        return respon;
    }
    @PostMapping("sysfindall")
    @ApiOperation(value="查询banner列表",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "enterprise", value = "企业名称", dataType = "String"),
            @ApiImplicitParam(name = "address", value = "注册地址", dataType = "String"),
            @ApiImplicitParam(name = "registernum", value = "入库登记号", dataType = "String"),
            @ApiImplicitParam(name = "socialcode", value = "统一社会信用代码", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String"),
            @ApiImplicitParam(name = "year", value = "年", dataType = "String"),
            @ApiImplicitParam(name = "batch", value = "批次", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String")
    })
    public Respon sysfindall(@RequestBody PageData pd){
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            List<PageData> list = rosterinfoService.sysfindall(page);
            respon.success( list,page);
        }catch (Exception e){
            e.printStackTrace();
            respon.error( e );
        }
        return respon;
    }



    @PostMapping("finadll")
    @ApiOperation(value="中小企业查询",notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
            @ApiImplicitParam(name = "enterprise", value = "企业名称", dataType = "String"),
            @ApiImplicitParam(name = "address", value = "注册地址", dataType = "String"),
            @ApiImplicitParam(name = "registernum", value = "入库登记号", dataType = "String"),
            @ApiImplicitParam(name = "socialcode", value = "统一社会信用代码", dataType = "String"),
            @ApiImplicitParam(name = "province", value = "省", dataType = "String"),
            @ApiImplicitParam(name = "year", value = "年", dataType = "String"),
            @ApiImplicitParam(name = "batch", value = "批次", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String")
    })
    public Respon finadll(  ){
        PageData  pd = this.getPageData();
        MyPage page = this.getMyPage( pd );
        Respon respon = new Respon();
        try {
            //查询访问次数
            QueryWrapper<PoliProRecord> qw=new QueryWrapper<PoliProRecord>();
            qw.eq("createid", LoginUserUtils.getUserId());
            qw.eq("local", "中小企业");
            qw.like("createdate", DateUtil.dateToStr(new Date(), 11));
            Integer counts=poliProRecordService.count(qw);
            counts=counts==null?0:counts;
            if(counts>=20 && pd.get("gtype").equals("free")){
                respon.setCode("1002");
                respon.setMsg("每天只能访问20次，再次访问需要1honey值");
                return respon;
            }
            if(counts>=20 && pd.get("gtype").equals("charge") ){
                //获取用户honey
                double myhoney = pointRedemptionService.getWaterTotal( LoginUserUtils.getUserId());
                if(myhoney<1){
                    respon.setCode("1003");
                    respon.setMsg("当前用户honey值余额不足，请及时充值");
                    return  respon;
                }else{
                    RewardUtil.disHoney(String.valueOf(RewardRule.Code.query_smalltech), Double.valueOf(-1), LoginUserUtils.getUserId(), LoginUserUtils.getUserId(),null);
                }
            }

            List<PageData> list = rosterinfoService.sysfindall(page);
            //-----------------记录用户足迹----------------//
            PoliProRecord p=new PoliProRecord();
            p.setCounts(Double.valueOf(page.getTotal()));
            p.setIp(RequestUtils.getIp());
            p.setInterfaceName("/sys/ht-biz/rosterinfo/finadll");
            p.setLocal("中小企业");
            p.setCreatedate(new Date());
            p.setCreateid(LoginUserUtils.getUserId());
            poliProRecordService.save(p);
            //-----------------记录用户足迹----------------//

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
            PageData rosterinfo =  rosterinfoService.getpagedataById(id);
            respon.success(  rosterinfo);
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
                boolean flas = rosterinfoService.removeByIds( idlists );
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
}
