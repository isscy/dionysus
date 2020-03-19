package cn.ff.dionysus.core.auth.handler;

import cn.ff.dionysus.common.basal.constant.ResultEnum;
import cn.ff.dionysus.common.basal.entity.R;
import cn.ff.dionysus.common.basal.utils.Rs;
import cn.ff.dionysus.common.basal.utils.WebUtil;
import cn.ff.dionysus.common.security.exception.BaseAuthenticationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆失败处理
 */
@Component
@Slf4j
public class LoginAuthenticationFailedHandler implements AuthenticationFailureHandler {

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        R r = Rs.fail(ResultEnum.CLIENT_ERROR);
        if (!(exception instanceof BaseAuthenticationException)) {
            r.setMessage(exception.getMessage());
        }else {
            BaseAuthenticationException auth2Exception = (BaseAuthenticationException) exception;

            r.setMessage(auth2Exception.getErrorCode() + auth2Exception.getMessage());
        }
        WebUtil.writeJson(response, r, HttpStatus.UNAUTHORIZED.value());
    }

}
