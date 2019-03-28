package com.peashoot.mybatis.mybatistest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.peashoot.mybatis.mybatistest.dao")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setAddCommandLineProperties(false);// 屏蔽命令行访问属性的设置
		app.run(args);
		//SpringApplication.run(DemoApplication.class, args);
	}

}
