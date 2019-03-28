package com.peashoot.mybatis.mybatistest.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUser {
    @NotNull(message = "The account can't be null")
    @NotBlank(message = "The account can't be empty")
    private String account;
    private String username;
    @Email(message = "The format of emailAddress isn't correct")
    private String emailaddress;
    @NotNull(message = "The password can't be empty")
    @NotBlank(message = "The password can't be null")
    private String password;
}