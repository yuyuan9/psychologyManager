package com.ht.entity.biz.sys.message;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;

@TableName(value = "t_sys_sms_records")
public class SysSmsRecords extends BaseEntity {

	private Boolean deleted = false;
	private String content;
	private String error_info;
	private Boolean is_available = false; // 记录是否失效，0表示有效，1表示无效
	private String phone;
	private String receiver;
	private Integer sms_type;// 短信类型,0表示绑定手机发送验证码的短信，1表示一般短信
	private Integer sms_send_immediately;// 短信是否立即发送，0表示短信不立即发送，1短信表示立即发送
	private String sms_fail_desc;// '发送失败原因',
	private Date sms_fail_date;// '发送失败时间',
	private Integer sms_fail_num;// '发送失败次数',
	private Integer sms_send_success;// '短信是否发送成功，0表示短信发送未成功，1表示短信发送成功',
	private String sms_template;// '短信模版',
	private String ip;

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

	public Boolean getIs_available() {
		return is_available;
	}

	public void setIs_available(Boolean is_available) {
		this.is_available = is_available;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Integer getSms_type() {
		return sms_type;
	}

	public void setSms_type(Integer sms_type) {
		this.sms_type = sms_type;
	}

	public Integer getSms_send_immediately() {
		return sms_send_immediately;
	}

	public void setSms_send_immediately(Integer sms_send_immediately) {
		this.sms_send_immediately = sms_send_immediately;
	}

	public String getSms_fail_desc() {
		return sms_fail_desc;
	}

	public void setSms_fail_desc(String sms_fail_desc) {
		this.sms_fail_desc = sms_fail_desc;
	}

	public Date getSms_fail_date() {
		return sms_fail_date;
	}

	public void setSms_fail_date(Date sms_fail_date) {
		this.sms_fail_date = sms_fail_date;
	}

	public Integer getSms_fail_num() {
		return sms_fail_num;
	}

	public void setSms_fail_num(Integer sms_fail_num) {
		this.sms_fail_num = sms_fail_num;
	}

	public Integer getSms_send_success() {
		return sms_send_success;
	}

	public void setSms_send_success(Integer sms_send_success) {
		this.sms_send_success = sms_send_success;
	}

	public String getSms_template() {
		return sms_template;
	}

	public void setSms_template(String sms_template) {
		this.sms_template = sms_template;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
