package com.quanzhilian.qzlscan.models.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SimpleRepositoryCutting implements Serializable {
	
	private List<SimpleRepositoryCuttingItem> repositoryCuttingItemList;
	private List<SimpleRepositoryCuttingDetail> repositoryCuttingDetailList;
	
	
	
    public List<SimpleRepositoryCuttingDetail> getRepositoryCuttingDetailList() {
		return repositoryCuttingDetailList;
	}

	public void setRepositoryCuttingDetailList(List<SimpleRepositoryCuttingDetail> repositoryCuttingDetailList) {
		this.repositoryCuttingDetailList = repositoryCuttingDetailList;
	}

	public List<SimpleRepositoryCuttingItem> getRepositoryCuttingItemList() {
		return repositoryCuttingItemList;
	}

	public void setRepositoryCuttingItemList(List<SimpleRepositoryCuttingItem> repositoryCuttingItemList) {
		this.repositoryCuttingItemList = repositoryCuttingItemList;
	}

	/**分切单ID**/
	private Integer repositoryCuttingId;
	/**仓库ID**/
	private Integer repositoryId;
	/****/
	private String repositoryName;
	/**仓库ID**/
	private Integer oldRepositoryId;
	/****/
	private String oldRepositoryName;
	/****/
	private Integer createBy;
	/****/
	private Date createDate;
	/****/
	private Integer scmId;
	/**如果是订单，则是来源订单的id**/
	private Integer orderId;
	/**是否锁货**/
	private Boolean isLockRepos;
	/**备注信息**/
	private String memo;
	/**状态1待审核2审核通过**/
	private Integer state;
	/****/
	private Double ton;
	/****/
	private Double oldTon;
	/**操作人**/
	private String operator;
	/**操作时间**/
	private Date operateDate;
	/****/
	private Date auditDate;
	/****/
	private Integer auditBy;
	/**审核备注信息**/
	private String auditMemo;
	/**来源信息说明**/
	private String sourceInfo;
	/**项目信息说明**/
	private String itemInfo;
	/**分切班组**/
	private String groupName;
	/**分切班组ID**/
	private String groupId;
	/**1:分切单2移库单3实发单4排单**/
	private Integer type;
	/****/
	private Integer logisticsId;
	/**物流公司**/
	private String logisticsName;
	/**随车电话**/
	private String logisticsCarPhone;
	/**物流运输备注**/
	private String logisticsMemo;
	/**物流运输费用**/
	private BigDecimal logisticsTransportFee;
	/**1分切单编号2移库单编号**/
	private String repositoryCuttingNo;
	/**物流公司车牌号**/
	private String logisticsCarNo;
	/**打印次数**/
	private Integer printCount;
	/**分切单父级ID**/
	private Integer parentId;
	/**完成率**/
	private Double tonDone;
	/**小类型默认1正常2排单**/
	private Integer minType;
	/**是否是父级，是父级则不进行分切转化**/
	private Boolean isParent;
	/**数量完成数**/
	private Double quantityDone;
	/**提货单ID**/
	private Integer orderPickupId;
	/**类型:1分切组2机台3叉车工4分切工5打包工**/
	private Integer groupId2;
	/**类型:1分切组2机台3叉车工4分切工5打包工**/
	private Integer groupId3;
	/**类型:1分切组2机台3叉车工4分切工5打包工**/
	private Integer groupId4;
	/**类型:1分切组2机台3叉车工4分切工5打包工**/
	private Integer groupId5;
	/**关联客户ID**/
	private Integer relativeId;
	/**关联客户名称**/
	private String relativeCompanyName;

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
	 * 设置：仓库ID
	 */
	public void setOldRepositoryId(Integer oldRepositoryId) {
		this.oldRepositoryId = oldRepositoryId;
	}
	/**
	 * 获取：仓库ID
	 */
	public Integer getOldRepositoryId() {
		return oldRepositoryId;
	}
	/**
	 * 设置：
	 */
	public void setOldRepositoryName(String oldRepositoryName) {
		this.oldRepositoryName = oldRepositoryName;
	}
	/**
	 * 获取：
	 */
	public String getOldRepositoryName() {
		return oldRepositoryName;
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
	 * 设置：如果是订单，则是来源订单的id
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	/**
	 * 获取：如果是订单，则是来源订单的id
	 */
	public Integer getOrderId() {
		return orderId;
	}
	/**
	 * 设置：是否锁货
	 */
	public void setIsLockRepos(Boolean isLockRepos) {
		this.isLockRepos = isLockRepos;
	}
	/**
	 * 获取：是否锁货
	 */
	public Boolean getIsLockRepos() {
		return isLockRepos;
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
	public void setOldTon(Double oldTon) {
		this.oldTon = oldTon;
	}
	/**
	 * 获取：
	 */
	public Double getOldTon() {
		return oldTon;
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
	 * 设置：分切班组
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 获取：分切班组
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * 设置：分切班组ID
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 获取：分切班组ID
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置：1:分切单2移库单3实发单4排单
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1:分切单2移库单3实发单4排单
	 */
	public Integer getType() {
		return type;
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
	 * 设置：物流运输费用
	 */
	public void setLogisticsTransportFee(BigDecimal logisticsTransportFee) {
		this.logisticsTransportFee = logisticsTransportFee;
	}
	/**
	 * 获取：物流运输费用
	 */
	public BigDecimal getLogisticsTransportFee() {
		return logisticsTransportFee;
	}
	/**
	 * 设置：1分切单编号2移库单编号
	 */
	public void setRepositoryCuttingNo(String repositoryCuttingNo) {
		this.repositoryCuttingNo = repositoryCuttingNo;
	}
	/**
	 * 获取：1分切单编号2移库单编号
	 */
	public String getRepositoryCuttingNo() {
		return repositoryCuttingNo;
	}
	/**
	 * 设置：物流公司车牌号
	 */
	public void setLogisticsCarNo(String logisticsCarNo) {
		this.logisticsCarNo = logisticsCarNo;
	}
	/**
	 * 获取：物流公司车牌号
	 */
	public String getLogisticsCarNo() {
		return logisticsCarNo;
	}
	/**
	 * 设置：打印次数
	 */
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}
	/**
	 * 获取：打印次数
	 */
	public Integer getPrintCount() {
		return printCount;
	}
	/**
	 * 设置：分切单父级ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：分切单父级ID
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：完成率
	 */
	public void setTonDone(Double tonDone) {
		this.tonDone = tonDone;
	}
	/**
	 * 获取：完成率
	 */
	public Double getTonDone() {
		return tonDone;
	}
	/**
	 * 设置：小类型默认1正常2排单
	 */
	public void setMinType(Integer minType) {
		this.minType = minType;
	}
	/**
	 * 获取：小类型默认1正常2排单
	 */
	public Integer getMinType() {
		return minType;
	}
	/**
	 * 设置：是否是父级，是父级则不进行分切转化
	 */
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	/**
	 * 获取：是否是父级，是父级则不进行分切转化
	 */
	public Boolean getIsParent() {
		return isParent;
	}
	/**
	 * 设置：数量完成数
	 */
	public void setQuantityDone(Double quantityDone) {
		this.quantityDone = quantityDone;
	}
	/**
	 * 获取：数量完成数
	 */
	public Double getQuantityDone() {
		return quantityDone;
	}
	/**
	 * 设置：提货单ID
	 */
	public void setOrderPickupId(Integer orderPickupId) {
		this.orderPickupId = orderPickupId;
	}
	/**
	 * 获取：提货单ID
	 */
	public Integer getOrderPickupId() {
		return orderPickupId;
	}
	/**
	 * 设置：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public void setGroupId2(Integer groupId2) {
		this.groupId2 = groupId2;
	}
	/**
	 * 获取：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public Integer getGroupId2() {
		return groupId2;
	}
	/**
	 * 设置：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public void setGroupId3(Integer groupId3) {
		this.groupId3 = groupId3;
	}
	/**
	 * 获取：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public Integer getGroupId3() {
		return groupId3;
	}
	/**
	 * 设置：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public void setGroupId4(Integer groupId4) {
		this.groupId4 = groupId4;
	}
	/**
	 * 获取：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public Integer getGroupId4() {
		return groupId4;
	}
	/**
	 * 设置：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public void setGroupId5(Integer groupId5) {
		this.groupId5 = groupId5;
	}
	/**
	 * 获取：类型:1分切组2机台3叉车工4分切工5打包工
	 */
	public Integer getGroupId5() {
		return groupId5;
	}
	/**
	 * 设置：关联客户ID
	 */
	public void setRelativeId(Integer relativeId) {
		this.relativeId = relativeId;
	}
	/**
	 * 获取：关联客户ID
	 */
	public Integer getRelativeId() {
		return relativeId;
	}
	/**
	 * 设置：关联客户名称
	 */
	public void setRelativeCompanyName(String relativeCompanyName) {
		this.relativeCompanyName = relativeCompanyName;
	}
	/**
	 * 获取：关联客户名称
	 */
	public String getRelativeCompanyName() {
		return relativeCompanyName;
	}
}