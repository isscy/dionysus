package cn.ff.dionysus.core.auth.handler;

import cn.ff.dionysus.common.basal.utils.Rs;
import cn.ff.dionysus.common.basal.utils.WebUtil;
import cn.ff.dionysus.common.security.token.UserAuthenticationToken;
import cn.ff.dionysus.core.auth.service.DefaultClientDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功处理 返回oauth token
 *
 * @author fengfan 2019-07-26
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final AuthorizationServerTokenServices sysTokenServices;
    private final DefaultClientDetailsService defaultClientDetailsService;
    /**
     * 认证成功后生成oAuth2AccessToken
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try { //TODO : clientId 应该放在header中传而不是等于grantType
            // ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            String grantType = request.getParameter(OAuth2Utils.GRANT_TYPE);
            String clientId = grantType;
            if (authentication instanceof UserAuthenticationToken){
                /*UserNewAuthenticationToken token = (UserNewAuthenticationToken)authentication;
                SysOauthClientDetails clientDetails= (SysOauthClientDetails)sysClientDetailsService.loadClientByClientId(token.getClientId());
                if (clientDetails != null && StringUtils.isNotBlank(token.getClientSecret()) && token.getClientSecret().equals(clientDetails.getClientSecret())){
                    clientId = token.getClientId();
                }else {
                    throw new DefaultOauth2Exception("client_id或者client_secret错误！");
                }*/
                clientId = ((UserAuthenticationToken) authentication).getClientId();
            }/*else if (authentication instanceof PhoneAuthenticationToken){
                clientId = ((PhoneAuthenticationToken) authentication).getClientId();
            }*/

            // 简化
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(clientId);
            TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, Sets.newHashSet(), grantType);
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            // TODO
           /* OAuth2AccessToken accessToken = defaultAuthorizationServerTokenServices.getAccessToken(oAuth2Authentication);
            OAuth2AccessToken oAuth2AccessToken = null;
            if (accessToken != null) {
                oAuth2AccessToken = defaultAuthorizationServerTokenServices.refreshAccessToken(accessToken.getRefreshToken().getValue(), tokenRequest);
            } else {
                oAuth2AccessToken = defaultAuthorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            }*/
            OAuth2AccessToken oAuth2AccessToken = sysTokenServices.createAccessToken(oAuth2Authentication);
            log.info("获取token 成功：{}", oAuth2AccessToken.getValue());
            response.setCharacterEncoding(WebUtil.CharsetUtil.UTF_8);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            PrintWriter printWriter = response.getWriter();
            //printWriter.append(objectMapper.writeValueAsString(oAuth2AccessToken)); // 替换成下面这个
            printWriter.append(objectMapper.writeValueAsString(Rs.ok(oAuth2AccessToken)));
        } catch (IOException e) {
            throw new BadCredentialsException( "Failed to decode basic authentication token");
        }

    }

}
