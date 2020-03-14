package cn.ff.dionysus.common.basal.entity;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

@Data
public class SysOauthClientDetails implements ClientDetails {

    private String clientId;

    private Set<String> resourceIds;

    private boolean secretRequired;

    private String clientSecret;

    private boolean scoped;

    private String scope;

    //private Set<String> authorizedGrantTypes;

    private String authorizedGrantType;

    private Set<String> registeredRedirectUri;


    //private Collection<GrantedAuthority> authorities;

    private String authority;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private boolean autoApprove;

    private Map<String, Object> additionalInformation;

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes(){
        if (StringUtils.isBlank(authorizedGrantType))
            return new HashSet<>();
        String[] arr = authorizedGrantType.split(",");
        return new HashSet<String>(Arrays.asList(arr));
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        if (StringUtils.isBlank(authority))
            return new ArrayList<>();
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
    @Override
    public Set<String> getScope(){
        if (StringUtils.isBlank(scope))
            return null;
        String[] arr = scope.split(",");
        return new HashSet<String>(Arrays.asList(arr));
    }
}
