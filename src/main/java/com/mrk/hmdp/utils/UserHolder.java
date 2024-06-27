package com.mrk.hmdp.utils;

import com.mrk.hmdp.dto.UserDTO;

/**
 * @author mrk
 * @create 2024-06-27-16:31
 */
public class UserHolder {

    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
