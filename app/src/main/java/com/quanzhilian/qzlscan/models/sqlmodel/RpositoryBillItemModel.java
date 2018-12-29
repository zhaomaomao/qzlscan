package com.quanzhilian.qzlscan.models.sqlmodel;

import android.text.style.TtsSpan;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yangtm on 2018/2/1.
 */

public class RpositoryBillItemModel implements Serializable {
    public String scmId;
    public Integer productId;
    public Integer repositoryBillId;
    public String repositoryBillNo;
    public Integer repositoryBillItemId;
    public String productInfo;
    public String productSku;
    public String productName;
    public String factoryName;
    public String brandName;
    public double gramWeight;
    public String specification;
    public String grade;
    public double quantity;
    public Integer quantityUnit;
    public double price;
    public Integer priceUnit;
    public double scanQuantity;
    public double ton;
    public Integer hasDetail;
    public Integer type;
    public Map<String,Object> map;
    public List<RpositoryBillItemDetailModel> rpositoryBillItemDetailModelList;
}
