package cn.ff.dionysus.common.upms.service;

import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import cn.ff.dionysus.common.upms.mapper.SysOauthClientDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * _
 *
 * @author fengfan 2020/2/20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultClientDetailsService implements ClientDetailsService {

    private final SysOauthClientDetailsMapper sysOauthClientDetailsMapper;

    @Override
    public SysOauthClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        SysOauthClientDetails clientDetails = sysOauthClientDetailsMapper.getByClientId(clientId);
        if (clientDetails == null)
            throw new ClientRegistrationException("client不存在");
        return clientDetails;
    }
}
