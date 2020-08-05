package com.ht.entity.biz.honeymanager;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

/*
 * 奖励制度
 */
@TableName("t_reward_rule")
public class RewardRule extends BaseEntity {
	// 规则名称
	@TableField(value="name")
	private String name;
	//所属板块
	@TableField(value="modular")
	private String modular;
	// 规则代码 
	@TableField(value="code")
	private String code;
	// 计算结果
	@TableField(value="returnValue")
	private String returnValue;
	// 0:honey 1:元
	@TableField(value="type")
	private Integer type = 0;
	// 排序
	@TableField(value="sort")
	private Integer sort;
	@TableField(value="remark")
	private String remark;
	@TableField(value="")//0表示暂不使用||1表示启用
	private Boolean deleted=false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}



	// 逻辑类型
	public enum Code {
		reg_user("普通用户注册"),
		reg_serv("服务商注册"),
	    reg_company_user("企业注册"),
		recommend_reg("推荐注册"),
		perfect_info("完善个人中心基本信息（企业+个人）"),
		perfect_serv_info("完善服务商信息"),
		login_day("每天登陆"),
		login_day_total_10("累计登陆10天"),
		login_day_total_20("累计登陆20天"),
		login_day_total_365("累计登陆365天"),
		condit_compare("现状对比表"),
		project_matching("项目匹配表"),
		his_pdf_export("免费评估pdf导出"),
		poli_pro_counts("政策库、立项库查询需要扣除1honey值每次"),
		poli_pro_export("政策库、立项库导出需要扣除honey值（此值为动态，默认为-1）"),
		add_honey("添加honey值"),
		honey_recharge("honey充值"),
		download_file("下载文档"),
		library_resource("资源包下载"),
		server_transaction("服务交易"),
		join_guarantee("加入保障计划"),
		com_pay_ask("完成付费咨询"),
		com_pro_review("完成项目评审"),
		query_smalltech("查询中小企业"),
//		// 注册机构
//		reg_org("注册机构"),
//		
		reg_expert("注册专家"),
//		// 推荐注册服务商

//		// 每天登陆
//		login_day("每天登陆"),
//		// 发布问题
		release_problem("发布问题"),
//		// 回答问题
//		//answer_problem("回答问题")
//		
//		// 发布问题
//	    release_problem("发布问题"),
//	    //上线500
	    answer_problem_yes("解答别人的求助(被采纳)"),
	    answer_problem_no("解答别人的求助(未被采纳)"),
	    evaluate_answer("评价答案"),
//	    share_word("共享文档"),
	    release_oration("发布资讯"),
//	    
//	    
//	    
//	    model_finsh_0_5_day("模块服务提前1~5天完成"),
//	    model_finsh_6_10_day("模块服务提前6~10天完成"),
//	    model_finsh_ge_11_day("模块服务提前11天及以上完成"),
//	    
	    serv_get_good("服务商获得下单人主动好评(服务好评)"),
	    serv_get_sys_good("服务商获得系统好评"),
//	    serv_get_medium("服务商获得下单人中评"),
	    serv_get_poor("服务商获得下单人差评(服务差评)"),
	    server_obtain_poor("服务商获得下单人主动差评(咨询差评)"),
	    export_get_poor("专家获得下单人差评(服务差评)"),
	    export_obtain_poor("专家获得下单人主动差评(咨询差评)"),
//	    
//	    serv_expert_get_good("服务商获得专家好评"),
//	    serv_expert_get_medium("服务商获得专家中评"),
//	    serv_expert_get_poor("服务商获得专家差评"),
//	    
//	    download_file("下载文档"),
//	    
//	    web_share("网站分享"),
//	    reg_share("注册推荐"),
//	    comp_share("企业推荐"),
//	    
//	    honey_recharge("honey充值"),
//	    
//	    place_order_reward("下单奖励"),
//	    
	    server_main_evaluate("针对服务商服务进行主动评价"),
	    expert_main_evaluate("针对专家评审进行主动评价"),
//	    
//	    server_end_service_finish("按截止时间完成申报服务"),
//	    
	    server_obtain_praise("服务商获得下单人主动好评(咨询好评)"),
//	    
//	    server_next_single("下单人主动评价专家评审能力"),
//	    
//	    expert_finish_server_start_end("完成服务商提交初稿和终稿的审核"),
//	    
//	    expert_main_praise_server("主动评价服务商服务质量"),
//	    
//	    expert_place_evaluate("获得下单人主动好评"),
//	    
//	    expert_server_evaluate("获得服务商主动好评"),
//	    //server_obtain_medium("服务商获得下单人中评"),
//	    
//	    company_test_server("兑换企业体验"),
//
//        patent_analysis("兑换专利分析"),
//        policy_guidance("兑换政策辅导"),
//        planning_suggestion("兑换规划建议"),
//	    prolib_data_export("立项库数据导出"),
//	    plclib_data_export("政策库数据导出"),
//	    
//	    add_honey("内部员工添加1000honey值"),
//	    
	    sign_day("每日签到"),
//	    
	    forum_sys_jp("精品"),
//	    forum_sys_tw_delete("帖子提问被管理员删除"),
	    forum_sys_hd_delete("帖子回答被管理员删除"),
	    forum_sys_dz("帖子被点赞"),
	    forum_sys_ev("帖子被评价"),
//	    forum_sys_tw_hf("帖子提问被管理员恢复"),
//	    forum_sys_hd_hf("帖子回答被管理员恢复"),
//	    //forum_sys_hmd("违法规定纳入黑名单"),
//	    forum_my_tw("我回答了读者提问的帖子"),
//	    forum_my_hd("我回答点赞了读者提问的帖子"),
//	    
//	    
//	    sz_pg_pdf_down("评估报告下载"),
	    sz_pg("线上体检"),
	    sz_pg_port_down("体检报告下载"),
	    matadata_technology("科技部火炬统计企业年报"),
	    matadata_high_company("广东省高企运行情况数据登记"),
	    matadata_high_prehensive("高企综合统计快报表（企业表）"),
	    matadata_high_tech("广东省高新技术产品概况表（粤科102表）"),
	    matadata_soft_info("广东省软件和信息技术服务业统计报表（企业主要指标表）"),
	    matadata_tech_activity("企业科技活动及相关情况表（107-2表）"),
	    matadata_comp_tech_pro("企业科技项目情况表（107-1表）"),
	    matadata_year_high_comp("年度高新技术企业发展情况表"),
//	    compnew_export("报表导出"),
//	    register_company("完善企业信息"),
//	    policy_project_counts("政策库立项库查询次数"),
//	    //forum_my_dz("我点赞的帖子"),

	    match_export("政策智能匹配匹配表导出"),
//	    hight_declare("高企申报管理工具模板查看与下载"),
//	    add_other_worker("内部其他员工"),
	    agent("代理人"),
	    partner("合伙人"),
//	    platform_dealer("平台入驻商"),
	    platform_franchisee("平台合作加盟商"),
//	    update_honey("高企云后台honey更新"),
//	    
//	    red_envelopes_honey("honey值红包"),
	    company_policy("企政查报表导出"),
	    policydig_subscribe("政策速递订阅"),
	    train_class_download("培训课件下载"),
	    malice_comment("恶意评论"),
//	    huaian_policy("淮安政策匹配表下载"),
//	    huaian_compare("淮安现状对比下载")
	    honey_exchange("金币兑换honey值"),
	    policy_match_export("匹配结果导出"),
	    
	    
		;

		Code(String name) {
			this.name = name;
		}

		private String name;

		public String getName() {
			return name;
		}

	}



	public String getModular() {
		return modular;
	}

	public void setModular(String modular) {
		this.modular = modular;
	}
}
