package com.tencent.wxcloudrun.pto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginPTO {
    String phoneNumber;
    String passWord;
    @Builder.Default
    Boolean isAdmin = false;
}
