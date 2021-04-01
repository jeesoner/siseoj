package com.sise.oj.enums;

/**
 * 常量枚举类
 *
 * @author Cijee
 * @version 1.0
 */
public class Constants {

    /**
     * 提交评测结果的状态码
     *
     * @author Cijee
     * @version 1.0
     */
    public enum Judge {

        /**
         * 等待中
         */
        PENDING(0, "Pending"),
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
        SYSTEM_ERROR(9, "System Error"),
        /**
         * 运行中
         */
        RUNNING(10, "Running"),
        /**
         * 提交错误
         */
        ERROR(11, "Submit Error"),
        /**
         * 评测中
         */
        JUDGING(12, "Judging"),
        /**
         * 分值题
         */
        SC(13, "Score");

        private Integer status;
        private String name;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        Judge(Integer status, String name) {
            this.status = status;
            this.name = name;
        }

        public boolean isAc() {
            return this == Judge.AC;
        }

        public boolean isErr() {
            return this == Judge.WA ||
                    this == Judge.CE ||
                    this == Judge.RE ||
                    this == Judge.TLE ||
                    this == Judge.MLE ||
                    this == Judge.OLE ||
                    this == Judge.PE;
        }

        public boolean isPd() {
            return this == Judge.PENDING ||
                    this == Judge.SYSTEM_ERROR ||
                    this == Judge.RUNNING ||
                    this == Judge.ERROR ||
                    this == Judge.JUDGING;
        }

        public static String getNameByCode(int code) {
            for (Judge r : Judge.values()) {
                if (code == r.getStatus()) {
                    return r.getName();
                }
            }
            return null;
        }

        public static Integer getCodeByName(String name) {
            for (Judge r : Judge.values()) {
                if (name.equals(r.getName())) {
                    return r.getStatus();
                }
            }
            return null;
        }
    }

    /**
     * 比赛相关的常量
     */
    public enum Contest {
        TYPE_GENERAL(0, "acm"),
        TYPE_RATING(1, "rating"),

        STATUS_SCHEDULED(-1, "Scheduled"),
        STATUS_RUNNING(0, "Running"),
        STATUS_ENDED(1, "Ended"),

        AUTH_PUBLIC(0, "Public"),
        AUTH_PRIVATE(1, "Private"),
        AUTH_PROTECT(2, "Protect"),

        RECORD_NOT_AC_PENALTY(-1, "未AC通过算罚时"),
        RECORD_NOT_AC_NOT_PENALTY(0, "未AC通过不罚时"),
        RECORD_AC(1, "AC通过");

        private final Integer code;
        private final String name;

        Contest(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum Email {
        OJ_URL("http://localhost:8080"),
        OJ_NAME("scode Online Judge"),
        OJ_SHORT_NAME("HOJ"),
        REGISTER_KEY_PREFIX("register-user:"),
        RESET_PASSWORD_KEY_PREFIX("reset-password:");
        private String value;

        Email(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
