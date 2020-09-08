package com.myzy.patient.system.entity.user;

import com.myzy.patient.system.entity.SysFile;
import com.myzy.patient.system.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户表分页查询
 *
 * @author leekejin
 * @date 2020-06-05 10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShowUserPageVO extends SysUser {

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

}
