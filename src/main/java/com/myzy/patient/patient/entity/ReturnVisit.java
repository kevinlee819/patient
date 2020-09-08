package com.myzy.patient.patient.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (ReturnVisit)表实体类
 * 回访信息
 * @author leekejin
 * @since 2020-08-04 14:14:59
 */
@Data
public class ReturnVisit extends Model<ReturnVisit> {

    @ApiModelProperty("id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("患者id")
    private Integer patientId;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("回访日期")
    private Date returnVisitDate;

    @ApiModelProperty("是否用药")
    private String isUseMedicine;

    @ApiModelProperty("回访目的")
    private String returnVisitPurpose;

    @ApiModelProperty("主要问题")
    private String mainQuestion;

    @ApiModelProperty("答复情况")
    private String answer;

    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty("下次回访时间")
    private Date nextVisitTime;


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