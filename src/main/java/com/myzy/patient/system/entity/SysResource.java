package com.myzy.patient.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API资源表
 *
 * @author leekejin
 * @since 2020-06-02 10:05:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysResource extends Model<SysResource> {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "详细描述说明")
    private String description;

    @ApiModelProperty(value = "资源路径")
    private String url;

    @ApiModelProperty(value = "请求方法(GET查询、POST新增、PUT修改 、DELETE删除)")
    private String method;

    @ApiModelProperty(value = "状态：0正常、1停用、9删除")
    private Integer status;

}