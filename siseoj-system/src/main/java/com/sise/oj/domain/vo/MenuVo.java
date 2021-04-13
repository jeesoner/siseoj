package com.sise.oj.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 前端路由Vo
 *
 * @author Cijee
 * @version 1.0
 */
@Data
public class MenuVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private List<MenuVo> children;
}
