package com.myzy.patient.patient.entity.patientInfo;

import org.springframework.format.annotation.DateTimeFormat;
import com.myzy.patient.core.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 查询实体
 *
 * @author leekejin
 * @since 2020-8-4 11:40:06
 */
@Data
public class QueryPatientInfoVO extends BasePage  {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "患者姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出生查询起始日期")
    private Date birthStart;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出生查询结束日期")
    private Date birthEnd;

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

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value="查询录入开始时间")
    private Date startTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value="查询录入结束时间")
    private Date endTime;
}
