package com.peashoot.mybatis.mybatistest.control;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.peashoot.mybatis.mybatistest.entity.User;
import com.peashoot.mybatis.mybatistest.util.DES3;
import com.peashoot.mybatis.mybatistest.util.DESMode;
import com.peashoot.mybatis.mybatistest.util.DESPadding;
import com.peashoot.mybatis.mybatistest.util.MD5;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserControlTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        // mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/hello").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("hello")));
    }

    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setAccount("account");
        user.setPassword("password");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
        mvc.perform(MockMvcRequestBuilders.get("/user/delete").param("account", "account")
                .accept(MediaType.APPLICATION_JSON_UTF8));
        mvc.perform(MockMvcRequestBuilders.post("/user/insert").headers(httpHeaders)
                .content(JSON.toJSON(user).toString()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("true")));
    }

    @Test
    public void testLogin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() {
            private static final long serialVersionUID = 1L;
            {
                put("account", Arrays.asList("account"));
                put("password", Arrays.asList("password"));
            }
        };
        mvc.perform(MockMvcRequestBuilders.post("/user/login").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("true")));
    }

    @Test
    public void testIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/get/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult result) throws Exception {
                        User user = JSON.parseObject(result.getResponse().getContentAsString(), User.class);
                        Assert.assertEquals(user.getAccount(), "wxl");
                        Assert.assertEquals(user.getPassword(), MD5.md5("123456"));
                    }
                });
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setAccount("wxl");
        user.setUsername("peashoot");
        user.setPassword("abc");
        user.setEmailaddress("1106328900@qq.com");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=utf-8");
        mvc.perform(MockMvcRequestBuilders.post("/user/insert").headers(httpHeaders)
                .content(JSON.toJSON(user).toString()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andExpect(content().string(equalTo("true")));
    }

    @Test
    public void testDecode() throws Exception {
        String key = "Why are you so niubi??";
        String iv = "12345678";
        String data = "j5R21ILRODIqiOWISKx9bWJgACJWrIZC";
        String afterDecode = DES3.decode(key, iv, data, DESMode.ECB, DESPadding.PKCS7Padding);
        Assert.assertEquals(afterDecode, "{\"lang\":\"en_US\"}");
    }

    @Test
    public void testEncode() throws Exception {
        String key = "Why are you so niubi??";
        String iv = "12345678";
        String data = "{\"lang\":\"en_US\"}";
        String afterEncode = DES3.encode(key, iv, data, DESMode.ECB, DESPadding.PKCS7Padding);
        Assert.assertEquals(afterEncode, "j5R21ILRODIqiOWISKx9bWJgACJWrIZC");
    }
}