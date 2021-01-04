package com.sise.oj.security.bean;

import com.sise.oj.exception.BadConfigurationException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.Data;

import java.util.Objects;

/**
 * 登录配置
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class LoginProperties {

    /**
     * 验证码
     */
    private LoginCode loginCode;

    /**
     * 获取验证码生产类
     *
     * @return Captcha
     */
    public Captcha getCaptcha() {
        if (Objects.isNull(loginCode)) {
            loginCode = new LoginCode();
            if (Objects.isNull(loginCode.getCodeType())) {
                loginCode.setCodeType(LoginCodeEnum.arithmetic);
            }
        }
        return switchCaptcha(loginCode);
    }

    /**
     * 依据配置信息生产验证码
     *
     * @param loginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(LoginCode loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(loginCode.getLength());
                    break;
                case spec:
                    // 字符类型
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 验证码位数
                    captcha.setLen(loginCode.getLength());
                    break;
                default:
                    throw new BadConfigurationException("验证码配置信息错误！正确配置查看 LoginCodeEnum ");
            }
        }
        return captcha;
    }
}
