package com.sise.oj.enums;

/**
 * 评测返回结果
 *
 * @author Cijee
 * @version 1.0
 */
public enum JudgeResult {
    /**
     * 评测中
     */
    PENDING(0, "Pending..."),
    /**
     * 结果正确
     */
    AC(1, "Accepted"),
    /**
     * 结果错误
     */
    WA(2, "Wrong Answer"),
    /**
     * 编译错误
     */
    CE(3, "Compilation Error"),
    /**
     * 运行时错误
     */
    RE(4, "Runtime Error"),
    /**
     * 时间超限
     */
    TLE(5, "Time Limit Exceeded"),
    /**
     * 内存超限制
     */
    MLE(6, "Memory Limit Exceeded"),
    /**
     * 输出超限制
     */
    OLE(7, "Output Limit Exceeded"),
    /**
     * 打印错误
     */
    PE(8, "Presentation Error"),
    /**
     * 系统错误
     */
    DANGER(9, "System Error"),
    /**
     * 运行中
     */
    RUNNING(10, "Running..."),
    /**
     * 提交错误
     */
    ERROR(11, "Submit Error"),
    /**
     * 评测中
     */
    JUDGING(12, "Judging..."),
    /**
     * 分值题
     */
    SC(13, "Score");

    private int code;
    private String name;

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

    JudgeResult(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public boolean isAc() {
        return this == JudgeResult.AC;
    }

    public boolean isErr() {
        return this == JudgeResult.WA ||
                this == JudgeResult.CE ||
                this == JudgeResult.RE ||
                this == JudgeResult.TLE ||
                this == JudgeResult.MLE ||
                this == JudgeResult.OLE ||
                this == JudgeResult.PE;
    }

    public boolean isPd() {
        return this == JudgeResult.PENDING ||
                this == JudgeResult.DANGER ||
                this == JudgeResult.RUNNING ||
                this == JudgeResult.ERROR ||
                this == JudgeResult.JUDGING;
    }

    public static String getNameByCode(int code) {
        for (JudgeResult r : JudgeResult.values()) {
            if (code == r.getCode()) {
                return r.getName();
            }
        }
        return null;
    }

    public static Integer getCodeByName(String name) {
        for (JudgeResult r : JudgeResult.values()) {
            if (name.equals(r.getName())) {
                return r.getCode();
            }
        }
        return null;
    }
}
