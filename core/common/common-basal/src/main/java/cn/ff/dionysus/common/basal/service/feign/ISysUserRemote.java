package cn.ff.dionysus.common.basal.service.feign;

import cn.ff.dionysus.common.basal.constant.ServiceNameConstant;
import cn.ff.dionysus.common.basal.entity.R;
import cn.ff.dionysus.common.basal.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 获取用户信息
 *
 * @author fengfan 2020/3/11
 */
//@FeignClient(contextId = "iSysUserRemote", value = ServiceNameConstant.SERVICE_UPMS/*, fallbackFactory = DefaultClientDetailsRemoteFallbackFactory.class*/)
public interface ISysUserRemote {


    /**
     * 通过用户ID获取用户信息 -> 包含 用户、角色
     */
    @GetMapping("user/info/{id}")
    R<SysUser> getUserById(@PathVariable String id);
    /**
     * 通过用户名获取用户信息 -> 包含 用户、角色
     */
    @GetMapping("user/info/username/{userName}")
    R<SysUser> getUserByUserName(@PathVariable String userName);

    /**
     * 通过手机号获取用户信息 -> 包含 用户、角色
     */
    @GetMapping("user/info/phone/{phone}")
    R<SysUser> getUserByPhone(@PathVariable String phone);


}
