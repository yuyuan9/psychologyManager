package com.ht.entity.biz;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;

@ApiModel(value="用户银行卡",description="Userbank")
@TableName("t_sys_user_bank")
public class Userbank extends BaseEntity {
    private String acuountname; //户主
    private String banktype;    //银行类型
    private String carnumber;   //卡号
    private String phone; //手机号

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAcuountname() {
        return acuountname;
    }

    public void setAcuountname(String acuountname) {
        this.acuountname = acuountname;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }
}
