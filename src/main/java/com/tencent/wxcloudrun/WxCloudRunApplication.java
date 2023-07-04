package com.tencent.wxcloudrun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.tencent.wxcloudrun.mapper.*"})
public class WxCloudRunApplication {  

  public static void main(String[] args) {
    SpringApplication.run(WxCloudRunApplication.class, args);
  }

  /**
   * 将加密工具类加入IOC容器中,便于加密
   * */
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}
