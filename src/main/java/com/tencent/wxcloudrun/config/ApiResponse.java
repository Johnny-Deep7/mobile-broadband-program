package com.tencent.wxcloudrun.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

  private String msg;
  private Object data;
  public ApiResponse(){}
  private ApiResponse(String msg, Object data) {
    this.msg = msg;
    this.data = data;
  }
  
  public static ApiResponse ok() {
    return new ApiResponse("", new HashMap<>());
  }
  public static ApiResponse ok(Object data) {
    return new ApiResponse("",  data);
  }

}
