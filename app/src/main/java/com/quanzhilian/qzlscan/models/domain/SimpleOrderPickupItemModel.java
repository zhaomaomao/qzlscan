package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by yangtm on 2018/3/8.
 */

public class SimpleOrderPickupItemModel {
    private ProductScm productScm;
    private SimpleOrderPickupModel orderPickup;
    private Map map;




    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public SimpleOrderPickupModel getOrderPickup() {
        return orderPickup;
    }

    public void setOrderPickup(SimpleOrderPickupModel orderPickup) {
        this.orderPickup = orderPickup;
    }

    public ProductScm getProductScm() {
        return productScm;
    }

    public void setProductScm(ProductScm productScm) {
        this.productScm = productScm;
    }

    private Integer orderPickupItemId;

    private Integer orderPickupId;

    private Integer productId;

    private Double quantity;

    private Integer quantityUnit;

    private BigDecimal price;

    private Double amount;

    private Double ton;

    private Double ling;

    private BigDecimal totalPrice;

    private Integer priceUnit;

    private String note;

    private Integer scmId;

    private String productName2;

    private Integer extraFeeType;

    private Double extraFeeValue;

    private BigDecimal extraFee;

    private Boolean isCountFreight;

    private Boolean isCountInvoice;

    private Integer oldRepositoryId;

    private Integer oldProductId;

    private String oldProductName;

    private String oldFactoryName;

    private String oldBrandName;

    private Double oldGramWeight;

    private String oldSpecification;

    public Integer getOrderPickupItemId() {
        return orderPickupItemId;
    }

    public void setOrderPickupItemId(Integer orderPickupItemId) {
        this.orderPickupItemId = orderPickupItemId;
    }

    public Integer getOrderPickupId() {
        return orderPickupId;
    }

    public void setOrderPickupId(Integer orderPickupId) {
        this.orderPickupId = orderPickupId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(Integer quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTon() {
        return ton;
    }

    public void setTon(Double ton) {
        this.ton = ton;
    }

    public Double getLing() {
        return ling;
    }

    public void setLing(Double ling) {
        this.ling = ling;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getScmId() {
        return scmId;
    }

    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2 == null ? null : productName2.trim();
    }

    public Integer getExtraFeeType() {
        return extraFeeType;
    }

    public void setExtraFeeType(Integer extraFeeType) {
        this.extraFeeType = extraFeeType;
    }

    public Double getExtraFeeValue() {
        return extraFeeValue;
    }

    public void setExtraFeeValue(Double extraFeeValue) {
        this.extraFeeValue = extraFeeValue;
    }

    public BigDecimal getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(BigDecimal extraFee) {
        this.extraFee = extraFee;
    }

    public Boolean getIsCountFreight() {
        return isCountFreight;
    }

    public void setIsCountFreight(Boolean isCountFreight) {
        this.isCountFreight = isCountFreight;
    }

    public Boolean getIsCountInvoice() {
        return isCountInvoice;
    }

    public void setIsCountInvoice(Boolean isCountInvoice) {
        this.isCountInvoice = isCountInvoice;
    }

    public Integer getOldRepositoryId() {
        return oldRepositoryId;
    }

    public void setOldRepositoryId(Integer oldRepositoryId) {
        this.oldRepositoryId = oldRepositoryId;
    }

    public Integer getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Integer oldProductId) {
        this.oldProductId = oldProductId;
    }

    public String getOldProductName() {
        return oldProductName;
    }

    public void setOldProductName(String oldProductName) {
        this.oldProductName = oldProductName == null ? null : oldProductName.trim();
    }

    public String getOldFactoryName() {
        return oldFactoryName;
    }

    public void setOldFactoryName(String oldFactoryName) {
        this.oldFactoryName = oldFactoryName == null ? null : oldFactoryName.trim();
    }

    public String getOldBrandName() {
        return oldBrandName;
    }

    public void setOldBrandName(String oldBrandName) {
        this.oldBrandName = oldBrandName == null ? null : oldBrandName.trim();
    }

    public Double getOldGramWeight() {
        return oldGramWeight;
    }

    public void setOldGramWeight(Double oldGramWeight) {
        this.oldGramWeight = oldGramWeight;
    }

    public String getOldSpecification() {
        return oldSpecification;
    }

    public void setOldSpecification(String oldSpecification) {
        this.oldSpecification = oldSpecification == null ? null : oldSpecification.trim();
    }
}
