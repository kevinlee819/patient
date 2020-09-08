package com.myzy.patient.patient.entity.patientInfo;

import com.myzy.patient.core.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 分页查询
 *
 * @author leekejin
 * @since 2020-07-31 16:57:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("分页")
public class QueryPatientInfoPage extends BasePage {

    @ApiModelProperty(value = "id")
    private String id;

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


}