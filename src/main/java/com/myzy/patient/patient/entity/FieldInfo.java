package com.myzy.patient.patient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (FieldInfo)表实体类
 * 字段信息
 * @author leekejin
 * @since 2020-08-04 11:59:01
 */
@Data
public class FieldInfo extends Model<FieldInfo> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "字段在数据库里的名称")
    private String fieldName;

    @ApiModelProperty(value = "前端应该展示的名称")
    private String showName;

    @ApiModelProperty(value = "前端页面的表格id，患者管理的表格暂列为1")
    private String viewId;

    @ApiModelProperty(value = "是否默认，0-非默认，1-默认")
    private Integer isDefault;

    @ApiModelProperty(value = "分组名")
    private String groupName;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}