package com.myzy.patient.patient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (TreatmentInfo)表实体类
 * 就诊信息
 * @author leekejin
 * @since 2020-08-04 08:52:21
 */
@Data
public class TreatmentInfo extends Model<TreatmentInfo> {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "患者id")
    private Integer patientId;

    @ApiModelProperty(value = "药品组，字符串直接传 络倍进 或 其他")
    private String drugGroup;

    @ApiModelProperty(value = "适应症")
    private String adaptDisease;

    @ApiModelProperty(value = "确诊医院")
    private String confirmHospital;

    @ApiModelProperty(value = "确诊医生")
    private String confirmDoctor;

    @ApiModelProperty(value = "购药医院")
    private String buyMedicineHospital;

    @ApiModelProperty(value = "确诊时间")
    private Date confirmTime;

    @ApiModelProperty(value = "联合治疗")
    private String unionCure;

    @ApiModelProperty(value = "首次确认客服")
    private String firstService;

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