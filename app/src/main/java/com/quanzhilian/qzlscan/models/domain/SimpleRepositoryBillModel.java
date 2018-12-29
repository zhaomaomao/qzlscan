package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出入库单据
 */

public class SimpleRepositoryBillModel {
    private List<SimpleRepositoryBillItemModel> repositoryBillItemList;
    private List<SimpleRepositoryBillItemDetailModel> repositoryBillItemDetailList;


    public List<SimpleRepositoryBillItemDetailModel> getRepositoryBillItemDetailList() {
        return repositoryBillItemDetailList;
    }

    public void setRepositoryBillItemDetailList(List<SimpleRepositoryBillItemDetailModel> repositoryBillItemDetailList) {
        this.repositoryBillItemDetailList = repositoryBillItemDetailList;
    }

    public List<SimpleRepositoryBillItemModel> getRepositoryBillItemList() {
        return repositoryBillItemList;
    }

    public void setRepositoryBillItemList(
            List<SimpleRepositoryBillItemModel> repositoryBillItemList) {
        this.repositoryBillItemList = repositoryBillItemList;
    }

    /**
     * 出入库单ID
     **/
    private Integer repositoryBillId;
    /**
     * 仓库ID
     **/
    private Integer repositoryId;
    /****/
    private String repositoryName;
    /****/
    private Integer createBy;
    /****/
    private Date createDate;
    /**
     * 最后修改时间
     **/
    private Date updateDate;
    /****/
    private Date auditDate;
    /****/
    private Integer auditBy;
    /****/
    private Integer scmId;
    /**
     * 1入库单2出库单
     **/
    private Integer type;
    /**
     * 备注信息
     **/
    private String memo;
    /**
     * 来源id，比如出库单的id
     **/
    private Integer sourceId;
    /**
     * 来源
     **/
    private Integer source;
    /**
     * 状态1待审核2审核通过
     **/
    private Integer state;
    /**
     * 操作人
     **/
    private String operator;
    /**
     * 操作时间
     **/
    private Date operateDate;
    /**
     * 审核备注信息
     **/
    private String auditMemo;
    /**
     * 如果是订单，则来源订单的id
     **/
    private Integer orderId;
    /**
     * 总金额
     **/
    private BigDecimal tradePrice;
    /****/
    private Double ton;
    /****/
    private Double ling;
    /****/
    private Double amount;
    /****/
    private Date expectedRepaymentDate;
    /****/
    private Date expectedFrozenDate;
    /**
     * 对应的会员ID,即出入库单出库到MEMBERID和来源是哪个member——Id
     **/
    private Integer relativeMemberId;
    /**
     * 取消人，做数据记录
     **/
    private Integer cancelBy;
    /**
     * 是否取消，只有未审核通过的单子才能够进行作废
     **/
    private Boolean isCancel;
    /**
     * 来源信息说明
     **/
    private String sourceInfo;
    /**
     * 项目信息说明
     **/
    private String itemInfo;
    /****/
    private Integer logisticsId;
    /**
     * 物流公司
     **/
    private String logisticsName;
    /**
     * 物流车号
     **/
    private String logisticsCarNo;
    /**
     * 随车电话
     **/
    private String logisticsCarPhone;
    /**
     * 物流运输备注
     **/
    private String logisticsMemo;
    /**
     * 运输地址
     **/
    private String address;
    /**
     * 物流公司每吨的运费
     **/
    private BigDecimal logisticsTransportFee;
    /**
     * 出入库单编号
     **/
    private String repositoryBillNo;
    /**
     * 客户ID或采购ID
     **/
    private Integer relativeId;
    /**
     * 对方企业名称
     **/
    private String relativeCompanyName;
    /**
     * 本公司抬头名称
     **/
    private String myCompanyName;
    /**
     * 负责的销售员ID
     **/
    private Integer salesmanId;
    /**
     * 负责的销售员名称
     **/
    private String salesman;

    /**
     * 设置：出入库单ID
     */
    public void setRepositoryBillId(Integer repositoryBillId) {
        this.repositoryBillId = repositoryBillId;
    }

    /**
     * 获取：出入库单ID
     */
    public Integer getRepositoryBillId() {
        return repositoryBillId;
    }

    /**
     * 设置：仓库ID
     */
    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }

    /**
     * 获取：仓库ID
     */
    public Integer getRepositoryId() {
        return repositoryId;
    }

    /**
     * 设置：
     */
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    /**
     * 获取：
     */
    public String getRepositoryName() {
        return repositoryName;
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
     * 设置：最后修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取：最后修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置：
     */
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    /**
     * 获取：
     */
    public Date getAuditDate() {
        return auditDate;
    }

    /**
     * 设置：
     */
    public void setAuditBy(Integer auditBy) {
        this.auditBy = auditBy;
    }

    /**
     * 获取：
     */
    public Integer getAuditBy() {
        return auditBy;
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
     * 设置：1入库单2出库单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取：1入库单2出库单
     */
    public Integer getType() {
        return type;
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
     * 设置：来源id，比如出库单的id
     */
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取：来源id，比如出库单的id
     */
    public Integer getSourceId() {
        return sourceId;
    }

    /**
     * 设置：来源
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 获取：来源
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置：状态1待审核2审核通过
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取：状态1待审核2审核通过
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置：操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取：操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置：操作时间
     */
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    /**
     * 获取：操作时间
     */
    public Date getOperateDate() {
        return operateDate;
    }

    /**
     * 设置：审核备注信息
     */
    public void setAuditMemo(String auditMemo) {
        this.auditMemo = auditMemo;
    }

    /**
     * 获取：审核备注信息
     */
    public String getAuditMemo() {
        return auditMemo;
    }

    /**
     * 设置：如果是订单，则来源订单的id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取：如果是订单，则来源订单的id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置：总金额
     */
    public void setTradePrice(BigDecimal tradePrice) {
        this.tradePrice = tradePrice;
    }

    /**
     * 获取：总金额
     */
    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    /**
     * 设置：
     */
    public void setTon(Double ton) {
        this.ton = ton;
    }

    /**
     * 获取：
     */
    public Double getTon() {
        return ton;
    }

    /**
     * 设置：
     */
    public void setLing(Double ling) {
        this.ling = ling;
    }

    /**
     * 获取：
     */
    public Double getLing() {
        return ling;
    }

    /**
     * 设置：
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取：
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置：
     */
    public void setExpectedRepaymentDate(Date expectedRepaymentDate) {
        this.expectedRepaymentDate = expectedRepaymentDate;
    }

    /**
     * 获取：
     */
    public Date getExpectedRepaymentDate() {
        return expectedRepaymentDate;
    }

    /**
     * 设置：
     */
    public void setExpectedFrozenDate(Date expectedFrozenDate) {
        this.expectedFrozenDate = expectedFrozenDate;
    }

    /**
     * 获取：
     */
    public Date getExpectedFrozenDate() {
        return expectedFrozenDate;
    }

    /**
     * 设置：对应的会员ID,即出入库单出库到MEMBERID和来源是哪个member——Id
     */
    public void setRelativeMemberId(Integer relativeMemberId) {
        this.relativeMemberId = relativeMemberId;
    }

    /**
     * 获取：对应的会员ID,即出入库单出库到MEMBERID和来源是哪个member——Id
     */
    public Integer getRelativeMemberId() {
        return relativeMemberId;
    }

    /**
     * 设置：取消人，做数据记录
     */
    public void setCancelBy(Integer cancelBy) {
        this.cancelBy = cancelBy;
    }

    /**
     * 获取：取消人，做数据记录
     */
    public Integer getCancelBy() {
        return cancelBy;
    }

    /**
     * 设置：是否取消，只有未审核通过的单子才能够进行作废
     */
    public void setIsCancel(Boolean isCancel) {
        this.isCancel = isCancel;
    }

    /**
     * 获取：是否取消，只有未审核通过的单子才能够进行作废
     */
    public Boolean getIsCancel() {
        return isCancel;
    }

    /**
     * 设置：来源信息说明
     */
    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    /**
     * 获取：来源信息说明
     */
    public String getSourceInfo() {
        return sourceInfo;
    }

    /**
     * 设置：项目信息说明
     */
    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    /**
     * 获取：项目信息说明
     */
    public String getItemInfo() {
        return itemInfo;
    }

    /**
     * 设置：
     */
    public void setLogisticsId(Integer logisticsId) {
        this.logisticsId = logisticsId;
    }

    /**
     * 获取：
     */
    public Integer getLogisticsId() {
        return logisticsId;
    }

    /**
     * 设置：物流公司
     */
    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    /**
     * 获取：物流公司
     */
    public String getLogisticsName() {
        return logisticsName;
    }

    /**
     * 设置：物流车号
     */
    public void setLogisticsCarNo(String logisticsCarNo) {
        this.logisticsCarNo = logisticsCarNo;
    }

    /**
     * 获取：物流车号
     */
    public String getLogisticsCarNo() {
        return logisticsCarNo;
    }

    /**
     * 设置：随车电话
     */
    public void setLogisticsCarPhone(String logisticsCarPhone) {
        this.logisticsCarPhone = logisticsCarPhone;
    }

    /**
     * 获取：随车电话
     */
    public String getLogisticsCarPhone() {
        return logisticsCarPhone;
    }

    /**
     * 设置：物流运输备注
     */
    public void setLogisticsMemo(String logisticsMemo) {
        this.logisticsMemo = logisticsMemo;
    }

    /**
     * 获取：物流运输备注
     */
    public String getLogisticsMemo() {
        return logisticsMemo;
    }

    /**
     * 设置：运输地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取：运输地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置：物流公司每吨的运费
     */
    public void setLogisticsTransportFee(BigDecimal logisticsTransportFee) {
        this.logisticsTransportFee = logisticsTransportFee;
    }

    /**
     * 获取：物流公司每吨的运费
     */
    public BigDecimal getLogisticsTransportFee() {
        return logisticsTransportFee;
    }

    /**
     * 设置：出入库单编号
     */
    public void setRepositoryBillNo(String repositoryBillNo) {
        this.repositoryBillNo = repositoryBillNo;
    }

    /**
     * 获取：出入库单编号
     */
    public String getRepositoryBillNo() {
        return repositoryBillNo;
    }

    /**
     * 设置：客户ID或采购ID
     */
    public void setRelativeId(Integer relativeId) {
        this.relativeId = relativeId;
    }

    /**
     * 获取：客户ID或采购ID
     */
    public Integer getRelativeId() {
        return relativeId;
    }

    /**
     * 设置：对方企业名称
     */
    public void setRelativeCompanyName(String relativeCompanyName) {
        this.relativeCompanyName = relativeCompanyName;
    }

    /**
     * 获取：对方企业名称
     */
    public String getRelativeCompanyName() {
        return relativeCompanyName;
    }

    /**
     * 设置：本公司抬头名称
     */
    public void setMyCompanyName(String myCompanyName) {
        this.myCompanyName = myCompanyName;
    }

    /**
     * 获取：本公司抬头名称
     */
    public String getMyCompanyName() {
        return myCompanyName;
    }

    /**
     * 设置：负责的销售员ID
     */
    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    /**
     * 获取：负责的销售员ID
     */
    public Integer getSalesmanId() {
        return salesmanId;
    }

    /**
     * 设置：负责的销售员名称
     */
    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    /**
     * 获取：负责的销售员名称
     */
    public String getSalesman() {
        return salesman;
    }
}
