package com.myzy.patient.patient.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (MedicationInfo)表实体类
 * 用药信息
 * @author leekejin
 * @since 2020-08-04 15:24:41
 */

@Data
public class MedicationInfo extends Model<MedicationInfo> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("患者Id")
    private Integer patientId;

    @ApiModelProperty("处方医院")
    private String prescriptionHospital;

    @ApiModelProperty("处方医生")
    private String prescriptionDoctor;

    @ApiModelProperty("购药时间")
    private Date buyMedicineDate;

    @ApiModelProperty("购药种类")
    private String buyMedicineType;

    @ApiModelProperty("数量")
    private String quantity;

    @ApiModelProperty("价格")
    private String unitPrice;

    @ApiModelProperty("总价")
    private String totalPrice;

//    @ApiModelProperty("是否赠药")
//    private String isGiveMedicine;

    @ApiModelProperty("可用药时间")
    private Date medicationTime;

    @ApiModelProperty("下次购药时间")
    private Date nextBuyMedicineDate;

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