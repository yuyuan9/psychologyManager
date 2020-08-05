package com.ht.entity.biz.product.productenum;

public enum ApplyType {

//  1 不是科技政策服务 false  科技项目分类true （：2全年申报  3 距离截止还有）  4申报结束
    No_SCIENCE (1,"非科技政策服务"),
    ALL_APPLY(2,"全年申报"),
    SECTION_APPLY(3,"申请时间为区段"),
    END_APPLU(4,"申报结束");
//    REGION_COMPANY("REGION_COMPANY","区域公司"),//區域公司


    private Integer  code;

    private String value;


    ApplyType(int  code, String value) {
        this.code = code;
        this.value = value;
    }


    public static Integer getCode(int  code) {
        ApplyType[] imageFormatTypes = values();
        for (ApplyType imageFormatType : imageFormatTypes) {
            if (imageFormatType.code==code) {
                return imageFormatType.code;
            }
        }
        return  null;
    }



    public static String getValue(int code) {
        ApplyType[] imageFormatTypes = values();
        for (ApplyType imageFormatType : imageFormatTypes) {
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
