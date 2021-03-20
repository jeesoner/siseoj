package com.sise.oj.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVo implements Serializable {

    /**
     * 收件人，支持多个收件人
     */
    @NotEmpty
    private String[] tos;

    /**
     * 邮件主题
     */
    @NotBlank
    private String subject;

    /**
     * 邮件内容
     */
    @NotBlank
    private String content;
}
