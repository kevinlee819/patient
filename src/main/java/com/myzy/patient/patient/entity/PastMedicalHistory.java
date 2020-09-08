package com.myzy.patient.patient.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (PastMedicalHistory)表实体类
 * 既往史
 * @author leekejin
 * @since 2020-08-04 17:16:05
 */
@Data
public class PastMedicalHistory extends Model<PastMedicalHistory> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("患者id")
    private Integer patientId;

    @ApiModelProperty("字段名,取字典的value")
    private String fieldValue;

    @ApiModelProperty("具体值")
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