package com.ht.entity.biz.comment;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.commons.utils.StringUtil;
import com.ht.entity.base.BaseEntity;
import com.ht.entity.shiro.SysUser;
import com.ht.entity.tree.EntityTree;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Library;

/*
 * 评论实体
 */
@TableName(value="t_evaluate_web")
public class EvaluateWebVO extends BaseEntity {
	
	/*	 * 评价星数	 */
	private Integer star;
	/*	 * 目标id	 */
	 @TableField(value="targetId")
	private String targetId;
	/*	 * 评论目标类	
	 * com.fh.entity.system.OnlineTraining 在线培训
	 * com.fh.entity.system.Library   高企文库
	 * 
	 *  */
	 @TableField(value="className")
	private String className=Library.class.getName();
	/*	 * 评论内容	 */

	private String content;
	/*	 * 评论父类id	 */
	private String pid;
	/*	 * 评论用户id	 */
	 @TableField(value="userId")
	private String userId;
	/* 关联用户*/
	private SysUser user;
	 @TableField(value="userName")
	private String userName;
	 @TableField(value="headImg")
	private String headImg;
	
	
	/*
	 * 异常   举报
	 */
	private boolean exception=false;
	 @TableField(value="exceptionId")
	private String exceptionId;
	
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		if(!StringUtils.isBlank(userName)){
			return StringUtil.getPhoneForUserName(userName);
		}
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public boolean isException() {
		return exception;
	}
	public void setException(boolean exception) {
		this.exception = exception;
	}
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "EvaluateWeb [star=" + star + ", targetId=" + targetId + ", className=" + className + ", content="
				+ content + ", userId=" + userId + ", userName=" + userName + ", headImg=" + headImg + ", exception="
				+ exception + ", exceptionId=" + exceptionId + "]";
	}
	
	
	
	
}
