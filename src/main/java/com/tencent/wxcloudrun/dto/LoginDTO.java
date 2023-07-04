package com.tencent.wxcloudrun.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    private Integer id;
    private String userName;
    private String phoneNumber;
    private String passWord;
    private String isAdministrator;
}
