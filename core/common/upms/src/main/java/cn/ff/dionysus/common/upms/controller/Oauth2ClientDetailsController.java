package cn.ff.dionysus.common.upms.controller;

import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import cn.ff.dionysus.common.upms.service.DefaultClientDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * oauth2 client
 *
 * @author fengfan 2020/2/24
 */
@RestController
@RequestMapping("oauth2/client")
@AllArgsConstructor
public class Oauth2ClientDetailsController {
    private DefaultClientDetailsService defaultClientDetailsService;


    /**
     * 通过clientId 获取一个 client
     */
    @GetMapping("/{clientId}")
    public ClientDetails getOneByClientId(@PathVariable String clientId) {
        SysOauthClientDetails detail = defaultClientDetailsService.loadClientByClientId(clientId);
        //return Rs.ok(detail);
        return detail;
    }
}
