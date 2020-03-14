package cn.ff.dionysus.common.basal.service.fallback;

import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import cn.ff.dionysus.common.basal.service.feign.IDefaultClientDetailsRemote;
import feign.hystrix.FallbackFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

/**
 * _
 *
 * @author fengfan 2020/2/24
 */
public class DefaultClientDetailsRemoteFallbackFactory implements FallbackFactory<IDefaultClientDetailsRemote> {
    @Override
    public IDefaultClientDetailsRemote create(Throwable throwable) {
        DefaultClientDetailsRemoteFallback fallback = new DefaultClientDetailsRemoteFallback();
        fallback.setCause(throwable);
        return fallback;
    }
}



@Slf4j
//@Component
class DefaultClientDetailsRemoteFallback implements IDefaultClientDetailsRemote {
    @Setter
    private Throwable cause;
    @Override
    public /*SysOauthClientDetails*/ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.error("[feignCallError] 获取Oauth2Client错误: ", cause);
        return null;
    }
}

