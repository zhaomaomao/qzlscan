package com.quanzhilian.qzlscan.models.enums;

/**
 * Created by yangtm on 2018/2/23.
 */

public enum EnumQuantityUnit {
    TON("吨", 1),
    JIAN("件", 2),
    LING("令", 3),
    ZHANG("张", 4),
    PINGFANG("平方", 5);

    private final int val;
    private final String name;

    public int getVal() {
        return val;
    }

    public String getName() {
        return name;
    }

    EnumQuantityUnit(String name, int val) {

        this.val = val;
        this.name = name;
    }

    //根据值反查枚举
    public static EnumQuantityUnit getValueById(int type) {
        EnumQuantityUnit state = null;
        EnumQuantityUnit[] typeList = EnumQuantityUnit.values();
        for (EnumQuantityUnit pt : typeList) {
            if (pt.val == type) {
                state = pt;
            }
        }
        return state;
    }
}
