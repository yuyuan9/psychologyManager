package com.ht.biz.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.ProductService;
import com.ht.biz.service.ShoppingCartService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Product;
import com.ht.entity.biz.product.ShoppingCart;
import com.ht.entity.shiro.SysUser;
import com.ht.utils.LoginUserUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value="/ht-biz/shopcart")
@Api(value="ShoppingCartController",description = "产品购物车前端管理")
public class ShoppingCartController extends BaseController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductService productService;
	
	@ApiOperation(value="用户购物车数据")
	@GetMapping("list")
	public Respon list(MyPage page){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				pd.add("userId", user.getUserId());
				page=getMyPage(pd);
				List<PageData> list=shoppingCartService.findList(page);
				page.setRecords(list);
				respon.success(page);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="购物车数据添加")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "productId", value = "用户选择产品id",  dataType = "string"),
        @ApiImplicitParam(paramType="query",name = "counts", value = "数量",  dataType = "int"),
	})
	@PostMapping("save")
	public Respon save(ShoppingCart shoppingCart){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				QueryWrapper<ShoppingCart> qw=new QueryWrapper<ShoppingCart>();
				qw.eq("productId", shoppingCart.getProductId());
				qw.eq("userId", user.getUserId());
				ShoppingCart s=shoppingCartService.getOne(qw, false);
				Product p=productService.getById(shoppingCart.getProductId());
				shoppingCart.setServiceId(p==null?null:p.getCreateid());
				if(s==null){
					shoppingCart.setUserId(user.getUserId());
					shoppingCart.setCreatedate(new Date());
					shoppingCart.setCreateid(user.getUserId());
					shoppingCart.setRegionid(user.getCompanyid());
					shoppingCartService.save(shoppingCart);
				}else{
					shoppingCart.setId(s.getId());
					shoppingCart.setCounts(shoppingCart.getCounts()+s.getCounts());
					shoppingCart.setLastmodified(new Date());
					shoppingCartService.updateById(shoppingCart);
				}
				//shoppingCartService.save(shoppingCart);
				respon.success(shoppingCart);
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="购物车数据删除")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query",name = "productIds", value = "用户选择产品id集合",  dataType = "object"),
	})
	@GetMapping("deleted")
	public Respon deleted(String productIds){
		Respon respon=this.getRespon();
		try {
			SysUser user=LoginUserUtils.getLoginUser();
			if(user!=null){
				if(StringUtils.isNotBlank(productIds)){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("userId", user.getUserId());
					for(String productId:productIds.split(",")){
						map.put("productId", productId);
						shoppingCartService.removeByMap(map);
					}
				}
				respon.success("删除成功");
			}else{
				respon.loginerror("未登录");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
