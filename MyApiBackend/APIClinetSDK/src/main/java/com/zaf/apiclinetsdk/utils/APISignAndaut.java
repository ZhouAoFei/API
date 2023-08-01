package com.zaf.apiclinetsdk.utils;

import cn.hutool.crypto.digest.DigestUtil;


/**
 * 用户参数 	+	密钥	=>	签名生成算法（MD5,HMac,Sha1) 	=>	不可解密的值
 * 签名认证工具类
 * 1.用户参数 -> Map
 * 2.用户参数+密钥
 * 3.nonce随机数
 * 4.timestamp
 *
 */
public class APISignAndaut {
    /**
     * 使用参数和密钥加密后给到后端
     * 存放参数的的方法
     */

        public static String getSign(String body, String secretKey) {
            String content = body + "." + secretKey;
            return DigestUtil.md5Hex(content);
         }






}
