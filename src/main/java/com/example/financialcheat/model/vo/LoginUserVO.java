package com.example.financialcheat.model.vo;

import lombok.Data;

@Data
public class LoginUserVO {
    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}
