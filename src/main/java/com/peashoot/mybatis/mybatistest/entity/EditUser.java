package com.peashoot.mybatis.mybatistest.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditUser {
    @Range(min = 1, message = "Id must bigger than zero")
    private int id;
    @NotBlank(message = "The account can't be empty")
    @NotNull(message = "The account can't be null")
    private String account;
    private String username;
    @Email(message = "The format of emailaddress isn't correct")
    private String emailaddress;
}