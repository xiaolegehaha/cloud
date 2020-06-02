package com.geostar.cloud.gateway.admin.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.geostar.cloud.gateway.admin.dto.GatewayFilterDefinition;
import com.geostar.cloud.gateway.admin.dto.GatewayPredicateDefinition;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@TableName("gateway_routes")
@Data
public class GatewayRoutes extends Model<GatewayRoutes> {

    private static final long serialVersionUID = 1L;
    private static final String is_ebl = "is_ebl";
    private static final String is_del = "is_del";

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路由id
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 转发目标uri
     */
    @TableField("route_uri")
    private String routeUri;

    /**
     * 路由执行顺序
     */
    @TableField("route_order")
    private Integer routeOrder;

    /**
     * 断言字符串集合，字符串结构：[{
                "name":"Path",
                "args":{
                   "pattern" : "/zy/**"
                }
              }]
     */
    private String predicates;

    /**
     * 过滤器字符串集合，字符串结构：{
              	"name":"StripPrefix",
              	 "args":{
              	 	"_genkey_0":"1"
              	 }
              }
     */
    private String filters;

    /**
     * 是否启用
     */
    @TableField("is_ebl")
    @TableLogic
    private Integer isEbl;

    /**
     * 是否删除
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 获取断言集合
     * @return
     */
    public List<GatewayPredicateDefinition> getPredicateDefinition(){
        if(!StringUtils.isEmpty(this.predicates)){
            return JSON.parseArray(this.predicates , GatewayPredicateDefinition.class);
        }
        return null;
    }

    /**
     * 获取过滤器集合
     * @return
     */
    public List<GatewayFilterDefinition> getFilterDefinition(){
        if(!StringUtils.isEmpty(this.filters)){
            return JSON.parseArray(this.filters , GatewayFilterDefinition.class);
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    public String getRouteUri() {
        return routeUri;
    }

    public void setRouteUri(String routeUri) {
        this.routeUri = routeUri;
    }
    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }
    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }
    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
    public Integer getIsEbl() {
        return isEbl;
    }

    public void setIsEbl(Integer isEbl) {
        this.isEbl = isEbl;
    }
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static String getIs_ebl() {
        return is_ebl;
    }

    public static String getIs_del() {
        return is_del;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GatewayRoutes{" +
        "id=" + id +
        ", routeId=" + routeId +
        ", routeUri=" + routeUri +
        ", routeOrder=" + routeOrder +
        ", predicates=" + predicates +
        ", filters=" + filters +
        ", isEbl=" + isEbl +
        ", isDel=" + isDel +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
