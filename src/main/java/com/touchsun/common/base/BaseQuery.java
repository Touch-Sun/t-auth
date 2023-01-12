package com.touchsun.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.touchsun.common.constants.ConstantNumber;
import lombok.Data;

/**
 * 基础查询对象
 *
 * @author lee
 */
@Data
public class BaseQuery {

    /**
     * 主键
     */
    private String id;

    /**
     * 当前页
     */
    private Integer current = ConstantNumber.ONE;

    /**
     * 页大小
     */
    private Integer size = ConstantNumber.MAX_PAGE;

    /**
     * 排序字段
     */
    private String orderField;

    /**
     * 排序方式
     */
    private String orderType;


    public <T> Page<T> getPage() {
        return new Page<>(current, size);
    }
}
