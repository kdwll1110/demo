package com.hyf.demo.enums;

public enum OperationTypeEnum {
    OTHER(0,"其他"),
    LOGIN(11,"登录"),
    ADDED(1,"新增"),
    Edit(2,"编辑"),
    UNDOCK(3,"移除"),
    AUTHORIZATION(4,"授权"),
    EXPORT(5,"导出"),
    IMPORT(6,"导入"),
    DOWNLOAD(7,"下载"),
    UPLOADING(8,"上传"),
    PASS(9,"审批通过"),
    REFUSE(10,"审批拒绝");

    private Integer code;

    private String info;

    OperationTypeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
