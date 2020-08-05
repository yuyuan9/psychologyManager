package com.ht.entity.biz.product.productenum;

public enum Productplevel {

    //状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
   PRO_COUNTRY(1,"国家"),
   PRO_PROVICE(2,"省级"),
   PRO_CITY(3,"市级"),
   PRO_AREA(4,"区级");
//    REGION_COMPANY("REGION_COMPANY","区域公司"),//區域公司


    private Integer  code;

    private String value;


    Productplevel(int  code, String value) {
        this.code = code;
        this.value = value;
    }


    public static Integer getCode(int  code) {
        Productplevel[] imageFormatTypes = values();
        for (Productplevel imageFormatType : imageFormatTypes) {
            if (imageFormatType.code==code) {
                return imageFormatType.code;
            }
        }
        return  null;
    }



    public static String getValue(int code) {
        Productplevel[] imageFormatTypes = values();
        for (Productplevel imageFormatType : imageFormatTypes) {
            if (imageFormatType.code==code) {
                return imageFormatType.value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}