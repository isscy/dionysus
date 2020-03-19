package cn.ff.dionysus.common.security.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户登录名和密码登陆
 *
 * @author fengfan 2020/3/17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAuthenticationToken extends DefaultAuthenticationToken{
    private static final long serialVersionUID = -1111L;

    /**
     * 识别ID
     */
    private String sessionId;
    /**
     * 验证码
     */
    private String imageCode;
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

    public UserAuthenticationToken(UserDetails principal, Object credentials) {
        super(principal, credentials, principal.getAuthorities());
    }

    /**
     * 在Provider中new出新的token会丢失之前的  所以加上  add by ff at 2019-09-05
     */
    public void setDetailPlus(Authentication authentication){
        this.setDetails(authentication.getDetails());
        UserAuthenticationToken old = (UserAuthenticationToken)authentication;
        this.setClientId(old.getClientId());
        this.setClientSecret(old.getClientSecret());
        /*this.setPrincipal(old.getPrincipal());
        this.setCredentials(old.getCredentials());*/
    }
}
