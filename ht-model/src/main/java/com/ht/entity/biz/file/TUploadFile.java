package com.ht.entity.biz.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ht.entity.base.BaseEntity;

public class TUploadFile  extends BaseEntity {
    /*
     * 文件名
     */
    @TableField(value="fileName")
    private String fileName;

    /*
     * 扩张名
     */
    private String ext;

    /*
     * 上传后文件名
     */
    @TableField(value="originalName")
    private String originalName;

    /*
     * 文件路径
     */
    private String path;
    private String pdfpath;
    /*
     * 文件大小
     */
    private Long size;

    /*
     * 类类型行
     */
    @TableField(value="clazzName")
    private String clazzName;
    @TableField(value="osspath")
    private String osspath;
    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getOsspath() {
        return osspath;
    }

    public void setOsspath(String osspath) {
        this.osspath = osspath;
    }
}
