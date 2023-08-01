package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.project.annotation.AuthCheck;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.DeleteRequest;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.constant.CommonConstant;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.model.dto.interfaceinfo.InterfaceinfoAddRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceinfoQueryRequest;
import com.yupi.project.model.dto.interfaceinfo.InterfaceinfoUpdateRequest;
import com.yupi.project.model.dto.common.idRequest;
import com.yupi.project.model.dto.interfaceinfo.invokeInterfaceRequest;
import com.yupi.project.model.entity.Interfaceinfo;
import com.yupi.project.model.entity.User;
import com.yupi.project.model.enums.InterfaceInfoStatusEnum;
import com.yupi.project.service.InterfaceinfoService;
import com.yupi.project.service.UserService;
import com.zaf.apiclinetsdk.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 接口
 *
 * @author
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceinfoController {

    @Resource
    private InterfaceinfoService interfaceinfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceinfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceinfo(@RequestBody InterfaceinfoAddRequest interfaceinfoAddRequest, HttpServletRequest request) {
        if (interfaceinfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = new Interfaceinfo();
        BeanUtils.copyProperties(interfaceinfoAddRequest, interfaceinfo);
        // 校验
        interfaceinfoService.validInterfaceinfo(interfaceinfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceinfo.setUserId(loginUser.getId());
        boolean result = interfaceinfoService.save(interfaceinfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceinfoId = interfaceinfo.getId();
        return ResultUtils.success(newInterfaceinfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceinfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceinfo = interfaceinfoService.getById(id);
        if (oldInterfaceinfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceinfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceinfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceinfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceinfo(@RequestBody InterfaceinfoUpdateRequest interfaceinfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceinfoUpdateRequest == null || interfaceinfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = new Interfaceinfo();
        BeanUtils.copyProperties(interfaceinfoUpdateRequest, interfaceinfo);
        // 参数校验
        interfaceinfoService.validInterfaceinfo(interfaceinfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceinfoUpdateRequest.getId();
        // 判断是否存在
        Interfaceinfo oldInterfaceinfo = interfaceinfoService.getById(id);
        if (oldInterfaceinfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceinfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Interfaceinfo> getInterfaceinfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = interfaceinfoService.getById(id);
        return ResultUtils.success(interfaceinfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceinfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<Interfaceinfo>> listInterfaceinfo(InterfaceinfoQueryRequest interfaceinfoQueryRequest) {
        Interfaceinfo interfaceinfoQuery = new Interfaceinfo();
        if (interfaceinfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceinfoQueryRequest, interfaceinfoQuery);
        }
        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>(interfaceinfoQuery);
        List<Interfaceinfo> interfaceinfoList = interfaceinfoService.list(queryWrapper);
        return ResultUtils.success(interfaceinfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceinfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<Interfaceinfo>> listInterfaceinfoByPage(InterfaceinfoQueryRequest interfaceinfoQueryRequest, HttpServletRequest request) {
        if (interfaceinfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfoQuery = new Interfaceinfo();
        BeanUtils.copyProperties(interfaceinfoQueryRequest, interfaceinfoQuery);
        long current = interfaceinfoQueryRequest.getCurrent();
        long size = interfaceinfoQueryRequest.getPageSize();
        String sortField = interfaceinfoQueryRequest.getSortField();
        String sortOrder = interfaceinfoQueryRequest.getSortOrder();
        String description = interfaceinfoQuery.getDescription();
        // description支持模糊搜索
        interfaceinfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Interfaceinfo> queryWrapper = new QueryWrapper<>(interfaceinfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<Interfaceinfo> interfaceinfoPage = interfaceinfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceinfoPage);
    }

    // endregion

    /**
     * 根据 id 上线接口
     *上线
     * @param idRequest
     * @return
     */
    @Autowired
    private UserClient userClient;
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")//仅管理员可操作
    public BaseResponse<Boolean> onlineInterfaceInfoUsingPOST(@RequestBody idRequest idRequest) {
        if (idRequest.getId()<=0 ||idRequest==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = interfaceinfoService.getById(idRequest.getId());
        //判断接口是否存在
        if (interfaceinfo==null ||interfaceinfo.getId()<=0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在！");
        }
        //判断接口是否已经上线
        if (interfaceinfo.getStatus() == 1) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"接口已经开启！");
        }
        /*
        * todo 判断接口是否可以进行调用
        * 先使用已经写死的进行调用
        * */
        //管理员因为在配置文件中将ak和sk写死在接口调用模拟客户端了，所以可以直接进行调用
        com.zaf.apiclinetsdk.model.User user = new com.zaf.apiclinetsdk.model.User();
        user.setName("周奥飞");
        String s =null;
        try {
            s = userClient.PostByUserName(user);
        } catch (UnsupportedEncodingException e) {
        }

        if(StringUtils.isEmpty(s)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口异常不可调用！");
        }
        interfaceinfo.setStatus(InterfaceInfoStatusEnum.FEMALE.getValue());
        boolean b = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 下线接口
     *下线
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
   @AuthCheck(mustRole = "admin")//仅管理员可操作
    public BaseResponse<Boolean> offlineInterfaceInfoUsingPOST(@RequestBody idRequest idRequest) {
        if (idRequest.getId()<=0 ||idRequest==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Interfaceinfo interfaceinfo = interfaceinfoService.getById(idRequest.getId());
        //判断接口是否存在
        if (interfaceinfo==null ||interfaceinfo.getId()<=0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在！");
        }
        //判断接口是否已经上线
        if (interfaceinfo.getStatus() == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"接口已经下线！");
        }


//        /*
//         * 判断接口是否可以进行调用
//         * 先使用已经写死的进行调用
//         * */
//        //管理员因为在配置文件中将ak和sk写死在接口调用模拟客户端了，所以可以直接进行调用
//        com.zaf.apiclinetsdk.model.User user = new com.zaf.apiclinetsdk.model.User();
//        user.setName("周奥飞");
//        String s =null;
//        try {
//            s = userClient.PostByUserName(user);
//        } catch (UnsupportedEncodingException e) {
//        }
//
//        if(StringUtils.isEmpty(s)){
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口异常不可调用！");
//        }


        interfaceinfo.setStatus(InterfaceInfoStatusEnum.MALE.getValue());
        boolean b = interfaceinfoService.updateById(interfaceinfo);
        return ResultUtils.success(b);
    }

    /**
     * 在线调用接口
     *
     * @param invokeInterfaceRequest 携带id、请求参数
     * @return data
     */
    @PostMapping("/invoke")
    public BaseResponse<String> invokeInterfaceInfoUsingPOST(@RequestBody invokeInterfaceRequest invokeInterfaceRequest,HttpServletRequest request) {
        Interfaceinfo interfaceinfo = interfaceinfoService.getById(invokeInterfaceRequest.getId());
        //判断接口是否存在
        if (interfaceinfo==null ||interfaceinfo.getId()<=0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在！");
        }
        //判断接口是否已经上线
        if (interfaceinfo.getStatus() == 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"接口已经下线！");
        }
        /*
         * 判断接口是否可以进行调用
         * 先使用已经写死的进行调用
         * */
        //这里不在是管理员，所以要用户输入自己的ak和sk
        com.zaf.apiclinetsdk.model.User user = new com.zaf.apiclinetsdk.model.User();
        user.setName(invokeInterfaceRequest.getRequestParams());
        User loginUser = userService.getLoginUser(request);
        String ak=loginUser.getAccessKey();
        String sk=loginUser.getSecretKey();
        UserClient userClient = new UserClient(ak,sk);
        String s =null;
        try {
            s = userClient.PostByUserName(user);
        } catch (UnsupportedEncodingException e) {
        }

        if(StringUtils.isEmpty(s)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"接口异常不可调用！");
        }
        return ResultUtils.success(s);
    }

}
