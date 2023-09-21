package com.hyf.demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ikun
 * @Date 2023/9/14 17:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtil<T> {

    private Long current;

    private Long size;

    private Long total;

    private List<T> info;

}
