package com.yupi.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.entity.Interfaceinfo;
import com.yupi.project.model.enums.PostGenderEnum;
import com.yupi.project.model.enums.PostReviewStatusEnum;
import com.yupi.project.service.InterfaceinfoService;
import com.yupi.project.mapper.InterfaceinfoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author ZAF
* @description 针对表【interfaceinfo(接口信息)】的数据库操作Service实现
* @createDate 2023-07-29 11:43:46
*/
@Service
public class InterfaceinfoServiceImpl extends ServiceImpl<InterfaceinfoMapper, Interfaceinfo>
    implements InterfaceinfoService{

    @Override
    public void validInterfaceinfo(Interfaceinfo interfaceinfo, boolean add) {
        if (interfaceinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //前端已经校验
        String name = interfaceinfo.getName();
        //自动生成可为空
        Date createTime = interfaceinfo.getCreateTime();
        //不为空即可且必须是请求类型
        String method = interfaceinfo.getMethod();
        System.out.println(method);
        String requestHeader = interfaceinfo.getRequestHeader();
        String responseHeader = interfaceinfo.getResponseHeader();
        String url = interfaceinfo.getUrl();
        //非空为整数即可
        Long userid = interfaceinfo.getUserId();
        // 创建时，所有参数必须非空
        if (add) {
            if (
                    !(method.equals("get") ) && !(method.equals("post"))
            ){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求接口类型参数错误！");
            }
            if (StringUtils.isNotBlank(requestHeader) && requestHeader.length() > 8192) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
            }
            if (StringUtils.isNotBlank(responseHeader) && responseHeader.length() > 8192) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
            }
            if (StringUtils.isNotBlank(url) && url.length() > 8192) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
            }

        }

    }
}




