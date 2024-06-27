package com.mrk.hmdp.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mrk
 * @create 2024-06-27-16:26
 */
@Data
public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
