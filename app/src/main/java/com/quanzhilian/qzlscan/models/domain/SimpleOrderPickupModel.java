package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangtm on 2018/3/8.
 */

public class SimpleOrderPickupModel {
    private List<SimpleOrderPickupItemModel> orderPickupItemList;

    public List<SimpleOrderPickupItemModel> getOrderPickupItemList() {
        return orderPickupItemList;
    }

    public void setOrderPickupItemList(List<SimpleOrderPickupItemModel> orderPickupItemList) {
        this.orderPickupItemList = orderPickupItemList;
    }

    private Integer orderPickupId;

    private Integer orderId;

    private String orderNo;

    private Integer customerId;

    private String customerName;

    private String pickupType;

    private Integer repositoryId;

    private String repositoryName;

    private Integer createBy;

    private Date createDate;

    private Integer scmId;

    private String memo;

    private Integer type;

    private Integer state;

    private Integer logisticsId;

    private String logisticsName;

    private String logisticsCarNo;

    private String logisticsCarPhone;

    private String logisticsMemo;

    private Double ton;

    private Double ling;

    private Double amount;

    private BigDecimal totalPrice;

    private String sourceInfo;

    private String itemInfo;

    private Integer orderState;

    private BigDecimal logisticsTransportFee;

    private String orderPickupNo;

    private Boolean isLockRepos;

    private Integer purchaseOrderId;

    private String purchaseOrderNo;

    private String address;

    private Integer printCount;

    private Integer isRecovery;
    /**分切单ID**/
    private Integer repositoryCuttingId;
    /**
     * 设置：分切单ID
     */
    public void setRepositoryCuttingId(Integer repositoryCuttingId) {
        this.repositoryCuttingId = repositoryCuttingId;
    }
    /**
     * 获取：分切单ID
     */
    public Integer getRepositoryCuttingId() {
        return repositoryCuttingId;
    }

    public Integer getIsRecovery() {
        return isRecovery;
    }

    public void setIsRecovery(Integer isRecovery) {
        this.isRecovery = isRecovery;
    }

    public Integer getOrderPickupId() {
        return orderPickupId;
    }

    public void setOrderPickupId(Integer orderPickupId) {
        this.orderPickupId = orderPickupId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType == null ? null : pickupType.trim();
    }

    public Integer getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName == null ? null : repositoryName.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName == null ? null : logisticsName.trim();
    }

    public String getLogisticsCarNo() {
        return logisticsCarNo;
    }

    public void setLogisticsCarNo(String logisticsCarNo) {
        this.logisticsCarNo = logisticsCarNo == null ? null : logisticsCarNo.trim();
    }

    public String getLogisticsCarPhone() {
        return logisticsCarPhone;
    }

    public void setLogisticsCarPhone(String logisticsCarPhone) {
        this.logisticsCarPhone = logisticsCarPhone == null ? null : logisticsCarPhone.trim();
    }

    public String getLogisticsMemo() {
        return logisticsMemo;
    }

    public void setLogisticsMemo(String logisticsMemo) {
        this.logisticsMemo = logisticsMemo == null ? null : logisticsMemo.trim();
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo == null ? null : sourceInfo.trim();
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo == null ? null : itemInfo.trim();
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public BigDecimal getLogisticsTransportFee() {
        return logisticsTransportFee;
    }

    public void setLogisticsTransportFee(BigDecimal logisticsTransportFee) {
        this.logisticsTransportFee = logisticsTransportFee;
    }

    public String getOrderPickupNo() {
        return orderPickupNo;
    }

    public void setOrderPickupNo(String orderPickupNo) {
        this.orderPickupNo = orderPickupNo == null ? null : orderPickupNo.trim();
    }

    public Boolean getIsLockRepos() {
        return isLockRepos;
    }

    public void setIsLockRepos(Boolean isLockRepos) {
        this.isLockRepos = isLockRepos;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Integer purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }
}
