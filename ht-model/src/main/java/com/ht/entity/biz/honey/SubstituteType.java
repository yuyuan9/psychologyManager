package com.ht.entity.biz.honey;

public enum SubstituteType {

    TO_BE_AUDITED(1,"待审核"),
    EXAMINE_FAIL(2,"审核失败"),
    EXAMINE_ING(3,"审核中"),
    TRANSFER_FAIL(4,"转账失败"),
    TRANSFER_SUCESS(5,"转账成功");
    private Integer code;
    private String value;

    SubstituteType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
