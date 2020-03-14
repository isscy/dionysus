package cn.ff.dionysus.common.basal.entity;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * 包装org.springframework.security.core.userdetails.User类 以及 包含 SysUser 用于生成 jwt 的用户信息
 *
 * @author fengfan 2020-03-11
 */
public class BaseUserDetail implements UserDetails, CredentialsContainer, Serializable {

    private final SysUser sysUser;
    private final User user;

    public BaseUserDetail(SysUser sysUser, User user) {
        this.sysUser = sysUser;
        this.user = user;
    }


    /**
     * 清空密码
     */
    @Override
    public void eraseCredentials() {
        user.eraseCredentials();
        if (sysUser != null) {
            sysUser.setPassword(null);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public SysUser getSysUser() {
        return sysUser;
    }

}
