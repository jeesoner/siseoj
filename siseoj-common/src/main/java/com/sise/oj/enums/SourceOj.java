package com.sise.oj.enums;

/**
 * OJ来源枚举类
 *
 * @author Cijee
 * @version 1.0
 */
public enum SourceOj {

    /**
     * 默认本地
     */
    DEFAULT(1, "SISEOJ"),
    HDU(2, "HDU"),
    POJ(3, "POJ"),
    UVA(4, "UVA"),
    BZOJ(5, "BZOJ"),
    UOJ(6, "UOJ"),
    NOD51(7, "51nod"),
    CF(8, "codeforces"),
    TC(9, "Topcoder"),
    SPOJ(10, "SPOJ"),
    Leetcode(11, "leetcode"),
    ZOJ(12, "ZOJ"),
    LUOGU(13, "洛谷");

    private int code;
    private String name;

    SourceOj(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(int code) {
        for (SourceOj item : SourceOj.values()) {
            if (item.getCode() == code) {
                return item.getName();
            }
        }
        return null;
    }
}
