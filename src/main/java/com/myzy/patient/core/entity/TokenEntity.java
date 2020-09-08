package com.myzy.patient.core.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JwtToken实体
 *
 * @author leekejin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {

    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "角色列表,逗号分隔多个角色")
    private String roleIds;

    @ApiModelProperty(value = "创建者，以此区分用户权限")
    private String createUserName;
}
