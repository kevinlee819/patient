package com.myzy.patient.core.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础分页组件
 *
 * @author leekejin
 */
@Data
public class BasePage {

    @ApiModelProperty(value = "排序下划线分隔：（字段）_（'ascend' 'descend'）")
    private String sorter;

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    /**
     * 生成分页条件
     */
    public Page genPage() {
        Page page = new Page<>();
        page.setCurrent(this.currentPage == null ? 1 : this.currentPage);
        page.setSize(this.pageSize == null ? 10 : this.pageSize);
        // 加入排序
        if (this.sorter != null && this.sorter.split("_").length == 2) {
            String[] split = this.sorter.split("_");
            String sort = StringUtils.camelToUnderline(split[0]);
            if ("ascend".equals(split[1])) {
                page.addOrder(OrderItem.asc(sort));
            } else {
                page.addOrder(OrderItem.desc(sort));
            }
        }
        return page;
    }
}
