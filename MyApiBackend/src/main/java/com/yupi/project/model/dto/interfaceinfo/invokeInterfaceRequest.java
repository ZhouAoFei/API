package com.yupi.project.model.dto.interfaceinfo;

import lombok.Data;


/**
 * 用户测试调用接口接受参数
 *
 */

@Data
public class invokeInterfaceRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     */
    private String requestParams;
    /**
     *用户的ak和sk（签名密钥）
     */

}
