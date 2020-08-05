package com.ht.biz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ht.biz.service.ProductService;

import com.ht.biz.service.ProductTypeService;
import com.ht.biz.service.SerprociderService;
import com.ht.biz.util.SearchRecordUtil;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;

import com.ht.commons.support.tree.TreeBuilder;
import com.ht.commons.support.tree.entity.TreeEntity;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.Tools;
import com.ht.entity.biz.msg.WorkReminder;
import com.ht.entity.biz.product.Product;
import com.ht.entity.biz.product.ProductType;
import com.ht.entity.biz.product.ProductVo;
import com.ht.entity.biz.product.productenum.ProductStaut;
import com.ht.entity.shiro.usertype.ServiceProvider;
import com.ht.utils.LoginUserUtils;
import com.ht.utils.MsgUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.rowset.serial.SerialStruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
@RestController
@RequestMapping(value = "/ht-biz/product")
@Api(value = "productController", description = "前端产品类型管理")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private SerprociderService serprociderService;
//    @Autowired
//    private PassproductService passproductService;
//    @ApiOperation(value="产品类型树形列表", notes="产品类型树形列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType="query", name = "name", value = "产品名称", required = false, dataType = "String"),
//
//    })
//    @PostMapping("/treelist")
//    public Respon treelist(@RequestBody PageData pd)throws Exception{
//        Respon respon = this.getRespon();
//        //1查询所有的产品类型
//        //2查询一级前6条
//        //3递归
//        return  null ;
//    }



    //查询一级产品类型
    @ApiOperation(value="查询一级产品类型", notes="查询一级产品类型")
    @PostMapping("/findfirsttype")
    @Transactional
    public Respon findfirsttype( )throws Exception{
        Respon respon = this.getRespon();
        QueryWrapper queryWrapper = new QueryWrapper(  );
        queryWrapper.isNull( "pid" );
        List<ProductType> productTypes = productTypeService.list(  queryWrapper);
        return  respon.success( productTypes ) ;
    }
    //查询下一级
    @ApiOperation(value="查询下一级", notes="查询下一级")
    @PostMapping("/findchild")
    @Transactional
    public Respon findchild(String id )throws Exception{
        Respon respon = this.getRespon();
        QueryWrapper queryWrapper = new QueryWrapper(  );
        queryWrapper.eq( "pid",id );
        List<ProductType> productTypes = productTypeService.list(  queryWrapper);
        return  respon.success( productTypes ) ;
    }


    @ApiOperation(value="添加/修改产品", notes="添加/修改产品，修改要带id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="Product", name = "Product", value = "Product实体类", required = false, dataType = "Product"),
    })
    @PostMapping("/insertPoroduct")
    @Transactional
    public Respon insertPoroduct(Product product)throws Exception{
        Respon respon = this.getRespon();
        //防止前端篡改转态
        if(ProductStaut.WATI_LOOK.getCode() !=product.getStaut() && ProductStaut.WAIT_SUBMIT.getCode()!=product.getStaut()
                && ProductStaut.OFF_LINE.getCode()!=product.getStaut()){
            return respon.error( "数据提交异常" );
        }

        if(StringUtils.isBlank( product.getId() )){
           product.setCreatedate( new Date(  ) );
           product.setCreateid( LoginUserUtils.getUserId() );
        }
        if(productService.saveOrUpdate(product  )){

            if(ProductStaut.WATI_LOOK.getCode()==product.getStaut()){ //提交审核
                MsgUtil.addMsg( product.getProductname(),WorkReminder.Work.product_commit_success.getName(),null,LoginUserUtils.getLoginUser().getUserId()  , LoginUserUtils.getLoginUser().getUserId(), null);
            }

            return respon.success( "提交成功" );
        }
        return  respon.error( "提交失败" ) ;
    }

    @ApiOperation(value="修改状态", notes="修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="id", name = "id", value = "", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="staut", name = "staut", value = "", required = false, dataType = "int")
    })
    @PostMapping("/updatestaut")
    public Respon updatestaut(String  id,int staut)throws Exception{
        Respon respon = this.getRespon();
        //防止前端篡改转态
        if(ProductStaut.WATI_LOOK.getCode() !=staut && ProductStaut.WAIT_SUBMIT.getCode()!=staut
                && ProductStaut.OFF_LINE.getCode()!=staut){
            return respon.error( "数据提交异常" );
        }
        Product product = productService.getById( id );
        if(null!=product){
            product.setStaut( staut );
            if(productService.saveOrUpdate(product  )){
                if(ProductStaut.WATI_LOOK.getCode()==product.getStaut()){ //提交审核
                    MsgUtil.addMsg( product.getProductname(),WorkReminder.Work.product_commit_success.getName(),null,LoginUserUtils.getLoginUser().getUserId()  , LoginUserUtils.getLoginUser().getUserId(), null);
                }
//                if(ProductStaut.OFF_LINE.getCode()==product.getStaut()){ //下线
//                    MsgUtil.addMsg( product.getProductname(),WorkReminder.Work.product_offline.getName(),null,LoginUserUtils.getLoginUser().getUserId() , LoginUserUtils.getLoginUser().getUserId(), product.getProductname()+"已下线" );
//                }
                return respon.success( "提交成功" );
            }
        }else{
            respon.error("该商品不存在");
        }

        return  respon.error( "提交失败" ) ;
    }

    //根据状态查询
    @ApiOperation(value="根据状态查询", notes="根据状态查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "staut", value = "1：待提交 2：待审核  3：下线  4：审核不通过 5：上线", required = false, dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "size", value = "每页条数", required = false, dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "current", value = "第几页", required = false, dataType = "int")
    })
    @PostMapping("/findbystaut")
    public Respon findbystaut(MyPage page, int staut){
        Respon respon = this.getRespon();
        try {
            QueryWrapper que  = new QueryWrapper(  );
            que.eq( "createid" ,LoginUserUtils.getUserId() );
            que.eq( "staut" ,staut);
            que.orderByDesc( "createdate" );
            IPage<Product> products  = productService.page(page ,que );
            respon.success(  products,page);
        }catch (Exception e){
            e.printStackTrace();
              respon.error( "查询失败" ) ;
        }
        return  respon ;
    }


    @ApiOperation(value="删除", notes="删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = false, dataType = "id")
    })
    @PostMapping("/delect")
    public Respon delect(String id){
        Respon respon = this.getRespon();
        try {
            if(productService.removeById( id)){
                respon.success( "删除成功" );
            }
        }catch (Exception e){
            e.printStackTrace();
            respon.error( "删除成功" ) ;
        }
        return  respon ;
    }


    @ApiOperation(value="根据id查询看详情", notes="根据id查询看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = false, dataType = "id"),

    })
    @PostMapping("/findbyid")
    public Respon findbyid(String id){
        Respon respon = new Respon(  );
        try {
            Product product = productService.getById(id);
            respon.success( productTypeService.getProductvo(product  ));
        }catch (Exception e){
            e.printStackTrace();
            respon.error("系统异常") ;
        }
        return  respon ;
    }

    @ApiOperation(value="跳到产品详情界面", notes="跳到产品详情界面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "size", value = "显示条数", required = false, dataType = "int"),
    })
    @GetMapping("/toproseroder")
    @Transactional
    public ModelAndView toproseroder(Integer size){
        PageData pd = this.getPageData();
        ModelAndView view = new ModelAndView(  );
        try {
            Product product = productService.getById(pd.getString( "id" ));
            view.addObject( "productinfo",productTypeService.getProductvo(product  ));
            QueryWrapper queryWrapper = new QueryWrapper(  );
            queryWrapper.eq( "createid",product.getCreateid() );
            ServiceProvider serverinfo = serprociderService.getOne( queryWrapper );
            view.addObject( "serverinfo",serprociderService.getOne( queryWrapper ));
            //相关推荐
            pd.put( "createid",product.getCreateid() );
            pd.put( "productid",product.getId() );
            List<Product> promote = productService.promoteproduct(pd);
            List<ProductVo> ableprod = new ArrayList<>(  );
            int flag =0;
            if(null==size){
                size = 5;
            }
            for(Product  info: promote){
                ProductVo productVo = productTypeService.getProductvo( info );
                if(productVo.isIsapply()){ //可以申报的项目
                	ableprod.add( productVo );
                    flag ++;
                }
                if(flag>=size){
                    break ;
                }
            }
            view.addObject( "promote",ableprod );
            product.setBrowsenum( product.getBrowsenum() );//设置浏览量
            productService.saveOrUpdate( product );
        }catch (Exception e){
            e.printStackTrace();
        }
        view.setViewName( "/trading/transaction_details" );
        return  view;
    }


    //查询各种状态条数
    @ApiOperation(value="查询各种状态条数", notes="查询各种状态条数")
    @GetMapping("/findstautnum")
    public Respon findstautnum(){
        Respon respon = this.getRespon();
        try {
            List<PageData> num = productService.findstautnum(LoginUserUtils.getUserId());
            respon.success(  productService.getstautname( num ));
        }catch (Exception e){
            e.printStackTrace();
            respon.error("系统异常"+e) ;
        }
        return  respon ;
    }

    @ApiOperation(value="产品树", notes="产品树")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "产品名称", required = false, dataType = "String"),
    })
    @PostMapping("/treelist")
    public Respon treelist( )throws Exception{
        PageData pd = this.getPageData();
        Respon respon = this.getRespon();
        try {
            QueryWrapper<ProductType> que = new QueryWrapper<>(  );
//            if(null!=pd.getString( "name" )&& StringUtils.isNotBlank(pd.getString( "name" ))){
//                que.eq( "name",pd.getString( "name" ) );
//            }
            que.orderByAsc( "sort" );
            que.eq( "active",1 );
            List<ProductType> list =  productTypeService.list(  que);
            List<TreeEntity> source = new ArrayList<TreeEntity>();
          
            source.addAll(list);
            List<TreeEntity> treelist = new TreeBuilder(source).buildTree();
            for(TreeEntity info : treelist){
                List<TreeEntity> twolist =info.getChildren();
                for( TreeEntity twoinfo:twolist){
                    ProductType productType = (ProductType) twoinfo;
                   if(StringUtils.isNotBlank( productType.getTwoflag() )){
                       twoinfo.setFlag( Arrays.asList( productType.getTwoflag().split( "," ) ) );
                   }
                }
            }
            respon.success(treelist);
        }catch(Exception e) {
            respon.error(e);
            e.printStackTrace();
        }
        return respon;
    }


    //根据店铺查商品 （店铺分为个人店铺和服务机构店铺） 商品项目类型统计

    @ApiOperation(value="跳转到店铺查商品页面", notes="根据店铺查商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "店铺id", required = false, dataType = "String"),
    })
    @GetMapping("/getlistprobyshop")
    public ModelAndView getlistprobyshop( )throws Exception{
        ModelAndView view = new ModelAndView(  );
        PageData pd = this.getPageData();
        try {
            if(StringUtils.isBlank( pd.getString( "id" ) )){
                return null;
            }
            ServiceProvider  serviceProvider = serprociderService.getById(pd.getString( "id" )  );
            view.addObject( "serviceProvider" ,serviceProvider);
            if(null!=serviceProvider){
                List<PageData>  protypenum =  productService.getnumprolist(serviceProvider.getCreateid());
                view.addObject( "protypenum",protypenum );
            }
        }catch(Exception e) {
           e.printStackTrace();
        }
        view.setViewName( "/trading/business_shops" );
        return view;
    }


    @ApiOperation(value="根据店铺查商品", notes="根据店铺查商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "createid", value = "店铺创建人id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "producttypeone", value = "项目类型一级id" ,dataType = "String")
    })
    @PostMapping("/topageserpro")
    public Respon topageserpro( MyPage page)throws Exception{
        Respon respon = new Respon(  );
        PageData pd = this.getPageData();
        try {
            page.setPd( pd );
            List<Product> lists = productService.getprolistbyser(page);
            List<ProductVo> productVos = new ArrayList<>(  );
            for(Product info : lists){
               ProductVo productVo =  productTypeService.getProductvo(info  );
               productVos.add( productVo );
            }
            respon.success( productVos,page );
        }catch(Exception e) {
            e.printStackTrace();
            respon.error( "系统异常"+e );
        }
        return respon;
    }

    @ApiOperation(value="交易中心", notes="交易中心")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页",  dataType = "String"),
            @ApiImplicitParam(name = "size", value = "页数" ,dataType = "String"),
            @ApiImplicitParam(name = "producttypeone", value = "项目类型一级id" ,dataType = "String"),
            @ApiImplicitParam(name = "producttypetwo", value = "项目类型二级id" ,dataType = "String"),
            @ApiImplicitParam(name = "producttypethree", value = "项目类型三级id" ,dataType = "String"),
            @ApiImplicitParam(name = "productname", value = "项目名称" ,dataType = "String"),
            @ApiImplicitParam(name = "plevel", value = "产品级别  1：国家 2：省级 3：市级  4：区级" ,dataType = "String"),
            @ApiImplicitParam(name = "provice", value = "省" ,dataType = "String"),
            @ApiImplicitParam(name = "city", value = "市" ,dataType = "String"),
            @ApiImplicitParam(name = "area", value = "区" ,dataType = "String")
    })
    @PostMapping("/findtypepro")
    public Respon findtypepro( MyPage page)throws Exception{
        Respon respon = new Respon(  );
        PageData pd = this.getPageData();
        try {
            page.setPd( pd );
            List<PageData> lists = productService.findpagepro(page);
            if(StringUtils.isNotBlank( pd.getString( "productname" ) )){
                SearchRecordUtil.searchRecord(pd.getString( "productname" ), (int)page.getTotal(),"t_sys_product");
            }
            for(PageData info : lists){
                productTypeService.getPageDatevo(info);
            }
            respon.success( lists,page );
        }catch(Exception e) {
            e.printStackTrace();
            respon.error( "系统异常"+e );
        }
        return respon;
    }
    @ApiOperation(value="跳转到产品专题页面", notes="跳转到产品专题页面")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = false, dataType = "id"),

    })
    @GetMapping ("/topagespecial")
    public ModelAndView topagespecial(Integer producttypeone,Integer productTypetwo,Integer producttypethree){
        ModelAndView view = new  ModelAndView();
        try {
            ProductType prone = productTypeService.getById( producttypeone );
            view.addObject( "proone", prone);
            QueryWrapper queryWrapper = new QueryWrapper(  );
            queryWrapper.eq( "pid",producttypeone );
            List<ProductType> protwo = productTypeService.list(  queryWrapper);
            view.addObject( "protwo", protwo);
//                if(null == productTypetwo  ){
//                 productTypetwo =protwo.get(0).getId();
//
//                }
//                QueryWrapper querytwo = new QueryWrapper(  );
//                querytwo.eq( "pid",productTypetwo );
//             List<ProductType> prothere = productTypeService.list(  querytwo);
//
//                    List<ProductType> prothere = productTypeService.list(  querytwo);
//                    view.addObject( "prothere", prothere);

//                }else{
//                    QueryWrapper querytwo = new QueryWrapper(  );
//                    querytwo.eq( "pid",protwo.get( 0 ).getId() );
//                    List<ProductType> prothere = productTypeService.list(  querytwo);
//                    view.addObject( "prothere", prothere);
//                }
            view.addObject( "producttypeone", producttypeone);
            view.addObject( "producttypetwo", productTypetwo);
            view.addObject( "producttypethree", producttypethree);
        }catch (Exception e){
            e.printStackTrace();

        }
            view.setViewName( "/trading/transaction" );
            return  view ;
}


//根据店铺查商品 （店铺分为个人店铺和服务机构店铺） 商品项目类型统计

    @ApiOperation(value="获取更多产品类型", notes="获取更多产品类型")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "一级id", required = false, dataType = "String"),
    })
    @PostMapping ("/getprotybyid")
    public Respon getlistprobyshop(int id )throws Exception{
        Respon respon = this.getRespon();
        QueryWrapper queryWrapper = new QueryWrapper(  );
        queryWrapper.eq( "pid",id );
        List<ProductType> twoprotype = productTypeService.list(  queryWrapper);
        List<PageData>  dataList = new ArrayList<>(  );
        if(twoprotype.size()>0){

            for(int j =0;j<twoprotype.size();j++){
                QueryWrapper quer = new QueryWrapper(  );
                quer.eq( "pid",twoprotype.get( j ).getId() );
                quer.orderByAsc( "sort" );
                List<ProductType> three = productTypeService.list(  quer);
                if(three.size()>0) {
                    if (j < 6) {
                        //从第5条开始取
                        for (int i = 4; i < three.size(); i++) {
                            PageData pd = new PageData();
                            pd.put( "name", three.get( i ).getName() );
                            pd.put( "producttypeone", id );
                            pd.put( "producttypetwo", twoprotype.get( j ).getId() );
                            pd.put( "producttypethree", three.get( i ).getId() );
                            dataList.add(pd  );
                        }
                    }else{
                        for (ProductType thr:three) {
                            PageData pd = new PageData();
                            pd.put( "name",thr.getName() );
                            pd.put( "producttypeone", id );
                            pd.put( "producttypetwo", twoprotype.get( j ).getId() );
                            pd.put( "producttypethree", thr.getId() );
                            dataList.add(pd  );
                        }
                    }
                }
            }
        }

        return respon.success( dataList );
    }
}

