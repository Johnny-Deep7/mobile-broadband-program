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
    Integer id;
    @TableField("USER_NAME")
    String userName;
    @TableField("PHONE_NUMBER")
    String phoneNumber;
    @TableField("PASSWORD")
    String passWord;
    @TableField("IS_ADMINISTRATOR")
    String isAdministrator;
}
