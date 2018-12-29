package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtm on 2018/1/11.
 */

public class SimpleRepositoryModel {
    private List<SimpleRepositoryPositionModel> repositionPositionList;

    public List<SimpleRepositoryPositionModel> getRepositionPositionList() {
        return repositionPositionList;
    }

    public void setRepositionPositionList(List<SimpleRepositoryPositionModel> repositionPositionList) {
        this.repositionPositionList = repositionPositionList;
    }

    private Integer repositoryId;

    private String repositoryCode;

    private String repositoryName;

    private String address;

    private Integer scmId;

    private String memo;

    private Integer createBy;

    private Date createDate;

    private Date updateDate;

    private Integer sort;

    private Integer manager;

    private String managerPhone;

    private String managerName;

    private Boolean isAvailable;

    private Boolean isDelete;

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryCode() {
        return repositoryCode;
    }

    public void setRepositoryCode(String repositoryCode) {
        this.repositoryCode = repositoryCode == null ? null : repositoryCode.trim();
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName == null ? null : repositoryName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getScmId() {
        return scmId;
    }

    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone == null ? null : managerPhone.trim();
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName == null ? null : managerName.trim();
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
}
