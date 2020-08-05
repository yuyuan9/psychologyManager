package com.ht.entity.biz.msg;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName("t_work_reminder")
public class WorkReminder extends BaseEntity {

	/*
	 * 表达式
	 */
	@TableField(value="expression")
	private String expression;
	/*
	 * 所属板块
	 */
	@TableField(value="modular")
	private String modular;
	/*
	 * 代码
	 */
	@TableField(value="code")
	private String code;

	/*
	 * 消息类型 0 ：个人消息   1： 系统消息   2：论坛消息
	 */
	@TableField(value="msgType")
	private Integer msgType = 1;
	@TableField(value="action")
	private String action;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	

	public enum Work {
//		xd_001("平台最新优惠活动，如2017年高企认定第一批工作已开始，高企云限时9折优惠，[a href=\'${obj.url}\']点击查看优惠详情[/a]"), 
//		xd_002("您有一项评估信息未下单，[a href=\'${obj.url}\']去下单[/a]"), 
//		xd_003("您有一个订单未付款，[a href=\'${obj.url}\']去付款[/a]"), 
//		xd_004("您的订单${obj.orderCode}有了新的动态，[a href=\'${obj.url}\']点击查看详情[/a]"), 
//		xd_005("点击下载[a href=\'${obj.url}\']2017年广东省高新技术企业认定申报通知/指南[/a]"),
//		xd_006("点击下载[a href=\'${obj.url}\']2017年广东省高新技术企业培育库入库申报通知/指南[/a]"),
//        xd_007("您有一项兑换服务"),
//        
//		zj_001("您报名的订单${obj.orderCode}已被下单人选择成为服务商，[a href=\'${obj.url}\']点击查看详情[/a]"), 
//		zj_002("您服务的订单${obj.orderCode}目前已超出规定时间未完成，可能会影响您在平台的综合评价，请尽快完成服务内容，[a href=\'${obj.url}\']继续服务[/a]"), 
//		zj_003("2017年高企认定公示名单（第一批/第二批/第三批）已出，您有一个订单可申请余下服务奖励，点击[a href=\'${obj.url}\']了解详情【是否实现的了】[/a]"),
//
//		fws_001("您有一个被分配的评审任务，请尽快确认，点击[a href=\'${obj.url}\']了解详情[/a]"), 
//		fws_002("2017年高企认定公示名单（第一批/第二批/第三批）已出，您有一个评审可申请评审奖励，点击[a href=\'${obj.url}\']了解详情【是否实现的了】[/a]"),
//		
//		
//		forum_sys_jp("您提问的《${obj.name}》对企业朋友很有帮助，已被管理员选为<font color='red'>精品</font>"),
//		forum_sys_tw_delete("您提问的《${obj.name}》违反高企云轻社区规则，已被管理员选为<font color='red'>删除</font>"),
//		forum_sys_hd_delete("您在《${obj.name}》的回答违反高企云轻社区规则已被管理员<font color='red'>删除</font>"),
//		forum_sys_dz_dz("读者点赞了您的提问《${obj.name}》"),
//		forum_sys_tz_hf("您提问的《${obj.name}》已被管理员 恢复"),
//		forum_sys_hmd("您的账号由于多次违反高企云轻社区规则，已被管理员列入<font color='red'>黑名单</font>，如有疑问请联系客服"),
//		
//		forum_my_tw("您提问了《${obj.name}》"),
//		forum_my_hd_dz_tw("您回答了读者提问的《${obj.name}》"),
//		forum_my_dz_dz("您<font color='red'>点赞</font>了读者提问的《${obj.name}》"),
//		forum_sd_dz_dz("读者<font color='red'>点赞</font>了您的《${obj.name}》"),
//		forum_sd_dz_hd("读者<font color='red'>回答</font>了您的《${obj.name}》"),
//		forum_sys_caina("你的评论被采纳，已获取采纳的honey"),
//		forum_sys_caina2("你的悬赏值已经已进入被采纳的账户"),
		company_register("已注册成功，请完善企业信息，<a href=${url}>去完善</a>"),
		server_register("您已提交服务商信息，等待管理员审核中"),
		doc_upload_success("文档上传成功，请等待审核"),
		doc_check_success("文档审核通过"),
		doc_check_fail("文档审核失败，点击查看原因"),
		server_check_success("服务商审核通过，可完善店铺信息，<a href=${url}>去完善</a>"),
		server_check_fail("服务商审核失败，点击查看原因"),
		free_assess_save("评估数据已保存，<a href=${url}>继续评估</a>"),
		free_assess_result("评估报告已生成，<a href=${url}>点击查看</a>"),
		deal_keyword("您的反馈已处理"),
		honey_exchange("您已成功兑换${url}honey值，消耗${url}金币"),
		honey_cash("您的提现已处理"),
		gold_cash_apply_commit("金币提现申请已提交，等待管理员审核"),
		gold_cash_check_fail("金币提现审核失败，<a href=${url}>点击查看原因</a>"),
		gold_cash_accounts_fail("金币提现转账失败，<a href=${url}>点击查看原因</a>"),
		gold_cash_accounts_success("金币提现转账成功"),
		product_offline("${name}产品已被下线，查看原因"),
		product_commit_success("${name}产品已提交成功，等待平台管理员审核"),
		product_check_pass("${name}产品已审核通过，<a href=${url}>前往产品管理了解详情</a>"),
		product_check_unpass("${name}产品审核不通过，查看原因"),
		user_order_success("下单成功，等待服务商确认服务"),
		sprovider_order_success("<a href=${url}>有新订单，请前往订单管理查看详情</a>"),
		user_receive_complete("服务商已确认接单，交易完成"),
		sprovider_receive_complete("接单成功，交易完成"),
		user_order_refuse("服务商拒接订单，交易失败，您可前往交易大厅重新选择服务商"),
		sprovider_order_refuse("拒接成功，交易失败"),
		user_order_dispose("订单取消成功"),
		;
		
		private String value;

		Work(String _value) {
			this.value = _value;
		}

		public String getValue() {
			return this.value;
		}

		public String getName() {
			return this.name();
		}
	}
//	public static void main(String[] args) {
//		System.out.println(Work.free_assess_result);
//		System.out.println(Work.free_assess_result.name());
//		System.out.println(Work.free_assess_result.value);
//	}

	public String getModular() {
		return modular;
	}

	public void setModular(String modular) {
		this.modular = modular;
	}
}


