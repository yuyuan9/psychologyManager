package com.ht.entity.biz.product.productenum;

public enum ProductStaut {

    //状态 1：待提交 2：待审核  3：下线  4：审核不通过 5：上线
    WAIT_SUBMIT(1,"待提交"),
    WATI_LOOK(2,"待审核"),
    OFF_LINE(3,"下线"),
    APPLY_FAILE(4,"审核不通过"),
    ON_LINE(5,"上线");
//    REGION_COMPANY("REGION_COMPANY","区域公司"),//區域公司


    private Integer  code;

    private String value;


    ProductStaut(int  code, String value) {
        this.code = code;
        this.value = value;
    }


    public static Integer getCode(int  code) {
        ProductStaut[] imageFormatTypes = values();
        for (ProductStaut imageFormatType : imageFormatTypes) {
            if (imageFormatType.code==code) {
                return imageFormatType.code;
            }
        }
        return  null;
    }



    public static String getValue(Integer code) {
        ProductStaut[] imageFormatTypes = values();
        for (ProductStaut imageFormatType : imageFormatTypes) {
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