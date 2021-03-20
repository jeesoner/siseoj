package com.sise.oj.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Cijee
 * @version 1.0
 */
@Data
public class RankDto implements Serializable {

    private Long uid;

    private String username;

    private String nickname;

    private Integer rating;

    private String motto;

    private Integer accept;

    private Integer solved;

    private Integer submissions;
}
