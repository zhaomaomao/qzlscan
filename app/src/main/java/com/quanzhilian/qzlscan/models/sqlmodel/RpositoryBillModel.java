package com.quanzhilian.qzlscan.models.sqlmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangtm on 2018/2/1.
 */

public class RpositoryBillModel implements Serializable {
    public String scmId;
    public Integer repositoryBillId;
    public String repositoryBillNo;
    public String relativeCompanyName;
    public Long createDate;
    public String repositoryName;
    public Integer repositoryId;
    public int state;

    public RpositoryBillModel(){}
    public RpositoryBillModel(String scmId, Integer repositoryBillId, String repositoryBillNo,String relativeCompanyName,Long createDate,String repositoryName,Integer repositoryId,int state){
        this.scmId = scmId;
        this.repositoryBillId = repositoryBillId;
        this.repositoryBillNo = repositoryBillNo;
        this.relativeCompanyName = relativeCompanyName;
        this.createDate = createDate;
        this.repositoryName = repositoryName;
        this.state = state;
        this.repositoryId = repositoryId;
    }
}
