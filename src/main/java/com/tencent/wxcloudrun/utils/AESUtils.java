package com.tencent.wxcloudrun.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

@Slf4j
public class AESUtils {

    /**
     * key  加密密钥，长度为32位字符
     */
    private static final String KEY = "rwabcdefghijlmph";

    /**
     * 使用CBC模式，需要一个向量,增强算法的强度
     */
    private static final String INIT_VECTOR = "1234567890abcdef";

    public static AES aesCoder = null;

    static {
        aesCoder = new AES(Mode.CBC, Padding.ISO10126Padding, KEY.getBytes(StandardCharsets.UTF_8),
                INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
    }

    //加密
    public static String encode(String data) {

        try {

            return aesCoder.encryptBase64(data.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            return null;
        }
    }

    //解密
    public static String decode(String data){
        try{

            byte[] decrypt = aesCoder.decrypt(data);

            return new String(decrypt, StandardCharsets.UTF_8);

        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String s= "1jgs3y1IwpI9XXNjSV4pyg==";

        String s1 = Base64.encodeBase64String(s.getBytes(StandardCharsets.UTF_8));
        System.out.println(s1);
        String decrypt = AESUtils.decode(s);
        System.out.println(decrypt);
    }
}
