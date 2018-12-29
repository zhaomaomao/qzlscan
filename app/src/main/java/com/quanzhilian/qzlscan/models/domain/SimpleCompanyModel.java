package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangtm on 2018/1/11.
 */

public class SimpleCompanyModel {

    private String companyDesc;

    private String contractTerms;

    private String purchaseTerms;


    private Integer companyId;

    private Date createDate;

    private Date updateDate;

    private Integer companyType;

    private String companyName;

    private Integer memberId;

    private Integer sort;

    private String companyBrief;

    private String companyAlias;

    private String logo;

    private String logoPath;

    private String city;

    private Integer cityId;

    private String province;

    private Integer provinceId;

    private String address;

    private String url;

    private String postcode;

    private String fax;

    private String qq;

    private String phone;

    private String linkman;

    private String businessArea;

    private String businessBrand;

    private String businessFactory;

    private String businessCatatory;

    private String synopsis;

    private String domainName;

    private Boolean isDelete;

    private String legal;

    private Integer source;

    private String companySealPath;

    private String companySealName;

    private Integer defaultSalesTemplate;

    private Integer defaultPurchaseTemplate;

    private String zhifubaoImg;

    private String zhifubaoImgPath;

    private String bankAccount;

    private String bankName;

    private String branchName;

    private BigDecimal slittingFee;

    private Integer ton;

    private Integer slittingFeeMax;

    private Integer slittingFeeMin;

    private Integer level;

    public String getCompanyAlias() {
        return companyAlias;
    }

    public void setCompanyAlias(String companyAlias) {
        this.companyAlias = companyAlias;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getSlittingFeeMax() {
        return slittingFeeMax;
    }

    public void setSlittingFeeMax(Integer slittingFeeMax) {
        this.slittingFeeMax = slittingFeeMax;
    }

    public Integer getSlittingFeeMin() {
        return slittingFeeMin;
    }

    public void setSlittingFeeMin(Integer slittingFeeMin) {
        this.slittingFeeMin = slittingFeeMin;
    }

    public BigDecimal getSlittingFee() {
        return slittingFee;
    }

    public void setSlittingFee(BigDecimal slittingFee) {
        this.slittingFee = slittingFee;
    }

    public Integer getTon() {
        return ton;
    }

    public void setTon(Integer ton) {
        this.ton = ton;
    }

    public String getZhifubaoImg() {
        return zhifubaoImg;
    }

    public void setZhifubaoImg(String zhifubaoImg) {
        this.zhifubaoImg = zhifubaoImg;
    }

    public String getZhifubaoImgPath() {
        return zhifubaoImgPath;
    }

    public void setZhifubaoImgPath(String zhifubaoImgPath) {
        this.zhifubaoImgPath = zhifubaoImgPath;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Integer getDefaultSalesTemplate() {
        return defaultSalesTemplate;
    }

    public void setDefaultSalesTemplate(Integer defaultSalesTemplate) {
        this.defaultSalesTemplate = defaultSalesTemplate;
    }

    public Integer getDefaultPurchaseTemplate() {
        return defaultPurchaseTemplate;
    }

    public void setDefaultPurchaseTemplate(Integer defaultPurchaseTemplate) {
        this.defaultPurchaseTemplate = defaultPurchaseTemplate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCompanyBrief() {
        return companyBrief;
    }

    public void setCompanyBrief(String companyBrief) {
        this.companyBrief = companyBrief == null ? null : companyBrief.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath == null ? null : logoPath.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea == null ? null : businessArea.trim();
    }

    public String getBusinessBrand() {
        return businessBrand;
    }

    public void setBusinessBrand(String businessBrand) {
        this.businessBrand = businessBrand == null ? null : businessBrand.trim();
    }

    public String getBusinessFactory() {
        return businessFactory;
    }

    public void setBusinessFactory(String businessFactory) {
        this.businessFactory = businessFactory == null ? null : businessFactory.trim();
    }

    public String getBusinessCatatory() {
        return businessCatatory;
    }

    public void setBusinessCatatory(String businessCatatory) {
        this.businessCatatory = businessCatatory == null ? null : businessCatatory.trim();
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis == null ? null : synopsis.trim();
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName == null ? null : domainName.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal == null ? null : legal.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getCompanySealPath() {
        return companySealPath;
    }

    public void setCompanySealPath(String companySealPath) {
        this.companySealPath = companySealPath;
    }

    public String getCompanySealName() {
        return companySealName;
    }

    public void setCompanySealName(String companySealName) {
        this.companySealName = companySealName;
    }

    public String getContractTerms() {
        return contractTerms;
    }

    public void setContractTerms(String contractTerms) {
        this.contractTerms = contractTerms;
    }

    public String getPurchaseTerms() {
        return purchaseTerms;
    }

    public void setPurchaseTerms(String purchaseTerms) {
        this.purchaseTerms = purchaseTerms;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }
}
