package com.ht.entity.biz.honeymanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import com.ht.entity.shiro.SysUser;
@TableName("t_user_water_reward")
public class UserWaterReward extends BaseEntity {
    /*
     * 奖励规则外键
     */
	@TableField(value="rewardRuleId")
    private String rewardRuleId;
	@TableField(exist=false)
    private RewardRule rewardRule;

    // 积分数
	@TableField(value="point")
    private Double point=0d; 
    // 0:honey（积分） 1:元（现金）
	@TableField(value="type")
    private Integer type=0;
    // 0：收入不可以提现 1:收入可以提现
	@TableField(value="waterType")
    private Integer waterType = 0;
    //流水属于谁
	@TableField(value="userId")
    private String userId;
	@TableField(exist=false)
    private SysUser user;

    //是否作废
	@TableField(value="deleted")
    private Boolean deleted=false;
	@TableField(value="remark")
    private String remark;
	@TableField(value="targetId")
    private String targetId;
	@TableField(value="targetClass")
    private String targetClass;


    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getRewardRuleId() {
        return rewardRuleId;
    }

    public void setRewardRuleId(String rewardRuleId) {
        this.rewardRuleId = rewardRuleId;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWaterType() {
        return waterType;
    }

    public void setWaterType(Integer waterType) {
        this.waterType = waterType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public RewardRule getRewardRule() {
        return rewardRule;
    }

    public void setRewardRule(RewardRule rewardRule) {
        this.rewardRule = rewardRule;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



}

