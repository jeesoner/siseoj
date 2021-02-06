package com.sise.oj.enums;

import java.util.Objects;

/**
 * 题目难度等级枚举类
 *
 * @author Cijee
 * @version 1.0
 */
public enum ProblemLevel {

    /**
     * 简单
     */
    EASY(1, "简单"),

    /**
     * 中等
     */
    MEDIUM(2, "中等"),

    /**
     * 困难
     */
    HARD(3, "困难");

    private int code;
    private String name;

    ProblemLevel(int code, String name) {
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

    public static String getNameByCode(Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }
        for (ProblemLevel level : ProblemLevel.values()) {
            if (level.getCode() == code) {
                return level.getName();
            }
        }
        return null;
    }
}
