package com.mrk.hmdp.dto;

import lombok.Data;

/**
 * @author mrk
 * @create 2024-06-27-16:26
 */
@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
