package com.zaf.apiclinetsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.zaf.apiclinetsdk.model.User;
import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.zaf.apiclinetsdk.utils.APISignAndaut.getSign;
@Data
public class UserClient {
    private String accessKey;
    private String secretKey;

    public UserClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * @param
     * @return get方式请求
     */

    private Map<String, String> getHeaders(String body) throws UnsupportedEncodingException {
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        header.put("sign", getSign(URLEncoder.encode(body, StandardCharsets.UTF_8.name()), secretKey));
        System.out.println(body);
        // 防止中文乱码
        header.put("body", URLEncoder.encode(body, StandardCharsets.UTF_8.name()));
        header.put("nonce", RandomUtil.randomNumbers(5));
        header.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return header;
    }
    public String GetByName(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result= HttpUtil.get("http://localhost:8999/getName/", paramMap);
        System.out.println(result);
        return result;
    }

    public String PostByName(String name) {
        // 可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String post = HttpUtil.post("http://localhost:8999/getName/", paramMap);
        System.out.println(post);
        return post;
    }

    public String PostByUserName(User user) throws UnsupportedEncodingException {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.post("http://localhost:8999/getName/user/")
                .body(json)
                .addHeaders(getHeaders(json))
                .execute();
        if (response.isOk()) {
            System.out.println(response);
            return response.body();
        }
        return "fail";
    }
}
