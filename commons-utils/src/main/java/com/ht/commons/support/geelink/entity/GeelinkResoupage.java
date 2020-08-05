package com.ht.commons.support.geelink.entity;

public class GeelinkResoupage {
    private String id;
    private String uploadusername="高企云";
    private String createdate;
    private String title;
    private Integer browsecount;
    private Integer libtype=9; //9资源包

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getUploadusername() {
        return uploadusername;
    }

    public void setUploadusername(String uploadusername) {
        this.uploadusername = uploadusername;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBrowsecount() {
        return browsecount;
    }

    public void setBrowsecount(Integer browsecount) {
        this.browsecount = browsecount;
    }

    public Integer getLibtype() {
        return libtype;
    }

    public void setLibtype(Integer libtype) {
        this.libtype = libtype;
    }
}
