package com.myzy.patient.system.entity.user;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentUserVO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "当前用户的角色属性，0-普通，1-管理员")
    private Integer auth;
}
