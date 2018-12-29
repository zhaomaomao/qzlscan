package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;

public class SysSetting {
    private Integer sysConfigId;

    private String name;

    private String code;

    private String value;

    private String info;

    private Boolean isAvailable;

    private Integer sort;
    private Integer type;

    public Integer getSysConfigId() {
        return sysConfigId;
    }

    public void setSysConfigId(Integer sysConfigId) {
        this.sysConfigId = sysConfigId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}