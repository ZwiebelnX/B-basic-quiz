package com.thoughtworks.gtb.csc.basicquiz;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// GTB: - 忘记提交 ./gradle 文件夹，导致clone 代码后，无法构建项目
@SpringBootApplication
public class BasicquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicquizApplication.class, args);
	}

}
