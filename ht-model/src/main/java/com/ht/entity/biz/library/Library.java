package com.ht.entity.biz.library;



import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ht.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 政策解读库
 * 
 * @author jied
 *
 */
@ApiModel(value="文库实体类",description="Library")
@TableName(value="t_library")
public class Library extends BaseEntity implements java.io.Serializable {
	private String uploadusername; //上传用户名
	private String reason;//拒绝理由
	private String appimg;
	/** 地区*/
	private String region;
	/** 项目类*/
	private String type;
	
	/** 政策功能*/
    private String function;
    
    /** 政策标题*/
    private String title;
    /** 内**/
    private String content;
/*    private String contentPdf;//根据路径读不出来的暂时标记为“pdfexitbutreadnot”
*/    /** pdf路径*/
    private String pdfpath;
    /** 压缩文件路径*/
    private String zippath;
    /*
     * pdf 大小
     */
    private Long pdfsize;
    
    /*
     * 浏览量
     */
    private Integer browsecount;
    private Integer jbrowsecount;
    
    /*
     * 下载量
     */
    private Integer downcount;
    private Integer jdowncount;

	/*
	 * 转发量
	 */
	private Integer forwardcount;
	private Integer jforwardcount;

	/*
	 * 收藏量
	 */
	private Integer collectioncount;
	private Integer jcollectioncount;
    /**
     * 星级数(评分)
     */
    private String starlevel;
    
    /*
     * 上传人
     *  1：普通用户
     *  2：服务商
     */
    private Integer person;
    
    /*
     * 审核状态
     * 0 没有审核
     * 1 审核通过
     * -1 审核不通过
     */
    private Integer status;
    
    /*
     * 审核人
     */
    private String auditperson;
    //政策类型
    private Integer libtype; //公示名录0 培训资料1 模板范文2 其他3 政策解读4  政府文件5 申报通知6
    /*
     * 排序
     */
    private Integer sort;
    
    private String keywords;
    
	private String description;
	
/*//	private Integer countPerson;
*/	
	//下载的honey
	private Integer downloadhoney;
	private Integer solrindex;
	private Date updatetime;

	private String province;
	private String city;  //n
	private String area;
	private int istop; //是否置顶 默认0（未置顶） 1置顶 n

	/** 技术领域  **/
	private String technicalfield;
	/** 产业领域  **/
	private String industrialfield;
	/** 原始文件路劲**/
	private String originalfile;
	private Integer cold; //金币 //n

	private String osspath; //oss文件路径

	public Integer getCold() {
		return cold;
	}

	public void setCold(Integer cold) {
		this.cold = cold;
	}

	public String getUploadusername() {
		return uploadusername;
	}

	public void setUploadusername(String uploadusername) {
		this.uploadusername = uploadusername;
	}

	public String getOriginalfile() {
		return originalfile;
	}

	public void setOriginalfile(String originalfile) {
		this.originalfile = originalfile;
	}

	public String getTechnicalfield() {
		return technicalfield;
	}

	public void setTechnicalfield(String technicalfield) {
		this.technicalfield = technicalfield;
	}

	public String getIndustrialfield() {
		return industrialfield;
	}

	public void setIndustrialfield(String industrialfield) {
		this.industrialfield = industrialfield;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getZippath() {
		return zippath;
	}

	public void setZippath(String zippath) {
		this.zippath = zippath;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region; 
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPdfpath() {
		return pdfpath;
	}

	public void setPdfpath(String pdfpath) {
		this.pdfpath = pdfpath;
	}

	public Integer getBrowsecount() {
		return browsecount;
	}

	public void setBrowsecount(Integer browsecount) {
		this.browsecount = browsecount;
	}

	public Integer getDowncount() {
		return downcount;
	}



//	public String getContentPdf() {
//		return contentPdf;
//	}
//
//
//
//	public void setContentPdf(String contentPdf) {
//		this.contentPdf = contentPdf;
//	}

	public void setDowncount(Integer downcount) {
		this.downcount = downcount;
	}

	public String getStarlevel() {
		return starlevel;
	}

	public void setStarlevel(String starlevel) {
		this.starlevel = starlevel;
	}

	public Integer getPerson() {
		return person;
	}

	public void setPerson(Integer person) {
		this.person = person;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuditperson() {
		return auditperson;
	}

	public void setAuditperson(String auditperson) {
		this.auditperson = auditperson;
	}

	public Integer getLibtype() {
		return libtype;
	}

	public void setLibtype(Integer libtype) {
		this.libtype = libtype;
	}

	public Integer getSort() {
		return sort==null?0:sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getPdfsize() {
		return pdfsize;
	}

	public Integer getSolrindex() {
		return solrindex;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setSolrindex(Integer solrindex) {
		this.solrindex = solrindex;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public void setPdfsize(Long pdfsize) {
		this.pdfsize = pdfsize;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
/*	public Integer getCountPerson() {
		return countPerson;
	}

	public void setCountPerson(Integer countPerson) {
		this.countPerson = countPerson;
	}
*/
	public Integer getDownloadhoney() {
		return downloadhoney;
	}

	public void setDownloadhoney(Integer downloadhoney) {
		this.downloadhoney = downloadhoney;
	}

	public String getAppimg() {
		return appimg;
	}

	public void setAppimg(String appimg) {
		this.appimg = appimg;
	}


    public Integer getJbrowsecount() {
        return jbrowsecount;
    }

    public void setJbrowsecount(Integer jbrowsecount) {
        this.jbrowsecount = jbrowsecount;
    }

    public Integer getJdowncount() {
        return jdowncount;
    }

    public void setJdowncount(Integer jdowncount) {
        this.jdowncount = jdowncount;
    }

	public Integer getForwardcount() {
		return forwardcount;
	}

	public void setForwardcount(Integer forwardcount) {
		this.forwardcount = forwardcount;
	}

	public Integer getJforwardcount() {
		return jforwardcount;
	}

	public void setJforwardcount(Integer jforwardcount) {
		this.jforwardcount = jforwardcount;
	}

	public Integer getCollectioncount() {
		return collectioncount;
	}

	public void setCollectioncount(Integer collectioncount) {
		this.collectioncount = collectioncount;
	}

	public Integer getJcollectioncount() {
		return jcollectioncount;
	}

	public void setJcollectioncount(Integer jcollectioncount) {
		this.jcollectioncount = jcollectioncount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}


	public String getOsspath() {
		return osspath;
	}

	public void setOsspath(String osspath) {
		this.osspath = osspath;
	}
}
