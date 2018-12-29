package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;

/**
 */

public class SimpleRepositoryBillItemDetailModel {
    private Integer rowNum;

    private Integer state;//扫描状态

    public Boolean getIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean full) {
        isFull = full;
    }

    private Boolean isFull;//是整件出

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    private SimpleRepositoryPositionModel repositoryPosition;

    //private ProductScm productScm;
    public SimpleRepositoryPositionModel getRepositoryPosition() {
        return repositoryPosition;
    }

    public void setRepositoryPosition(SimpleRepositoryPositionModel repositoryPosition) {
        this.repositoryPosition = repositoryPosition;
    }

//    public ProductScm getProductScm() {
//        return productScm;
//    }
//
//    public void setProductScm(ProductScm productScm) {
//        this.productScm = productScm;
//    }

    /****/
    private Integer repositoryBillItemDetailId;
    /****/
    private Integer scmId;
    /****/
    private Integer repositoryBillId;
    /****/
    private Integer repositoryBillItemId;
    /**
     * 件重
     **/
    private Double amountWeight;
    /**
     * 创建时间
     **/
    private Date createDate;
    /**
     * 产品ID
     **/
    private Integer productId;
    /**
     * 入库库位ID，默认0没有库位
     **/
    private Integer repositoryPositionId;
    /**
     * 入库备注信息
     **/
    private String remark;
    /**
     * 出库单对应的仓库产品ID
     **/
    private Integer repositoryProductId;
    /**
     * 条形码
     **/
    private String barCode;
    /**
     * 厂家条码号
     **/
    private String barCodeFactory;
    /**
     * 件令数
     **/
    private Double amountLing;
    /**
     * 原件重，当前件重可能和原件重不符
     **/
    private Double oldAmountWeight;

    /**
     * 设置：
     */
    public void setRepositoryBillItemDetailId(Integer repositoryBillItemDetailId) {
        this.repositoryBillItemDetailId = repositoryBillItemDetailId;
    }

    /**
     * 获取：
     */
    public Integer getRepositoryBillItemDetailId() {
        return repositoryBillItemDetailId;
    }

    /**
     * 设置：
     */
    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    /**
     * 获取：
     */
    public Integer getScmId() {
        return scmId;
    }

    /**
     * 设置：
     */
    public void setRepositoryBillId(Integer repositoryBillId) {
        this.repositoryBillId = repositoryBillId;
    }

    /**
     * 获取：
     */
    public Integer getRepositoryBillId() {
        return repositoryBillId;
    }

    /**
     * 设置：
     */
    public void setRepositoryBillItemId(Integer repositoryBillItemId) {
        this.repositoryBillItemId = repositoryBillItemId;
    }

    /**
     * 获取：
     */
    public Integer getRepositoryBillItemId() {
        return repositoryBillItemId;
    }

    /**
     * 设置：件重
     */
    public void setAmountWeight(Double amountWeight) {
        this.amountWeight = amountWeight;
    }

    /**
     * 获取：件重
     */
    public Double getAmountWeight() {
        return amountWeight;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置：产品ID
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取：产品ID
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置：入库库位ID，默认0没有库位
     */
    public void setRepositoryPositionId(Integer repositoryPositionId) {
        this.repositoryPositionId = repositoryPositionId;
    }

    /**
     * 获取：入库库位ID，默认0没有库位
     */
    public Integer getRepositoryPositionId() {
        return repositoryPositionId;
    }

    /**
     * 设置：入库备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：入库备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置：出库单对应的仓库产品ID
     */
    public void setRepositoryProductId(Integer repositoryProductId) {
        this.repositoryProductId = repositoryProductId;
    }

    /**
     * 获取：出库单对应的仓库产品ID
     */
    public Integer getRepositoryProductId() {
        return repositoryProductId;
    }

    /**
     * 设置：条形码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 获取：条形码
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * 设置：厂家条码号
     */
    public void setBarCodeFactory(String barCodeFactory) {
        this.barCodeFactory = barCodeFactory;
    }

    /**
     * 获取：厂家条码号
     */
    public String getBarCodeFactory() {
        return barCodeFactory;
    }

    /**
     * 设置：件令数
     */
    public void setAmountLing(Double amountLing) {
        this.amountLing = amountLing;
    }

    /**
     * 获取：件令数
     */
    public Double getAmountLing() {
        return amountLing;
    }

    /**
     * 设置：原件重，当前件重可能和原件重不符
     */
    public void setOldAmountWeight(Double oldAmountWeight) {
        this.oldAmountWeight = oldAmountWeight;
    }

    /**
     * 获取：原件重，当前件重可能和原件重不符
     */
    public Double getOldAmountWeight() {
        return oldAmountWeight;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
