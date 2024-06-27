package com.mrk.hmdp.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mrk
 * @create 2024-06-27-16:29
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
