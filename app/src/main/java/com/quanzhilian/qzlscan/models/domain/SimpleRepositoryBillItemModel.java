package com.quanzhilian.qzlscan.models.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 */

public class SimpleRepositoryBillItemModel {
    public double getScanQuantity() {
        return scanQuantity;
    }

    public void setScanQuantity(double scanQuantity) {
        this.scanQuantity = scanQuantity;
    }

    private double scanQuantity;

    private Map<String,Object> map;
    private SimpleRepositoryBillModel repositoryBill;
    private ProductScm productScm;
    //private OrderScmInfo orderScmInfo;
    //private OrderPickup orderPickup;
    private BigDecimal surplusPrice; //用于封装，数据库无此字段
    private List<SimpleRepositoryBillItemDetailModel> repositoryBillItemDetailList;

    public List<SimpleRepositoryBillItemDetailModel> getRepositoryBillItemDetailList() {
        return repositoryBillItemDetailList;
    }

    public void setRepositoryBillItemDetailList(List<SimpleRepositoryBillItemDetailModel> repositoryBillItemDetailList) {
        this.repositoryBillItemDetailList = repositoryBillItemDetailList;
    }


    public BigDecimal getSurplusPrice() {
        return surplusPrice;
    }

    public void setSurplusPrice(BigDecimal surplusPrice) {
        this.surplusPrice = surplusPrice;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

//    public OrderPickup getOrderPickup() {
//        return orderPickup;
//    }
//
//    public void setOrderPickup(OrderPickup orderPickup) {
//        this.orderPickup = orderPickup;
//    }
//
//    public OrderScmInfo getOrderScmInfo() {
//        return orderScmInfo;
//    }
//
//    public void setOrderScmInfo(OrderScmInfo orderScmInfo) {
//        this.orderScmInfo = orderScmInfo;
//    }

    public SimpleRepositoryBillModel getRepositoryBill() {
        return repositoryBill;
    }

    public void setRepositoryBill(SimpleRepositoryBillModel repositoryBill) {
        this.repositoryBill = repositoryBill;
    }

    public ProductScm getProductScm() {
        return productScm;
    }

    public void setProductScm(ProductScm productScm) {
        this.productScm = productScm;
    }

    /****/
    private Integer repositoryBillItemId;
    /**出入库单id**/
    private Integer repositoryBillId;
    /****/
    private Integer productId;
    /**库位ID**/
    private Integer repositoryPositionId;
    /****/
    private Double quantity;
    /****/
    private Integer quantityUnit;
    /**单价,为了张价保留4位**/
    private BigDecimal price;
    /**总价**/
    private BigDecimal totalPrice;
    /**如果是订单，则是来源订单的id**/
    private Integer orderId;
    /****/
    private Double amount;
    /**吨数**/
    private Double ton;
    /**lingshu**/
    private Double ling;
    /**开票状态1未开票2部分开票3完全开票**/
    private Integer invoiceState;
    /**已开票金额**/
    private BigDecimal invoicePrice;
    /**开票信息**/
    private String invoiceInfo;
    /**开票日期,多个通过英文逗号分开**/
    private String invoiceDate;
    /**开票号码,多个通过英文逗号分开**/
    private String invoiceNo;
    /**开票金额,多个通过英文逗号分开**/
    private String invoicePriceInfo;
    /**消单金额**/
    private BigDecimal eliminatePrice;
    /**对应的收付款单号**/
    private String eliminateNo;
    /****/
    private Integer eliminateState;
    /**价格单位，价格类型，默认1吨单位**/
    private Integer priceUnit;
    /**单项备注**/
    private String note;
    /**系统ID**/
    private Integer scmId;
    /**产品成本费用，主要是用作出库用**/
    private BigDecimal productCost;
    /**是否计算运费**/
    private Boolean isCountFreight;
    /**是否统计发票**/
    private Boolean isCountInvoice;
    /**税点**/
    private BigDecimal tax;
    /**产品第二品名**/
    private String productName2;
    /**附加费类型，1：总费用，2：加吨单价，3：叠加克重附加费**/
    private Integer extraFeeType;
    /**附加费值，价格类型1：总费用，2：吨单价*吨数，类型3：加克重数量**/
    private Double extraFeeValue;
    /**附加费值，价格类型1：总附加费，2：吨单价*吨数，类型3：克重数量先转吨数*吨单价**/
    private BigDecimal extraFee;
    /**是否有明细项**/
    private Boolean hasDetail;
    /**出入库库存对应的库存ID**/
    private Integer repositoryProductId;

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
     * 设置：出入库单id
     */
    public void setRepositoryBillId(Integer repositoryBillId) {
        this.repositoryBillId = repositoryBillId;
    }
    /**
     * 获取：出入库单id
     */
    public Integer getRepositoryBillId() {
        return repositoryBillId;
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
     * 设置：
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    /**
     * 获取：
     */
    public Double getQuantity() {
        return quantity;
    }
    /**
     * 设置：
     */
    public void setQuantityUnit(Integer quantityUnit) {
        this.quantityUnit = quantityUnit;
    }
    /**
     * 获取：
     */
    public Integer getQuantityUnit() {
        return quantityUnit;
    }
    /**
     * 设置：单价,为了张价保留4位
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    /**
     * 获取：单价,为了张价保留4位
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * 设置：总价
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    /**
     * 获取：总价
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
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
     * 设置：lingshu
     */
    public void setLing(Double ling) {
        this.ling = ling;
    }
    /**
     * 获取：lingshu
     */
    public Double getLing() {
        return ling;
    }
    /**
     * 设置：开票状态1未开票2部分开票3完全开票
     */
    public void setInvoiceState(Integer invoiceState) {
        this.invoiceState = invoiceState;
    }
    /**
     * 获取：开票状态1未开票2部分开票3完全开票
     */
    public Integer getInvoiceState() {
        return invoiceState;
    }
    /**
     * 设置：已开票金额
     */
    public void setInvoicePrice(BigDecimal invoicePrice) {
        this.invoicePrice = invoicePrice;
    }
    /**
     * 获取：已开票金额
     */
    public BigDecimal getInvoicePrice() {
        return invoicePrice;
    }
    /**
     * 设置：开票信息
     */
    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }
    /**
     * 获取：开票信息
     */
    public String getInvoiceInfo() {
        return invoiceInfo;
    }
    /**
     * 设置：开票日期,多个通过英文逗号分开
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    /**
     * 获取：开票日期,多个通过英文逗号分开
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }
    /**
     * 设置：开票号码,多个通过英文逗号分开
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    /**
     * 获取：开票号码,多个通过英文逗号分开
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }
    /**
     * 设置：开票金额,多个通过英文逗号分开
     */
    public void setInvoicePriceInfo(String invoicePriceInfo) {
        this.invoicePriceInfo = invoicePriceInfo;
    }
    /**
     * 获取：开票金额,多个通过英文逗号分开
     */
    public String getInvoicePriceInfo() {
        return invoicePriceInfo;
    }
    /**
     * 设置：消单金额
     */
    public void setEliminatePrice(BigDecimal eliminatePrice) {
        this.eliminatePrice = eliminatePrice;
    }
    /**
     * 获取：消单金额
     */
    public BigDecimal getEliminatePrice() {
        return eliminatePrice;
    }
    /**
     * 设置：对应的收付款单号
     */
    public void setEliminateNo(String eliminateNo) {
        this.eliminateNo = eliminateNo;
    }
    /**
     * 获取：对应的收付款单号
     */
    public String getEliminateNo() {
        return eliminateNo;
    }
    /**
     * 设置：
     */
    public void setEliminateState(Integer eliminateState) {
        this.eliminateState = eliminateState;
    }
    /**
     * 获取：
     */
    public Integer getEliminateState() {
        return eliminateState;
    }
    /**
     * 设置：价格单位，价格类型，默认1吨单位
     */
    public void setPriceUnit(Integer priceUnit) {
        this.priceUnit = priceUnit;
    }
    /**
     * 获取：价格单位，价格类型，默认1吨单位
     */
    public Integer getPriceUnit() {
        return priceUnit;
    }
    /**
     * 设置：单项备注
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * 获取：单项备注
     */
    public String getNote() {
        return note;
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
     * 设置：产品成本费用，主要是用作出库用
     */
    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }
    /**
     * 获取：产品成本费用，主要是用作出库用
     */
    public BigDecimal getProductCost() {
        return productCost;
    }
    /**
     * 设置：是否计算运费
     */
    public void setIsCountFreight(Boolean isCountFreight) {
        this.isCountFreight = isCountFreight;
    }
    /**
     * 获取：是否计算运费
     */
    public Boolean getIsCountFreight() {
        return isCountFreight;
    }
    /**
     * 设置：是否统计发票
     */
    public void setIsCountInvoice(Boolean isCountInvoice) {
        this.isCountInvoice = isCountInvoice;
    }
    /**
     * 获取：是否统计发票
     */
    public Boolean getIsCountInvoice() {
        return isCountInvoice;
    }
    /**
     * 设置：税点
     */
    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
    /**
     * 获取：税点
     */
    public BigDecimal getTax() {
        return tax;
    }
    /**
     * 设置：产品第二品名
     */
    public void setProductName2(String productName2) {
        this.productName2 = productName2;
    }
    /**
     * 获取：产品第二品名
     */
    public String getProductName2() {
        return productName2;
    }
    /**
     * 设置：附加费类型，1：总费用，2：加吨单价，3：叠加克重附加费
     */
    public void setExtraFeeType(Integer extraFeeType) {
        this.extraFeeType = extraFeeType;
    }
    /**
     * 获取：附加费类型，1：总费用，2：加吨单价，3：叠加克重附加费
     */
    public Integer getExtraFeeType() {
        return extraFeeType;
    }
    /**
     * 设置：附加费值，价格类型1：总费用，2：吨单价*吨数，类型3：加克重数量
     */
    public void setExtraFeeValue(Double extraFeeValue) {
        this.extraFeeValue = extraFeeValue;
    }
    /**
     * 获取：附加费值，价格类型1：总费用，2：吨单价*吨数，类型3：加克重数量
     */
    public Double getExtraFeeValue() {
        return extraFeeValue;
    }
    /**
     * 设置：附加费值，价格类型1：总附加费，2：吨单价*吨数，类型3：克重数量先转吨数*吨单价
     */
    public void setExtraFee(BigDecimal extraFee) {
        this.extraFee = extraFee;
    }
    /**
     * 获取：附加费值，价格类型1：总附加费，2：吨单价*吨数，类型3：克重数量先转吨数*吨单价
     */
    public BigDecimal getExtraFee() {
        return extraFee;
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
     * 设置：出入库库存对应的库存ID
     */
    public void setRepositoryProductId(Integer repositoryProductId) {
        this.repositoryProductId = repositoryProductId;
    }
    /**
     * 获取：出入库库存对应的库存ID
     */
    public Integer getRepositoryProductId() {
        return repositoryProductId;
    }
}
