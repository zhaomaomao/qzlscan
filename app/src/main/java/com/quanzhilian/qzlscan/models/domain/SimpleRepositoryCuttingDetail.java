package com.quanzhilian.qzlscan.models.domain;

import java.util.List;

public class SimpleRepositoryCuttingDetail {
	private List<SimpleRepositoryCuttingDetailIn> repositoryCuttingDetailInList;
	
	public List<SimpleRepositoryCuttingDetailIn> getRepositoryCuttingDetailInList() {
		return repositoryCuttingDetailInList;
	}
	public void setRepositoryCuttingDetailInList(List<SimpleRepositoryCuttingDetailIn> repositoryCuttingDetailInList) {
		this.repositoryCuttingDetailInList = repositoryCuttingDetailInList;
	}
	/**repository_cutting_detail_id**/
	private Integer repositoryCuttingDetailId;
	/**repository_product_id**/
	private Integer repositoryProductId;
	/**repository_cutting_id**/
	private Integer repositoryCuttingId;
	/**repository_cutting_item_id**/
	private Integer repositoryCuttingItemId;
	/**备注信息**/
	private String note;
	/**条码号**/
	private String barCode;
	/**原料产品明细**/
	private String oldProductInfo;
	/**可用量**/
	private Double availableTon;
	/**库位**/
	private String repositoryPositionName;
	/**成品规格**/
	private String targetSpecification;
	/**分切数**/
	private Double detailTon;
	/**余卷数量**/
	private Double leftTon;
	/**父级库存ID**/
	private Integer parentRepositoryProductId;
	/**系统ID**/
	private Integer scmId;
	/**分切数**/
	private Double detailQuantity;
	/**是否余卷**/
	private Boolean isLeft;
	/**成品产品ID**/
	private Integer productId;
	/**分切数单位**/
	private Integer detailQuantityUnit;
	/**库位ID**/
	private Integer repositoryPositionId;
	/**是否有入库明细**/
	private Boolean hasInDetail;

	/**
	 * 设置：repository_cutting_detail_id
	 */
	public void setRepositoryCuttingDetailId(Integer repositoryCuttingDetailId) {
		this.repositoryCuttingDetailId = repositoryCuttingDetailId;
	}
	/**
	 * 获取：repository_cutting_detail_id
	 */
	public Integer getRepositoryCuttingDetailId() {
		return repositoryCuttingDetailId;
	}
	/**
	 * 设置：repository_product_id
	 */
	public void setRepositoryProductId(Integer repositoryProductId) {
		this.repositoryProductId = repositoryProductId;
	}
	/**
	 * 获取：repository_product_id
	 */
	public Integer getRepositoryProductId() {
		return repositoryProductId;
	}
	/**
	 * 设置：repository_cutting_id
	 */
	public void setRepositoryCuttingId(Integer repositoryCuttingId) {
		this.repositoryCuttingId = repositoryCuttingId;
	}
	/**
	 * 获取：repository_cutting_id
	 */
	public Integer getRepositoryCuttingId() {
		return repositoryCuttingId;
	}
	/**
	 * 设置：repository_cutting_item_id
	 */
	public void setRepositoryCuttingItemId(Integer repositoryCuttingItemId) {
		this.repositoryCuttingItemId = repositoryCuttingItemId;
	}
	/**
	 * 获取：repository_cutting_item_id
	 */
	public Integer getRepositoryCuttingItemId() {
		return repositoryCuttingItemId;
	}
	/**
	 * 设置：备注信息
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注信息
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：条码号
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	/**
	 * 获取：条码号
	 */
	public String getBarCode() {
		return barCode;
	}
	/**
	 * 设置：原料产品明细
	 */
	public void setOldProductInfo(String oldProductInfo) {
		this.oldProductInfo = oldProductInfo;
	}
	/**
	 * 获取：原料产品明细
	 */
	public String getOldProductInfo() {
		return oldProductInfo;
	}
	/**
	 * 设置：可用量
	 */
	public void setAvailableTon(Double availableTon) {
		this.availableTon = availableTon;
	}
	/**
	 * 获取：可用量
	 */
	public Double getAvailableTon() {
		return availableTon;
	}
	/**
	 * 设置：库位
	 */
	public void setRepositoryPositionName(String repositoryPositionName) {
		this.repositoryPositionName = repositoryPositionName;
	}
	/**
	 * 获取：库位
	 */
	public String getRepositoryPositionName() {
		return repositoryPositionName;
	}
	/**
	 * 设置：成品规格
	 */
	public void setTargetSpecification(String targetSpecification) {
		this.targetSpecification = targetSpecification;
	}
	/**
	 * 获取：成品规格
	 */
	public String getTargetSpecification() {
		return targetSpecification;
	}
	/**
	 * 设置：分切数
	 */
	public void setDetailTon(Double detailTon) {
		this.detailTon = detailTon;
	}
	/**
	 * 获取：分切数
	 */
	public Double getDetailTon() {
		return detailTon;
	}
	/**
	 * 设置：余卷数量
	 */
	public void setLeftTon(Double leftTon) {
		this.leftTon = leftTon;
	}
	/**
	 * 获取：余卷数量
	 */
	public Double getLeftTon() {
		return leftTon;
	}
	/**
	 * 设置：父级库存ID
	 */
	public void setParentRepositoryProductId(Integer parentRepositoryProductId) {
		this.parentRepositoryProductId = parentRepositoryProductId;
	}
	/**
	 * 获取：父级库存ID
	 */
	public Integer getParentRepositoryProductId() {
		return parentRepositoryProductId;
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
	 * 设置：分切数
	 */
	public void setDetailQuantity(Double detailQuantity) {
		this.detailQuantity = detailQuantity;
	}
	/**
	 * 获取：分切数
	 */
	public Double getDetailQuantity() {
		return detailQuantity;
	}
	/**
	 * 设置：是否余卷
	 */
	public void setIsLeft(Boolean isLeft) {
		this.isLeft = isLeft;
	}
	/**
	 * 获取：是否余卷
	 */
	public Boolean getIsLeft() {
		return isLeft;
	}
	/**
	 * 设置：成品产品ID
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：成品产品ID
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：分切数单位
	 */
	public void setDetailQuantityUnit(Integer detailQuantityUnit) {
		this.detailQuantityUnit = detailQuantityUnit;
	}
	/**
	 * 获取：分切数单位
	 */
	public Integer getDetailQuantityUnit() {
		return detailQuantityUnit;
	}
	/**
	 * 设置：库位ID
	 */
	public void setRepositoryPositionId(Integer repositoryPositionId) {
		this.repositoryPositionId = repositoryPositionId;
	}
	/**
	 * 获取：库位ID
	 */
	public Integer getRepositoryPositionId() {
		return repositoryPositionId;
	}
	/**
	 * 设置：是否有入库明细
	 */
	public void setHasInDetail(Boolean hasInDetail) {
		this.hasInDetail = hasInDetail;
	}
	/**
	 * 获取：是否有入库明细
	 */
	public Boolean getHasInDetail() {
		return hasInDetail;
	}
}