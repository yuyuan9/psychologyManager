package com.ht.entity.shiro;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
@TableName(value="t_sys_sms_records")
public class SendCode extends BaseEntity {

    private int deleted;
    private String content;
    @TableField(value="error_info")
    private String errorinfo;
    @TableField(value="is_available")
    private int isavailable;
    private String phone;
    private String receiver  ;
    @TableField(value="sms_type")
    private int smstype;
    @TableField(value="sms_send_immediately")
    private int smssendimmediately;
    @TableField(value="sms_fail_desc")
    private String smsfaildesc;
    @TableField(value="sms_fail_date")
    private String smsfail_date;
    @TableField(value="sms_fail_num")
    private int smsfailnum;
    @TableField(value="sms_send_success")
    private int smssendsuccess;
    @TableField(value="sms_template")
    private String smstemplate;
    private String ip;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public int getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(int isavailable) {
        this.isavailable = isavailable;
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

    public int getSmstype() {
        return smstype;
    }

    public void setSmstype(int smstype) {
        this.smstype = smstype;
    }

    public int getSmssendimmediately() {
        return smssendimmediately;
    }

    public void setSmssendimmediately(int smssendimmediately) {
        this.smssendimmediately = smssendimmediately;
    }

    public String getSmsfaildesc() {
        return smsfaildesc;
    }

    public void setSmsfaildesc(String smsfaildesc) {
        this.smsfaildesc = smsfaildesc;
    }

    public String getSmsfail_date() {
        return smsfail_date;
    }

    public void setSmsfail_date(String smsfail_date) {
        this.smsfail_date = smsfail_date;
    }

    public int getSmsfailnum() {
        return smsfailnum;
    }

    public void setSmsfailnum(int smsfailnum) {
        this.smsfailnum = smsfailnum;
    }

    public int getSmssendsuccess() {
        return smssendsuccess;
    }

    public void setSmssendsuccess(int smssendsuccess) {
        this.smssendsuccess = smssendsuccess;
    }

    public String getSmstemplate() {
        return smstemplate;
    }

    public void setSmstemplate(String smstemplate) {
        this.smstemplate = smstemplate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
