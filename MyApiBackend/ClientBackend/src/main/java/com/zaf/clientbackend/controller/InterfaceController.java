package com.zaf.clientbackend.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import com.zaf.apiclinetsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

import static com.zaf.apiclinetsdk.utils.APISignAndaut.getSign;


/**
 *
 * 1.  GET 接口
 * 2.  POST 接口（url传参）
 * 3.  POST接口（Restful）
 */
@RestController
@RequestMapping("/getName")
public class InterfaceController {
    @GetMapping("/")
    public String GetByName(String name) throws UnsupportedEncodingException {
        return "GetByName你的名字是" + name;
    }
    @PostMapping("/")
    public String PostByName(String name) {
        return "PostByName你的名字是" + name;
    }

    @PostMapping("/user")
    public String PostByUserName(@RequestBody User user , HttpServletRequest request) throws UnsupportedEncodingException {
        String accessKey = request.getHeader("accessKey");
        // 防止中文乱码
        String body = request.getHeader("body");
        String sign = request.getHeader("sign");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        boolean hasBlank = StrUtil.hasBlank(accessKey, body, sign, nonce, timestamp);
        // 判断是否有空
        if (hasBlank) {
            return "无权限1";
        }
        // TODO 使用accessKey去数据库查询secretKey
        // 假设查到的secret是abc 进行加密得到sign
        String secretKey="aaaa";
        String sign1 = getSign(body,secretKey);
        if (!sign1.equals(sign)) {
            return "无权限2";
        }
        // TODO 判断随机数nonce
        // 时间戳是否为数字
        if (!NumberUtil.isNumber(timestamp)) {
            return "无权限3";
        }
        // 五分钟内的请求有效
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 5 * 60 * 1000) {
            return "无权限4";
        }
        return "GetByUserName你的名字是" + user.getName();
    }






}
