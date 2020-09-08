package com.myzy.patient.system.entity.user;

import com.myzy.patient.core.entity.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表分页查询
 *
 * @author leekejin
 * @date 2020-06-05 10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryUserPageVO extends BasePage {

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "状态：0正常、1停用")
    private Integer status;

    @ApiModelProperty(value = "创建人账号")
    private String createUserName;

}
