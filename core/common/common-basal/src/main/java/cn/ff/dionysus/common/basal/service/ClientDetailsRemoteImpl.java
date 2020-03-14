package cn.ff.dionysus.common.basal.service;

import cn.ff.dionysus.common.basal.service.feign.IDefaultClientDetailsRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author fengfan 2020/2/25
 */
//@Service
public class ClientDetailsRemoteImpl implements ClientDetailsService {

    @Autowired
    private IDefaultClientDetailsRemote iDefaultClientDetailsRemote;


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return iDefaultClientDetailsRemote.loadClientByClientId(clientId);
    }
}
