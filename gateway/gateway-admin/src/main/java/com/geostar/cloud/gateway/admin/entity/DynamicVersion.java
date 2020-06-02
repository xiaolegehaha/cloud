package com.geostar.cloud.gateway.admin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaolege
 * @since 2020-05-08
 */
@TableName("dynamic_version")
public class DynamicVersion extends Model<DynamicVersion> {

    private static final long serialVersionUID = 1L;
    private static final String create_time = "create_time";


    /**
     * 主键、自动增长、版本号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static String getCreate_time() {
        return create_time;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DynamicVersion{" +
        "id=" + id +
        ", createTime=" + createTime +
        "}";
    }
}
