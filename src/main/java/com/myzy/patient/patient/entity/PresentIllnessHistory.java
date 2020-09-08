package com.myzy.patient.patient.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (PresentIllnessHistory)表实体类
 * 现病史
 * @author leekejin
 * @since 2020-08-04 17:52:08
 */
@Data
public class PresentIllnessHistory extends Model<PresentIllnessHistory> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value="患者id")
    private Integer patientId;

    @ApiModelProperty(value="字段名")
    private String fieldValue;

    @ApiModelProperty(value="字段具体值")
    private String details;


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