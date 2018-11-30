package com.kingtechfin.wxthirdparty.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author MrChao
 * @since 2018-07-02
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("wx_menu")
public class WxMenu extends Model<WxMenu> {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String authorizerAppid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long menuId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentMenuId;
    private String menuName;
    private String addTime;
    private String menuType;
    private String menuKey;
    private String menuValue;
    private String menuUrl;
    @TableField(exist = false)
    private List<WxMenu> childMenu;
    @TableField(exist = false)
    private Long orgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorizerAppid() {
        return authorizerAppid;
    }

    public void setAuthorizerAppid(String authorizerAppid) {
        this.authorizerAppid = authorizerAppid;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public List<WxMenu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<WxMenu> childMenu) {
        this.childMenu = childMenu;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "WxMenu{" +
        ", id=" + id +
        ", authorizerAppid=" + authorizerAppid +
        ", menuId=" + menuId +
        ", parentMenuId=" + parentMenuId +
        ", menuName=" + menuName +
        ", addTime=" + addTime +
        ", menuType=" + menuType +
        ", menuKey=" + menuKey +
        ", menuValue=" + menuValue +
        ", menuUrl=" + menuUrl +
        "}";
    }
}
