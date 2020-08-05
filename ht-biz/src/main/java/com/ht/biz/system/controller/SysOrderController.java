package com.ht.biz.system.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ht.biz.service.OrderService;
import com.ht.biz.service.ProductService;
import com.ht.biz.service.ProductTypeService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.product.Order;
import com.ht.entity.biz.product.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping(value = "/sys/ht-biz/order")
@Api(value = "SysOrderController", description = "后台订单管理")
public class SysOrderController extends BaseController{
	@Autowired
	private OrderService orderService;
	@Autowired
    private ProductService productService;
	@Autowired
    private ProductTypeService productTypeService;
	 
	@ApiOperation(value="后台订单数据查询接口")
	@ApiImplicitParams({
	    @ApiImplicitParam(paramType="query",name = "keyword", value = "关键字搜索",  dataType = "string"),
	})
	@PostMapping("list")
	public Respon list(@RequestBody PageData pd){
		Respon respon=this.getRespon();
		try {
			MyPage<PageData> page=getMyPage(pd);
			List<PageData> list=orderService.findlist(page);
			page.setRecords(list);
			respon.success(page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="后台订单数据详情")
	@ApiImplicitParams({
	    @ApiImplicitParam(paramType="query",name = "id", value = "数据id",  dataType = "string"),
	})
	@GetMapping("edit")
	public Respon edit(String id){
		Respon respon=this.getRespon();
		PageData pd=this.getPageData();
		try {
			Order order=null;
			if(StringUtils.isNotBlank(id)){
				pd.add("id", id);
				order=orderService.findById(pd);
				if(order!=null){
					Product product=productService.getById(order.getProductId());
					order.setProductVo(productTypeService.getProductvo(product  ));
				}
			}
			respon.success(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
	@ApiOperation(value="后台订单数据删除")
	@ApiImplicitParams({
	    @ApiImplicitParam(paramType="query",name = "ids", value = "数据ids",  dataType = "string"),
	})
	@GetMapping("deleted")
	public Respon deleted(String ids){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(ids)){
				for(String id:ids.split(",")){
					orderService.removeById(id);
				}
			}
			respon.success(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
