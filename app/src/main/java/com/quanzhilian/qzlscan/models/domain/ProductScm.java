package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yangtm on 2018/2/6.
 */

public class ProductScm {
    public ProductScm() {

    }

    public Double getPWidth() {
        return pWidth;
    }

    public void setPWidth(Double pWidth) {
        this.pWidth = pWidth;
    }

    public Double getPLength() {
        return pLength;
    }

    public void setPLength(Double pLength) {
        this.pLength = pLength;
    }

    private String productSimpleCodeJSON;

    private String[] productImages;

    public String[] getProductImages() {
        return productImages;
    }

    public void setProductImages(String[] productImages) {
        this.productImages = productImages;
    }


    public String getProductSimpleCodeJSON() {
        return productSimpleCodeJSON;
    }

    public void setProductSimpleCodeJSON(String productSimpleCodeJSON) {
        this.productSimpleCodeJSON = productSimpleCodeJSON;
    }

    private Integer productId;

    private Date createDate;

    private Date updateDate;

    private String productName;

    private Date lastUpdatePriceDate;

    private Integer createBy;

    private Integer scmId;

    private Integer factoryId;

    private String factoryName;

    private Integer paperKindId;

    private String paperKindName;

    private Integer brandId;

    private String brandName;

    private String productSku;

    private String productSimpleCode;

    private String productBrief;

    private String specification;

    private String productImage;

    private String productImagePath;

    private Double amountWeight;

    private Double gramWeight;

    private String machine;

    private String grade;

    private Integer unit;

    private BigDecimal originalPrice;

    private BigDecimal mallPrice;

    private Integer doorWeight;

    private Double pWidth;

    private Double pLength;

    private BigDecimal price;

    private BigDecimal cost;

    private BigDecimal lingAddPrice;

    private BigDecimal zhangAddPrice;

    private Double salesAmount;

    private Integer supplyState;

    private Double pieceLing;

    private Double lingAmount;

    private Double productNumber;

    private String availableQuantity;

    private Boolean isShowQuantity;

    private Integer source;

    private Integer juanPing;

    private Integer mainType;

    private Integer mainMinType;

    private BigDecimal minOrderCount;

    private Boolean isPromotion;

    private Boolean isShow;

    private Boolean isDelete;

    private Integer sort;

    private BigDecimal costAdd;

    private String productName2;

    private RepositoryProduct repositoryProduct;

    private Integer isTon;//是否有库存

    public Integer getIsTon() {
        return isTon;
    }

    public void setIsTon(Integer isTon) {
        this.isTon = isTon;
    }

    public RepositoryProduct getRepositoryProduct() {
        return repositoryProduct;
    }

    public void setRepositoryProduct(RepositoryProduct repositoryProduct) {
        this.repositoryProduct = repositoryProduct;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Date getLastUpdatePriceDate() {
        return lastUpdatePriceDate;
    }

    public void setLastUpdatePriceDate(Date lastUpdatePriceDate) {
        this.lastUpdatePriceDate = lastUpdatePriceDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getScmId() {
        return scmId;
    }

    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    public Integer getPaperKindId() {
        return paperKindId;
    }

    public void setPaperKindId(Integer paperKindId) {
        this.paperKindId = paperKindId;
    }

    public String getPaperKindName() {
        return paperKindName;
    }

    public void setPaperKindName(String paperKindName) {
        this.paperKindName = paperKindName == null ? null : paperKindName.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku == null ? null : productSku.trim();
    }

    public String getProductSimpleCode() {
        return productSimpleCode;
    }

    public void setProductSimpleCode(String productSimpleCode) {
        this.productSimpleCode = productSimpleCode == null ? null : productSimpleCode.trim();
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief == null ? null : productBrief.trim();
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath == null ? null : productImagePath.trim();
    }

    public Double getAmountWeight() {
        return amountWeight;
    }

    public void setAmountWeight(Double amountWeight) {
        this.amountWeight = amountWeight;
    }

    public Double getGramWeight() {
        return gramWeight;
    }

    public void setGramWeight(Double gramWeight) {
        this.gramWeight = gramWeight;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine == null ? null : machine.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getMallPrice() {
        return mallPrice;
    }

    public void setMallPrice(BigDecimal mallPrice) {
        this.mallPrice = mallPrice;
    }

    public Integer getDoorWeight() {
        return doorWeight;
    }

    public void setDoorWeight(Integer doorWeight) {
        this.doorWeight = doorWeight;
    }

    public Double getpWidth() {
        return pWidth;
    }

    public void setpWidth(Double pWidth) {
        this.pWidth = pWidth;
    }

    public Double getpLength() {
        return pLength;
    }

    public void setpLength(Double pLength) {
        this.pLength = pLength;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getLingAddPrice() {
        return lingAddPrice;
    }

    public void setLingAddPrice(BigDecimal lingAddPrice) {
        this.lingAddPrice = lingAddPrice;
    }

    public BigDecimal getZhangAddPrice() {
        return zhangAddPrice;
    }

    public void setZhangAddPrice(BigDecimal zhangAddPrice) {
        this.zhangAddPrice = zhangAddPrice;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getSupplyState() {
        return supplyState;
    }

    public void setSupplyState(Integer supplyState) {
        this.supplyState = supplyState;
    }

    public Double getPieceLing() {
        return pieceLing;
    }

    public void setPieceLing(Double pieceLing) {
        this.pieceLing = pieceLing;
    }

    public Double getLingAmount() {
        return lingAmount;
    }

    public void setLingAmount(Double lingAmount) {
        this.lingAmount = lingAmount;
    }

    public Double getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Double productNumber) {
        this.productNumber = productNumber;
    }

    public String getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(String availableQuantity) {
        this.availableQuantity = availableQuantity == null ? null : availableQuantity.trim();
    }

    public Boolean getIsShowQuantity() {
        return isShowQuantity;
    }

    public void setIsShowQuantity(Boolean isShowQuantity) {
        this.isShowQuantity = isShowQuantity;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getJuanPing() {
        return juanPing;
    }

    public void setJuanPing(Integer juanPing) {
        this.juanPing = juanPing;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public Integer getMainMinType() {
        return mainMinType;
    }

    public void setMainMinType(Integer mainMinType) {
        this.mainMinType = mainMinType;
    }

    public BigDecimal getMinOrderCount() {
        return minOrderCount;
    }

    public void setMinOrderCount(BigDecimal minOrderCount) {
        this.minOrderCount = minOrderCount;
    }

    public Boolean getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(Boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public BigDecimal getCostAdd() {
        return costAdd;
    }

    public void setCostAdd(BigDecimal costAdd) {
        this.costAdd = costAdd;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2 == null ? null : productName2.trim();
    }
}
