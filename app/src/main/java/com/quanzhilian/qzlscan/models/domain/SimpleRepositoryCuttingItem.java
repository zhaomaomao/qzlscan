package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.List;

public class SimpleRepositoryCuttingItem {
	private SimpleRepositoryCutting repositoryCutting;
	private ProductScm oldProductScm;
	private List<SimpleRepositoryCuttingDetail> repositoryCuttingDetailList;
	private List<SimpleRepositoryCuttingDetailIn> repositoryCuttingDetailInList;
	

	public List<SimpleRepositoryCuttingDetailIn> getRepositoryCuttingDetailInList() {
		return repositoryCuttingDetailInList;
	}

	public void setRepositoryCuttingDetailInList(List<SimpleRepositoryCuttingDetailIn> repositoryCuttingDetailInList) {
		this.repositoryCuttingDetailInList = repositoryCuttingDetailInList;
	}

	public ProductScm getOldProductScm() {
		return oldProductScm;
	}

	public void setOldProductScm(ProductScm oldProductScm) {
		this.oldProductScm = oldProductScm;
	}

	public List<SimpleRepositoryCuttingDetail> getRepositoryCuttingDetailList() {
		return repositoryCuttingDetailList;
	}

	public void setRepositoryCuttingDetailList(List<SimpleRepositoryCuttingDetail> repositoryCuttingDetailList) {
		this.repositoryCuttingDetailList = repositoryCuttingDetailList;
	}

	public SimpleRepositoryCutting getRepositoryCutting() {
		return repositoryCutting;
	}

	public void setRepositoryCutting(SimpleRepositoryCutting repositoryCutting) {
		this.repositoryCutting = repositoryCutting;
	}

	/**repository_cutting_item_id**/
	private Integer repositoryCuttingItemId;
	/**分切单id**/
	private Integer repositoryCuttingId;
	/**scm_id**/
	private Integer scmId;
	/**如果是订单，则是来源订单的id**/
	private Integer orderId;
	/**product_id**/
	private Integer productId;
	/**本次发货数量**/
	private Double quantity;
	/**数量单位**/
	private Integer quantityUnit;
	/**吨数**/
	private Double ton;
	/**old_product_id**/
	private Integer oldProductId;
	/**吨数**/
	private Double oldTon;
	/**商品名称**/
	private String productName;
	/**商品的唯一货号**/
	private String productSku;
	/**brand_name**/
	private String brandName;
	/**克重**/
	private Double gramWeight;
	/**规格**/
	private String specification;
	/**等级 **/
	private String grade;
	/**商品名称**/
	private String oldProductName;
	/**商品的唯一货号**/
	private String oldProductSku;
	/**old_brand_name**/
	private String oldBrandName;
	/**克重**/
	private Double oldGramWeight;
	/**规格**/
	private String oldSpecification;
	/**等级 **/
	private String oldGrade;
	/**历史数量**/
	private Double oldQuantity;
	/**历史数量单位**/
	private Integer oldQuantityUnit;
	/**其他费用/吨**/
	private BigDecimal otherFee;
	/**分切项完成率**/
	private Double tonDone;
	/**分切完成数量**/
	private Double quantityDone;
	/**原料库存ID**/
	private Integer oldRepositoryProductId;
	/**项目备注**/
	private String itemMemo;

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
	 * 设置：分切单id
	 */
	public void setRepositoryCuttingId(Integer repositoryCuttingId) {
		this.repositoryCuttingId = repositoryCuttingId;
	}
	/**
	 * 获取：分切单id
	 */
	public Integer getRepositoryCuttingId() {
		return repositoryCuttingId;
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
	 * 设置：product_id
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：product_id
	 */
	public Integer getProductId() {
		return productId;
	}
	/**
	 * 设置：本次发货数量
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	/**
	 * 获取：本次发货数量
	 */
	public Double getQuantity() {
		return quantity;
	}
	/**
	 * 设置：数量单位
	 */
	public void setQuantityUnit(Integer quantityUnit) {
		this.quantityUnit = quantityUnit;
	}
	/**
	 * 获取：数量单位
	 */
	public Integer getQuantityUnit() {
		return quantityUnit;
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
	 * 设置：old_product_id
	 */
	public void setOldProductId(Integer oldProductId) {
		this.oldProductId = oldProductId;
	}
	/**
	 * 获取：old_product_id
	 */
	public Integer getOldProductId() {
		return oldProductId;
	}
	/**
	 * 设置：吨数
	 */
	public void setOldTon(Double oldTon) {
		this.oldTon = oldTon;
	}
	/**
	 * 获取：吨数
	 */
	public Double getOldTon() {
		return oldTon;
	}
	/**
	 * 设置：商品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置：商品的唯一货号
	 */
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}
	/**
	 * 获取：商品的唯一货号
	 */
	public String getProductSku() {
		return productSku;
	}
	/**
	 * 设置：brand_name
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * 获取：brand_name
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * 设置：克重
	 */
	public void setGramWeight(Double gramWeight) {
		this.gramWeight = gramWeight;
	}
	/**
	 * 获取：克重
	 */
	public Double getGramWeight() {
		return gramWeight;
	}
	/**
	 * 设置：规格
	 */
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	/**
	 * 获取：规格
	 */
	public String getSpecification() {
		return specification;
	}
	/**
	 * 设置：等级 
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 获取：等级 
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：商品名称
	 */
	public void setOldProductName(String oldProductName) {
		this.oldProductName = oldProductName;
	}
	/**
	 * 获取：商品名称
	 */
	public String getOldProductName() {
		return oldProductName;
	}
	/**
	 * 设置：商品的唯一货号
	 */
	public void setOldProductSku(String oldProductSku) {
		this.oldProductSku = oldProductSku;
	}
	/**
	 * 获取：商品的唯一货号
	 */
	public String getOldProductSku() {
		return oldProductSku;
	}
	/**
	 * 设置：old_brand_name
	 */
	public void setOldBrandName(String oldBrandName) {
		this.oldBrandName = oldBrandName;
	}
	/**
	 * 获取：old_brand_name
	 */
	public String getOldBrandName() {
		return oldBrandName;
	}
	/**
	 * 设置：克重
	 */
	public void setOldGramWeight(Double oldGramWeight) {
		this.oldGramWeight = oldGramWeight;
	}
	/**
	 * 获取：克重
	 */
	public Double getOldGramWeight() {
		return oldGramWeight;
	}
	/**
	 * 设置：规格
	 */
	public void setOldSpecification(String oldSpecification) {
		this.oldSpecification = oldSpecification;
	}
	/**
	 * 获取：规格
	 */
	public String getOldSpecification() {
		return oldSpecification;
	}
	/**
	 * 设置：等级 
	 */
	public void setOldGrade(String oldGrade) {
		this.oldGrade = oldGrade;
	}
	/**
	 * 获取：等级 
	 */
	public String getOldGrade() {
		return oldGrade;
	}
	/**
	 * 设置：历史数量
	 */
	public void setOldQuantity(Double oldQuantity) {
		this.oldQuantity = oldQuantity;
	}
	/**
	 * 获取：历史数量
	 */
	public Double getOldQuantity() {
		return oldQuantity;
	}
	/**
	 * 设置：历史数量单位
	 */
	public void setOldQuantityUnit(Integer oldQuantityUnit) {
		this.oldQuantityUnit = oldQuantityUnit;
	}
	/**
	 * 获取：历史数量单位
	 */
	public Integer getOldQuantityUnit() {
		return oldQuantityUnit;
	}
	/**
	 * 设置：其他费用/吨
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}
	/**
	 * 获取：其他费用/吨
	 */
	public BigDecimal getOtherFee() {
		return otherFee;
	}
	/**
	 * 设置：分切项完成率
	 */
	public void setTonDone(Double tonDone) {
		this.tonDone = tonDone;
	}
	/**
	 * 获取：分切项完成率
	 */
	public Double getTonDone() {
		return tonDone;
	}
	/**
	 * 设置：分切完成数量
	 */
	public void setQuantityDone(Double quantityDone) {
		this.quantityDone = quantityDone;
	}
	/**
	 * 获取：分切完成数量
	 */
	public Double getQuantityDone() {
		return quantityDone;
	}
	/**
	 * 设置：原料库存ID
	 */
	public void setOldRepositoryProductId(Integer oldRepositoryProductId) {
		this.oldRepositoryProductId = oldRepositoryProductId;
	}
	/**
	 * 获取：原料库存ID
	 */
	public Integer getOldRepositoryProductId() {
		return oldRepositoryProductId;
	}
	/**
	 * 设置：项目备注
	 */
	public void setItemMemo(String itemMemo) {
		this.itemMemo = itemMemo;
	}
	/**
	 * 获取：项目备注
	 */
	public String getItemMemo() {
		return itemMemo;
	}
}