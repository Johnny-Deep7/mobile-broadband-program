package com.tencent.wxcloudrun.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResponse {
    private String msg;
    private String isAdmin;
}
