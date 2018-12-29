package com.quanzhilian.qzlscan.models.enums;

/**
 * Created by yangtm on 2018/2/23.
 */

/**
 * in_repository_bill：查询入库，
 *          out_repository_bill：查询出库；
 *          maching_bill：查询加工单
 */
public enum EnumQueryType {
    in_repository_bill("查询入库", 1),
    out_repository_bill("查询出库", 2),
    maching_bill("查询加工单", 3);

    private final int val;
    private final String name;

    public int getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    EnumQueryType(String name, int val) {

        this.val = val;
        this.name = name;
    }

    //根据值反查枚举
    public static EnumQueryType getValueById(int type) {
        EnumQueryType state = null;
        EnumQueryType[] typeList = EnumQueryType.values();
        for (EnumQueryType pt : typeList) {
            if (pt.val == type) {
                state = pt;
            }
        }
        return state;
    }
}
