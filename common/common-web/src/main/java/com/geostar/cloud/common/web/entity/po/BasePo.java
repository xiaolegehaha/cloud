package com.geostar.cloud.common.web.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePo implements Serializable {
    public final static String DEFAULT_USERNAME = "system";
    /**主键id类型配置
     * AUTO : AUTO(0, “数据库ID自增”),
     * INPUT : INPUT(1, “用户输入ID”),
     * ID_WORKER : ID_WORKER(2, “全局唯一ID”),
     * UUID : UUID(3, “全局唯一ID”),
     * NONE : NONE(4, “该类型为未设置主键类型”),
     * ID_WORKER_STR : ID_WORKER_STR(5, “字符串全局唯一ID”);
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
}
