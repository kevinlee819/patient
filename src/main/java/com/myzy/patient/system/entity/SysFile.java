package com.myzy.patient.system.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件信息表实体
 *
 * @author leekejin
 * @since 2020-07-13 09:10:13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFile extends Model<SysFile> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件原名称")
    private String originalName;

    @ApiModelProperty(value = "文件新名称（uuid随机）")
    private String newName;

    @ApiModelProperty(value = "文件类型（识别是否是图片或文档...）")
    private String fileType;

    @ApiModelProperty(value = "缩略图BASE64")
    private String thumb;

    @ApiModelProperty(value = "关联id")
    private Integer relationId;

    @ApiModelProperty(value = "关联的业务类型（使用枚举）")
    private Integer relationType;

    @ApiModelProperty(value = "关联业务子类型（一个类型下存在多种业务的时候使用）")
    private Integer relationChildType;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}