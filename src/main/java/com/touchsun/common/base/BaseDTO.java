package com.touchsun.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础传输对象
 *
 * @author lee
 */
@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -5853190635978548859L;

    /**
     * 主键
     */
    private String id;
}
