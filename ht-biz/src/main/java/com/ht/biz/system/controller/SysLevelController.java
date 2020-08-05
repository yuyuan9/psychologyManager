package com.ht.biz.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ht.biz.service.LevelService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.sys.Level;
import com.ht.utils.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


/*
 * 权限
 */

@RequestMapping(value = "/ht-biz/level")
@RestController
@Api(value = "SysLevelController", description = "用户等级")
public class SysLevelController extends BaseController {
	
	@Autowired
	private LevelService levelService;
	
	
	/**
	 * 列表
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页", dataType = "String"),
			@ApiImplicitParam(name = "size", value = "页数", dataType = "String"),
			@ApiImplicitParam(name = "keyword", value ="关键字", dataType = "String")

	})
	@GetMapping(value="/list")
	@ApiOperation(value = "用户等级列表", notes = "")
	public Respon list(){
		Respon respon=this.getRespon();
		PageData pd = this.getPageData();
		MyPage page = this.getMyPage( pd );
		try {
			Page<Level> levelLst=levelService.findAllList(page);
			respon.success( levelLst );
		}catch (Exception e){
			e.printStackTrace();
			respon.error( e );
		}


		return respon;
	}
	
	@PostMapping(value="/save")
	@ApiOperation(value = "添加等级制度", notes = "")
	public Respon save(@RequestBody Level level) throws Exception{
			Respon	respon = this.getRespon();
			Level l=levelService.findByName(level.getName());
			if(l==null){
				level.setCreatedate(new Date());
				level.setCreateid(LoginUserUtils.getUserId() );
				levelService.save(level);
				respon.success( level );
			}else{
				respon.error( "该名称已存在" );
			}
			return respon;
	}
	
	@PostMapping("/edit")
	@ApiOperation(value = "编辑", notes = "")
	public Respon edit(@RequestBody Level level)throws Exception{
		Respon	respon = this.getRespon();
	    Level odllevel = levelService.getById(level.getId());
		if(!odllevel.getName().equals( level.getName() )){
			Level l=levelService.findByName(level.getName());
			if(l==null){
				levelService.saveOrUpdate(level);
				respon.success( null );
			}else{
				respon.error( "该名称已存在" );
			}
		}else{
			levelService.saveOrUpdate(level);
			respon.success( null );
		}
		return respon;
	}
	
	@GetMapping(value="/delete")
	@ApiOperation(value = "删除", notes = "")
	public Respon delete(String id)throws Exception{
		Respon	respon = this.getRespon();
		if(levelService.removeById(id)){
			respon.success( "删除成功" );
		}else{
			respon.success( "删除失败" );
		}
		return respon;
	}
	

}
