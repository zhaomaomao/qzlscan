package com.quanzhilian.qzlscan.models.sqlmodel;

import java.io.Serializable;

/**
 * Created by yangtm on 2018/2/1.
 */

public class RpositoryBillItemDetailModel implements Serializable {
    public String scmId;
    public Integer repositoryBillId;
    public Integer repositoryBillItemId;
    public Integer repositoryBillItemDetailId;
    public Integer repositoryProductId;
    public String barCode;
    public double amountLing;
    public double amountWeight;
    public Integer repositoryPositionId;
    public String repositoryPositionName;
    public String originalBarcode;
    public Integer state;
    public Integer isFull;
    public Integer type;
}
