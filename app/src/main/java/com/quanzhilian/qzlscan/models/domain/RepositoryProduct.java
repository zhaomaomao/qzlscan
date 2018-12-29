package com.quanzhilian.qzlscan.models.domain;

import java.util.Date;

/**
 * Created by yangtm on 2018/2/6.
 */

public class RepositoryProduct {
    private ProductScm productScm;
    private Double quantity;
    private Double zhang;
    private Integer quantityUnit;


    public Double getZhang() {
        return zhang;
    }

    public void setZhang(Double zhang) {
        this.zhang = zhang;
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

    public ProductScm getProductScm() {
        return productScm;
    }

    public void setProductScm(ProductScm productScm) {
        this.productScm = productScm;
    }

    /****/
    private Integer repositoryProductId;
    /**
     * 仓库的id
     **/
    private Integer repositoryId;
    /****/
    private Integer productId;
    /**
     * 系统ID
     **/
    private Integer scmId;
    /****/
    private Integer createBy;
    /****/
    private Date createDate;
    /****/
    private Date udpateDate;
    /**
     * 令数
     **/
    private Double ling;
    /**
     * 件数
     **/
    private Double amount;
    /**
     * 吨数
     **/
    private Double ton;
    /**
     * 冻结吨数
     **/
    private Double frozenTon;
    /**
     * 备注信息
     **/
    private String memo;
    /**
     * 是否有明细项
     **/
    private Boolean hasDetail;
    /**
     * 库位ID，默认0无库位
     **/
    private Integer repositoryPositionId;
    /**
     * 父级ID
     **/
    private Integer parentId;
    /**
     * 条形码
     **/
    private String barCode;
    /**
     * 厂家条码号
     **/
    private String barCodeFactory;
    /**
     * 原始件重
     **/
    private Double oldAmountWeight;
    /**
     * 是否在被使用
     **/
    private Boolean inUse;
    /**
     * 入库产品的件令数
     **/
    private Double oldAmountLing;
    /**
     * 库存明细占用信息
     **/
    private String inUseInfo;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getRepositoryBillId() {
        return repositoryBillId;
    }

    public void setRepositoryBillId(Integer repositoryBillId) {
        this.repositoryBillId = repositoryBillId;
    }

    private Integer repositoryBillId;

    /**
     * 设置：
     */
    public void setRepositoryProductId(Integer repositoryProductId) {
        this.repositoryProductId = repositoryProductId;
    }

    /**
     * 获取：
     */
    public Integer getRepositoryProductId() {
        return repositoryProductId;
    }

    /**
     * 设置：仓库的id
     */
    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * 获取：仓库的id
     */
    public Integer getRepositoryId() {
        return repositoryId;
    }

    /**
     * 设置：
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取：
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置：系统ID
     */
    public void setScmId(Integer scmId) {
        this.scmId = scmId;
    }

    /**
     * 获取：系统ID
     */
    public Integer getScmId() {
        return scmId;
    }

    /**
     * 设置：
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置：
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取：
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置：
     */
    public void setUdpateDate(Date udpateDate) {
        this.udpateDate = udpateDate;
    }

    /**
     * 获取：
     */
    public Date getUdpateDate() {
        return udpateDate;
    }

    /**
     * 设置：令数
     */
    public void setLing(Double ling) {
        this.ling = ling;
    }

    /**
     * 获取：令数
     */
    public Double getLing() {
        return ling;
    }

    /**
     * 设置：件数
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取：件数
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置：吨数
     */
    public void setTon(Double ton) {
        this.ton = ton;
    }

    /**
     * 获取：吨数
     */
    public Double getTon() {
        return ton;
    }

    /**
     * 设置：冻结吨数
     */
    public void setFrozenTon(Double frozenTon) {
        this.frozenTon = frozenTon;
    }

    /**
     * 获取：冻结吨数
     */
    public Double getFrozenTon() {
        return frozenTon;
    }

    /**
     * 设置：备注信息
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取：备注信息
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置：是否有明细项
     */
    public void setHasDetail(Boolean hasDetail) {
        this.hasDetail = hasDetail;
    }

    /**
     * 获取：是否有明细项
     */
    public Boolean getHasDetail() {
        return hasDetail;
    }

    /**
     * 设置：库位ID，默认0无库位
     */
    public void setRepositoryPositionId(Integer repositoryPositionId) {
        this.repositoryPositionId = repositoryPositionId;
    }

    /**
     * 获取：库位ID，默认0无库位
     */
    public Integer getRepositoryPositionId() {
        return repositoryPositionId;
    }

    /**
     * 设置：父级ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：父级ID
     */
    public Integer getParentId() {
        return parentId;
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
     * 设置：原始件重
     */
    public void setOldAmountWeight(Double oldAmountWeight) {
        this.oldAmountWeight = oldAmountWeight;
    }

    /**
     * 获取：原始件重
     */
    public Double getOldAmountWeight() {
        return oldAmountWeight;
    }

    /**
     * 设置：是否在被使用
     */
    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    /**
     * 获取：是否在被使用
     */
    public Boolean getInUse() {
        return inUse;
    }

    /**
     * 设置：入库产品的件令数
     */
    public void setOldAmountLing(Double oldAmountLing) {
        this.oldAmountLing = oldAmountLing;
    }

    /**
     * 获取：入库产品的件令数
     */
    public Double getOldAmountLing() {
        return oldAmountLing;
    }

    /**
     * 设置：库存明细占用信息
     */
    public void setInUseInfo(String inUseInfo) {
        this.inUseInfo = inUseInfo;
    }

    /**
     * 获取：库存明细占用信息
     */
    public String getInUseInfo() {
        return inUseInfo;
    }
}
