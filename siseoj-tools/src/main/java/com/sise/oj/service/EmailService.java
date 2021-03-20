package com.sise.oj.service;

import com.sise.oj.base.BaseService;
import com.sise.oj.domain.EmailConfig;
import com.sise.oj.domain.vo.EmailVo;

/**
 * @author Cijee
 * @version 1.0
 */
public interface EmailService extends BaseService<EmailConfig> {

    /**
     * 更新邮件配置
     *
     * @param emailConfig 新的邮件配置
     * @param old 旧的邮件配置
     * @return EmailConfig
     */
    EmailConfig config(EmailConfig emailConfig, EmailConfig old);

    /**
     * 查询邮件配置
     *
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     * @param emailVo 邮件发送的内容
     * @param emailConfig 邮件配置
     */
    void send(EmailVo emailVo, EmailConfig emailConfig);

    /**
     * 给指定用户的邮箱发送一封重置密码的邮件
     *
     * @param username
     * @param code
     * @param email
     */
    void sendRestPassword(String username, String code, String email);
}
