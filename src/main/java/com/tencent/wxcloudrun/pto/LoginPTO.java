package com.tencent.wxcloudrun.pto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "mbp_log_in")
public class LoginPTO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("USER_NAME")
    private String userName;
    @TableField("PHONE_NUMBER")
    private String phoneNumber;
    @TableField("PASSWORD")
    private String passWord;
    @TableField("IS_ADMINISTRATOR")
    private String isAdministrator;
}
