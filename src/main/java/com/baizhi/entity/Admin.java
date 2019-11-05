package com.baizhi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sun.security.util.Password;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Admin {
    private String id;
    private String username;
    private String password;
    private String nickname;
}
