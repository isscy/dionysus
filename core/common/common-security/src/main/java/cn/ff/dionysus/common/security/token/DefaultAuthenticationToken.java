package cn.ff.dionysus.common.security.token;

import lombok.Data;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义AbstractAuthenticationToken
 *
 * @author fengfan 2020/3/17
 */
@Data
public class DefaultAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 110L;
    private Object username;
    private Object password;

    public DefaultAuthenticationToken() {
        super(null);
    }
    public DefaultAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.username = principal;
        this.password = credentials;
        super.setAuthenticated(true);
    }
    public DefaultAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.username = principal;
        this.password = credentials;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.password = null;
    }
}
