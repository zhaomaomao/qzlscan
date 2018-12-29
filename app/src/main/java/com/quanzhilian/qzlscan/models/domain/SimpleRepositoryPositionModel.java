package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;

/**
 * Created by yangtm on 2018/1/11.
 */

public class SimpleRepositoryPositionModel {
    private Integer repositoryPositionId;

    private Integer scmId;

    private Integer repositoryId;

    private Integer sort;

    private Boolean isAvailable;

    private Boolean isDelete;

    private String name;

    private String code;

    private Integer createBy;

    private Date createDate;

    private String memo;

    private String repositoryName;

    public Integer getRepositoryPositionId() {
        return repositoryPositionId;
    }

    public void setRepositoryPositionId(Integer repositoryPositionId) {
        this.repositoryPositionId = repositoryPositionId;
    }

    public Integer getScmId() {
        return scmId;
    }

    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName == null ? null : repositoryName.trim();
    }
}
