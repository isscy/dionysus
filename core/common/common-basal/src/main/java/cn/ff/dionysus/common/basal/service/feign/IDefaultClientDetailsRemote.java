package cn.ff.dionysus.common.basal.service.feign;

import cn.ff.dionysus.common.basal.constant.ServiceNameConstant;
import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import cn.ff.dionysus.common.basal.service.fallback.DefaultClientDetailsRemoteFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程方法调用： 客户端
 * @author fengfan 2020/2/20
 */
//@FeignClient(contextId = "IDefaultClientDetailsRemote", value = ServiceNameConstant.SERVICE_UPMS, fallbackFactory = RemoteLogServiceFallbackFactory.class)
//@FeignClient(contextId = "iDefaultClientDetailsRemote", value = ServiceNameConstant.SERVICE_UPMS/*, fallbackFactory = DefaultClientDetailsRemoteFallbackFactory.class*/)
public interface IDefaultClientDetailsRemote extends ClientDetailsService {

    @Override
    @GetMapping("oauth2/client/{clientId}")
    /*SysOauthClientDetails*/ClientDetails loadClientByClientId(@PathVariable String clientId) throws ClientRegistrationException;



}
