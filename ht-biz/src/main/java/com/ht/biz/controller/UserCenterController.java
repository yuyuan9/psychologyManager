package com.ht.biz.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ht.biz.service.WorkReminderRecordService;
import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.biz.msg.WorkReminderRecord;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/ht-biz/usercenter")
@Api(value="UserCenterController",description = "个人中心")
public class UserCenterController extends BaseController{
	
	@Autowired
	private WorkReminderRecordService workReminderRecordService;
	
	
	@GetMapping(value="/user/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("user/"+page);
	}
	
	@GetMapping("msgList")
	public Respon list(MyPage<WorkReminderRecord> page) throws Exception{
		Respon respon=this.getRespon();
		try {
			Map<String,Object > map=getLoginInfo();
			if(map!=null){
				QueryWrapper<WorkReminderRecord> qw=new QueryWrapper<WorkReminderRecord>();
				qw.eq("userId", map.get("userId"));
				qw.orderByDesc("createdate");
				workReminderRecordService.page(page, qw);
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
	@GetMapping("deleted")
	public Respon deleted(String id){
		Respon respon=this.getRespon();
		try {
			if(StringUtils.isNotBlank(id)){
				workReminderRecordService.removeById(id);
			}
			respon.success("成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respon.error(e);
		}
		return respon;
	}
}
