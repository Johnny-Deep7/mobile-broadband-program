package com.tencent.wxcloudrun.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {
    private Integer code;
    private String msg;
    private Boolean isFirstLogin;
    private String isAdmin;
}
