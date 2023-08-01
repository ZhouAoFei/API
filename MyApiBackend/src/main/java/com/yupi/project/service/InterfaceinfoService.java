package com.yupi.project.service;

import com.yupi.project.model.entity.Interfaceinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ZAF
* @description 针对表【interfaceinfo(接口信息)】的数据库操作Service
* @createDate 2023-07-29 11:43:46
*/
public interface InterfaceinfoService extends IService<Interfaceinfo> {

    void validInterfaceinfo(Interfaceinfo interfaceinfo, boolean b);
}
