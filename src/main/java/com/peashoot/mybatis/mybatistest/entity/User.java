package com.peashoot.mybatis.mybatistest.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class User {
    private Integer id;

    private String account;

    private String emailaddress;

    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resgiterdate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifydate;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return the emailaddress
     */
    public String getEmailaddress() {
        return emailaddress;
    }

    /**
     * @param emailaddress the emailaddress to set
     */
    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the resgiterdate
     */
    public Date getResgiterdate() {
        return resgiterdate;
    }

    /**
     * @param resgiterdate the resgiterdate to set
     */
    public void setResgiterdate(Date resgiterdate) {
        this.resgiterdate = resgiterdate;
    }

    /**
     * @return the modifydate
     */
    public Date getModifydate() {
        return modifydate;
    }

    /**
     * @param modifydate the modifydate to set
     */
    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

}