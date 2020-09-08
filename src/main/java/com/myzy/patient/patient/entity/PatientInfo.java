package com.myzy.patient.patient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (PatientInfo)表实体类
 *
 * @author leekejin
 * @since 2020-08-03 09:55:40
 */
@Data
public class PatientInfo extends Model<PatientInfo> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "患者姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    private Date birth;

    @ApiModelProperty(value = "是否医保")
    private String isInsurance;

    @ApiModelProperty(value="联系方式")
    private String contactType;

    @ApiModelProperty(value="家庭电话")
    private String homePhone;

    @ApiModelProperty(value="地址")
    private String address;

    @ApiModelProperty(value="居住省")
    private String province;

    @ApiModelProperty(value="居住市")
    private String city;

    @ApiModelProperty(value="民族")
    private String nation;

    @ApiModelProperty(value="录入人")
    private String inputPeople;

    @ApiModelProperty(value="录入时间")
    private Date inputDate;

    @ApiModelProperty(value="创建人")
    private String createPeople;

    @ApiModelProperty(value="创建时间")
    private Date createDate;



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