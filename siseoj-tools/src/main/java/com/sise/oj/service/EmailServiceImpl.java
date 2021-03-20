package com.sise.oj.service;

import com.sise.oj.base.BaseServiceImpl;
import com.sise.oj.domain.EmailConfig;
import com.sise.oj.domain.vo.EmailVo;
import com.sise.oj.exception.BusinessException;
import com.sise.oj.mapper.EmailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

/**
 * @author Cijee
 * @version 1.0
 */
@Slf4j
@Service
public class EmailServiceImpl extends BaseServiceImpl<EmailMapper, EmailConfig> implements EmailService {

    private final JavaMailSender mailSender;

    private final EmailMapper emailMapper;

    private final TemplateEngine templateEngine;

    @Value("${code.expire}")
    private Integer expire;

    public EmailServiceImpl(JavaMailSender mailSender, EmailMapper emailMapper, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.emailMapper = emailMapper;
        this.templateEngine = templateEngine;
    }

    @Override
    public EmailConfig config(EmailConfig emailConfig, EmailConfig old) {
        emailConfig.setId(1L);
        emailMapper.updateById(emailConfig);
        return emailConfig;
    }

    @Override
    public EmailConfig find() {
        return emailMapper.selectById(1L);
    }

    @Override
    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if(emailConfig.getId() == null){
            throw new BusinessException("请先配置，再操作");
        }
        // 创建邮件信息
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getUser());
        message.setTo(emailVo.getTos());
        // 邮件标题
        message.setSubject(emailVo.getSubject());
        // 邮件内容
        message.setText(emailVo.getContent());
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void sendRestPassword(String username, String code, String email) {
    }
}
