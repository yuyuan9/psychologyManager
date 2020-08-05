package com.ht.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.ProductService;
import com.ht.biz.service.ProductTypeService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.biz.product.Product;
import com.ht.entity.biz.product.ProductType;
import com.ht.entity.biz.product.productenum.ProductStaut;
import com.ht.utils.LoginUserUtils;

import com.ht.utils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/sys/ht-biz/product")
@Api(value="SysproductController",description = "后端产品管理")
public class SysproductController extends BaseController {

    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private ProductService productService;

    @ApiOperation(value="产品类型树形列表", notes="产品类型树形列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "产品名称", required = false, dataType = "String"),
    })
    @PostMapping("/treelist")
    public Respon treelist(@RequestBody PageData pd)throws Exception{
        Respon respon = this.getRespon();
        try {
            QueryWrapper<ProductType> que = new QueryWrapper<>(  );
            if(null!=pd.getString( "name" )&& StringUtils.isNotBlank(pd.getString( "name" ))){
                que.eq( "name",pd.getString( "name" ) );
            }
            que.orderByAsc( "sort" );
            List<ProductType> list =  productTypeService.list(  que);
            if(null!=pd.getString( "name" )&& StringUtils.isNotBlank(pd.getString( "name" ))){
                return    respon.success(list);
            }
            List<TreeEntity> source = new ArrayList<TreeEntity>();
            source.addAll(list);
            List<TreeEntity> treelist = new TreeBuilder(source).buildTree();
            respon.success(treelist);
        }catch(Exception e) {
            respon.error(e);
        }
        return respon;
    }


    @PostMapping("/add")
    @ApiOperation(value="添加/修改", notes="添加/修改")
    public Respon add(@RequestBody ProductType productType)throws Exception{
        Respon respon = this.getRespon();
        try {
            productType.setCreateid(LoginUserUtils.getLoginUser().getUserId());
            productType.setCreatedate( new Date(  ) );
          if( productTypeService.saveOrUpdate(  productType)){
              respon.success( "添加成功" );
          }else{
              respon.error( "添加失败" );
          }
        }catch(Exception e) {
            respon.error(e);
        }
        return respon;
    }

    @GetMapping("/delect")
    @ApiOperation(value="删除", notes="删除")
    public Respon delect( String   id)throws Exception{
        Respon respon = this.getRespon();
        try {
            QueryWrapper<ProductType> que = new QueryWrapper<>(  );
            que.eq( "pid",id );
            if(productTypeService.count(  que)>0){
            	  respon.error( "请先删除子节点" );
            }else{
                QueryWrapper<Product> quer = new QueryWrapper(  );
                quer.eq("producttypeone",id).or().eq( "producttypetwo",id )
                        .or().eq( "producttypethree",id );
                int count  = productService.count( quer );
                if(count>0){
                   return  respon.error( "分类存在项目关联，禁止删除");
                }
                if( productTypeService.removeById(  id)){
                    respon.success( "删除成功" );
                }else{
                    respon.error( "删除失败" );
                }
              
            }

        }catch(Exception e) {
            respon.error(e);
        }
        return respon;
    }

    //查询各种状态条数
    @ApiOperation(value="查询各种状态条数", notes="查询各种状态条数")
    @GetMapping("/sysfindstautnum")
    public Respon findstautnum(){
        Respon respon = this.getRespon();
        try {
            List<PageData> num = productService.findallstautnum();
            respon.success( productService.getstautname( num ));
        }catch (Exception e){
            e.printStackTrace();
            respon.error("删除成功") ;
        }
        return  respon ;
    }


    //查询产品列表
    @PostMapping("sysfindall")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "banme", value = "服务商名称" ,dataType = "String"),
            @ApiImplicitParam(name = "protypeId", value = "传选择产品类型的id" ,dataType = "String"),
            @ApiImplicitParam(name = "provice", value = "省" ,dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市",dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区" ,dataType = "String"),
            @ApiImplicitParam(name = "staut", value = " 2：待审核  3：下线  4：审核不通过 5：上线" ,dataType = "String"),
            @ApiImplicitParam(name = "productytpe", value = "产品类型id" ,dataType = "String"),


    })
    @ApiOperation(value="查询服务商列表",notes="查询服务商列表")
    public Respon findallservice(@RequestBody PageData pd) {
        Respon respon = new Respon();
        MyPage page = this.getMyPage( pd );
        try {
            List<PageData> list = productService.sysfindByPage(page);
            for(PageData info: list){
                if(StringUtils.isNotBlank(info.getString( "producttypeone" ))){
                    ProductType productType = productTypeService.getById( info.getString( "producttypeone" ));
                    if(null!=productType){
                        info.put( "producttypeonename" ,productTypeService.getById( info.getString( "producttypeone" ) ).getName());
                    }
                }
                if(StringUtils.isNotBlank(info.getString( "producttypetwo" ))){
                    ProductType productType = productTypeService.getById( info.getString( "producttypetwo" ));
                    if(null!=productType){
                        info.put( "producttypetwoname" ,productTypeService.getById( info.getString( "producttypetwo" ) ).getName());
                    }
                }
                if(StringUtils.isNotBlank(info.getString( "producttypethree" ))){
                    ProductType productType = productTypeService.getById( info.getString( "producttypethree" ));
                    if(null!=productType){
                        info.put( "producttypethreename" ,productTypeService.getById( info.getString( "producttypethree" ) ).getName());
                    }
                }
            }
            respon.success(list,page  );
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常" );
        }
        return respon;
    }



    @GetMapping("sysdeleted")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",  dataType = "String")
    })
    @ApiOperation(value="删除",notes="删除")
    public Respon deleted(String id) {
        Respon respon = new Respon(  );
        try {
             Product product =  productService.getById( id );
            if(productService.removeById( id )){
                MsgUtil.addMsg(product.getProductname(), WorkReminder.Work.product_offline.getName(),null,product.getCreateid()  , LoginUserUtils.getLoginUser().getUserId(), "商品违规已下线");
                return respon.success("删除成功" );
            }
            respon.error(  "删除失败");
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "系统异常"+e );
        }
        return respon;
    }
    @ApiOperation(value="查询详情",notes="查询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",  dataType = "String")
    })
    @GetMapping("/sysfindbyid")
    public Respon findbyid(String id){
        Respon respon = this.getRespon();
        try {
            Product product =  productService.getById( id);
            respon.success(  productTypeService.getProductvo(product  ));
        }catch (Exception e){
            e.printStackTrace();
            respon.error("系统异常") ;
        }
        return  respon ;
    }

    @ApiOperation(value="审核",notes="审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id",  dataType = "String"),
            @ApiImplicitParam(name = "staut", value = "状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线",  dataType = "String"),
            @ApiImplicitParam(name = "resond", value = "拒接原因",  dataType = "String")
    })
    @GetMapping("/reviewed")
    public Respon reviewed(Product product){
        Respon respon = this.getRespon();
        try {
            Product prod =  productService.getById( product.getId() );
            prod.setStaut( product.getStaut() );
            prod.setResond(product.getResond());
            if(productService.saveOrUpdate(prod  )){
                if(ProductStaut.ON_LINE.getCode()==prod.getStaut()){ //上线
                    MsgUtil.addMsg( prod.getProductname(),WorkReminder.Work.product_check_pass.getName(),"/ht-biz/service/index/product_details?id="+ prod.getId(),prod.getCreateid()  , LoginUserUtils.getLoginUser().getUserId(), null);
                }
                if(ProductStaut.APPLY_FAILE.getCode()==prod.getStaut()){ //审核不通过
                    MsgUtil.addMsg( prod.getProductname(),WorkReminder.Work.product_check_unpass.getName(),null,prod.getCreateid()  , LoginUserUtils.getLoginUser().getUserId(), prod.getResond() );
                }
                if(ProductStaut.OFF_LINE.getCode()==prod.getStaut()){ //下线
                    MsgUtil.addMsg( prod.getProductname(),WorkReminder.Work.product_offline.getName(),null,prod.getCreateid()  , LoginUserUtils.getLoginUser().getUserId(), prod.getResond()  );
                }
                return respon.success( "审核成功" );
            }
            respon.error( "审核失败" );
        }catch (Exception e){
            e.printStackTrace();
            respon.error("系统异常"+e) ;
        }
        return  respon ;
    }

}
