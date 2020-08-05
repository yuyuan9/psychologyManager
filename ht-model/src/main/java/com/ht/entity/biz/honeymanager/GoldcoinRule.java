package com.ht.entity.biz.honeymanager;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
/*
 * 金币规则
 */
import com.ht.entity.base.BaseEntity;
@TableName("t_gold_coin_rule")
public class GoldcoinRule extends BaseEntity{
	//操作详情
	private String name;
	//所属板块
	@TableField(value="modular")
	private String modular;
	//金币值
	private Double golds=0d;
	//规则代码
	private String code;
	//排序
	private Integer sort=0;
	//备注
	private String remark;
	//是否使用：//0表示暂不使用||1表示启用
	private Boolean deleted=false;
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public Integer getSort() {
		return sort;
	}
	public String getRemark() {
		return remark;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Double getGolds() {
		return golds;
	}
	public void setGolds(Double golds) {
		this.golds = golds;
	}
		// 逻辑类型
		public enum Gold {
			doc_check_success("文档审核成功"),
			honey_exchange("兑换honey值"),
			honey_cash("金币提现处理"),
			;
			
			Gold(String name) {
				this.name = name;
			}

			private String name;

			public String getName() {
				return name;
			}
		}
//	public static void main(String[] args) {
//		System.out.println(Gold.doc_check_success);
//		
//	}
		public String getModular() {
			return modular;
		}
		public void setModular(String modular) {
			this.modular = modular;
		}
}
