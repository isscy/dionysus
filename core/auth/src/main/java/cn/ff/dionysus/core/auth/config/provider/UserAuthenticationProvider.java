package cn.ff.dionysus.core.auth.config.provider;

import cn.ff.dionysus.common.basal.constant.ClientType;
import cn.ff.dionysus.common.basal.entity.SysOauthClientDetails;
import cn.ff.dionysus.common.security.exception.BaseAuthenticationException;
import cn.ff.dionysus.common.security.exception.UserAccountException;
import cn.ff.dionysus.common.security.provider.AbstractUserDetailsAuthenticationProvider;
import cn.ff.dionysus.common.security.token.UserAuthenticationToken;
import cn.ff.dionysus.core.auth.service.BaseUserDetailsService;
import cn.ff.dionysus.core.auth.service.DefaultClientDetailsService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 通过账号密码登陆
 *
 * @author fengfan 2020/3/17
 */
@Component
@AllArgsConstructor
public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final BaseUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final DefaultClientDetailsService defaultClientDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthenticationToken authenticationToken = (UserAuthenticationToken) authentication;
        ClientType clientType = ClientType.instance(authenticationToken.getClientId());
        String username = authentication.getName();
        UserDetails user;
        try {
            user = this.retrieveUser(username, clientType);
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException var6) {
            throw var6;
        }
        this.additionalAuthenticationChecks(user, authentication);
        return this.createSuccessAuthentication(authentication, user);
    }

    @Deprecated
    @Override
    protected UserDetails retrieveUser(String username, Authentication authentication) throws BaseAuthenticationException {
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("账号或密码不正确");
        }
        if (user == null || !user.isEnabled())
            throw new UsernameNotFoundException("账号已被锁定,请联系管理员");
        return user;
    }

    protected UserDetails retrieveUser(String username, ClientType clientType) throws BaseAuthenticationException {
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username, clientType);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("账号或密码不正确");
        }
        if (user == null || !user.isEnabled())
            throw new UsernameNotFoundException("账号已被锁定,请联系管理员");
        return user;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails sysUser, Authentication authentication) throws BaseAuthenticationException {
        UserAuthenticationToken authenticationToken = (UserAuthenticationToken) authentication;
        /*String captchaKey = SecurityConstant.SECURITY_KEY_TYPE_IMAGE_CODE + authenticationToken.getSessionUUID();
         String captcha = RedisUtil.getAndDel(captchaKey);  TODO  check 验证码
       if (StringUtils.isBlank(authenticationToken.getImageCode()) || !authenticationToken.getImageCode().equalsIgnoreCase(captcha)) {
            throw new ImageCodeNotMatchException("验证码有误");
        }*/
        String encodedPassword = sysUser.getPassword();
        String rawPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserAccountException("账号或密码不正确");
        }
        if (StringUtils.isBlank(authenticationToken.getClientId()) || StringUtils.isBlank(authenticationToken.getClientSecret()))
            throw new UserAccountException("请求client不能为空");
        SysOauthClientDetails clientDetails = defaultClientDetailsService.loadClientByClientId(authenticationToken.getClientId());
        if (clientDetails != null && StringUtils.isNotBlank(authenticationToken.getClientSecret()) && passwordEncoder.matches(authenticationToken.getClientSecret(), clientDetails.getClientSecret())) {
            //clientId = authenticationToken.getClientId();
        } else {
            throw new UserAccountException("client_id或者client_secret错误！");
        }
    }

    @Override
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        UserAuthenticationToken result = new UserAuthenticationToken(user, authentication.getCredentials());
        //result.setDetails(authentication.getDetails());
        result.setDetailPlus(authentication);
        return result;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
