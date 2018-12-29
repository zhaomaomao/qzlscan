package com.quanzhilian.qzlscan.models.enums;

/**
 * Created by yangtm on 2018/3/7.
 */

public class MessageWhat {

    public MessageWhat(){}

    public interface MessageType {
        int QUERY_POSITION_SUCCESS = 12000;
        int QUERY_DETAIL_SUCCESS = 10000;
        int QUERY_DETAIL_FAIL = 20000;
        int SAVE_CHANGE_POSITION_SUCCESS = 30000;
        int SAVE_EXIT = 40000;
        int GET_QUERY_TIEM_SUCCESS = 50000;
        int SAVE_BILL_SUCCESS = 60000;//保存单据成功
        int APPROVE_BILL_SUCCESS = 70000;//审核单据成功
        int BILL_APPROVEED = 80000;//单据已审核
    }
}
