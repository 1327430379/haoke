package cn.haoke.mgmt.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Menu implements Serializable {

    /**
     * 路径
     */
    private String path;

    /***
     * 名称
     */
    private String name;

    /***
     * 图标
     */
    private String icon;

    private Boolean exact;

    /**
     * 子菜单
     */
    private List<Menu> children = new ArrayList<>();

    /**
     * 父菜单
     */
    private Menu parent;
}
