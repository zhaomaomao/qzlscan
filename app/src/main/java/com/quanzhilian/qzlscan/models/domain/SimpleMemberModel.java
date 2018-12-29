package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;

/**
 * Created by yangtm on 2018/1/11.
 */

public class SimpleMemberModel {
    private String beanCode;
    private SimpleCompanyModel company;

    private Integer memberId;

    private Integer companyId;

    private String companyName;

    private Integer dataSourceId;

    private Integer parentId;

    private Integer userType;

    private Boolean isAdmin;

    private String username;

    private String phone;

    private Boolean phoneValidated;

    private Boolean isDelete;

    private String email;

    private Boolean emailValidated;

    private String password;

    private Integer createBy;

    private Date createDate;

    private Date updateDate;

    private Boolean isAvailable;

    private Integer source;

    private String payPassword;

    private Integer initDataSourceId;

    private Integer bindStatus;

    private String nickName;

    private String contact;

    private String picturePath;

    private String picture;

    private String memo;

    private String identityNo;

    private Integer sex;

    private Boolean smsNotify;

    private String openid;

    private Boolean showVersionUpdate;

    private Integer initStep;

    private String fastMenuIds;

    public SimpleCompanyModel getCompany() {
        return company;
    }

    public void setCompany(SimpleCompanyModel company) {
        this.company = company;
    }

    public String getBeanCode() {
        return beanCode;
    }

    public void setBeanCode(String beanCode) {
        this.beanCode = beanCode;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getPhoneValidated() {
        return phoneValidated;
    }

    public void setPhoneValidated(Boolean phoneValidated) {
        this.phoneValidated = phoneValidated;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getEmailValidated() {
        return emailValidated;
    }

    public void setEmailValidated(Boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    public Integer getInitDataSourceId() {
        return initDataSourceId;
    }

    public void setInitDataSourceId(Integer initDataSourceId) {
        this.initDataSourceId = initDataSourceId;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo == null ? null : identityNo.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Boolean getSmsNotify() {
        return smsNotify;
    }

    public void setSmsNotify(Boolean smsNotify) {
        this.smsNotify = smsNotify;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Boolean getShowVersionUpdate() {
        return showVersionUpdate;
    }

    public void setShowVersionUpdate(Boolean showVersionUpdate) {
        this.showVersionUpdate = showVersionUpdate;
    }

    public Integer getInitStep() {
        return initStep;
    }

    public void setInitStep(Integer initStep) {
        this.initStep = initStep;
    }

    public String getFastMenuIds() {
        return fastMenuIds;
    }

    public void setFastMenuIds(String fastMenuIds) {
        this.fastMenuIds = fastMenuIds == null ? null : fastMenuIds.trim();
    }
}
