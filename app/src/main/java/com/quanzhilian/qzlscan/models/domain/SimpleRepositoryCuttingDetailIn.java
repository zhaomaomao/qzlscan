package com.quanzhilian.qzlscan.models.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 分切入库明细
 * 
 * @author caixinyang
 * @email cxyangc@qq.com
 * @date 2018-01-08 18:27:58
 */
public class SimpleRepositoryCuttingDetailIn implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**repository_bill_item_detail_in_id**/
	private Integer repositoryBillItemDetailInId;
	/**明细ID**/
	private Integer repositoryCuttingDetailId;
	/**scm_id**/
	private Integer scmId;
	/**repository_cutting_id**/
	private Integer repositoryCuttingId;
	/**repository_cutting_item_id**/
	private Integer repositoryCuttingItemId;
	/**件重**/
	private Double amountWeight;
	/**创建时间**/
	private Date createDate;
	/**产品ID**/
	private Integer productId;
	/**入库库位ID，默认0没有库位**/
	private Integer repositoryPositionId;
	/**入库备注信息**/
	private String remark;
	/**入库前对应的仓库产品ID**/
	private Integer repositoryProductId;
	/**条形码**/
	private String barCode;
	/**厂家条码号**/
	private String barCodeFactory;
	/**件令数**/
	private Double amountLing;
	/**原件重，当前件重可能和原件重不符**/
	private Double oldAmountWeight;

	/**
	 * 设置：repository_bill_item_detail_in_id
	 */
	public void setRepositoryBillItemDetailInId(Integer repositoryBillItemDetailInId) {
		this.repositoryBillItemDetailInId = repositoryBillItemDetailInId;
	}
	/**
	 * 获取：repository_bill_item_detail_in_id
	 */
	public Integer getRepositoryBillItemDetailInId() {
		return repositoryBillItemDetailInId;
	}
	/**
	 * 设置：明细ID
	 */
	public void setRepositoryCuttingDetailId(Integer repositoryCuttingDetailId) {
		this.repositoryCuttingDetailId = repositoryCuttingDetailId;
	}
	/**
	 * 获取：明细ID
	 */
	public Integer getRepositoryCuttingDetailId() {
		return repositoryCuttingDetailId;
	}
	/**
	 * 设置：scm_id
	 */
	public void setScmId(Integer scmId) {
		this.scmId = scmId;
	}
	/**
	 * 获取：scm_id
	 */
	public Integer getScmId() {
		return scmId;
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
	 * 设置：入库前对应的仓库产品ID
	 */
	public void setRepositoryProductId(Integer repositoryProductId) {
		this.repositoryProductId = repositoryProductId;
	}
	/**
	 * 获取：入库前对应的仓库产品ID
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
}
