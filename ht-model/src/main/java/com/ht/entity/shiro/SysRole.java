package com.ht.entity.shiro;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author huitu123
 * @since 2018-01-23
 */
@TableName("t_sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String role;//角色名称
    
    private String code;//角色编码
    
    private String description;//描述
    
    private Date createdate;//创建时间
    
    private Boolean active=true;


    public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Override
    public String toString() {
        return "SysRole{" +
        "id=" + id +
        ", description=" + description +
        ", role=" + role +
        "}";
    }
}
