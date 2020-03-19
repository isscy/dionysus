package cn.ff.dionysus.core.auth.service;

import cn.ff.dionysus.common.basal.constant.CacheConstant;
import cn.ff.dionysus.common.basal.constant.ClientType;
import cn.ff.dionysus.common.basal.constant.SecurityConstant;
import cn.ff.dionysus.common.basal.entity.*;
import cn.ff.dionysus.common.basal.service.feign.ISysUserRemote;
import cn.ff.dionysus.core.auth.mapper.SysMenuMapper;
import cn.ff.dionysus.core.auth.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取用户信息 封装oauth2权限
 * TODO ****** oauth2 sso单点登录 前端跨域问题 ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 */
@Service("userDetailsService")
@AllArgsConstructor
public class BaseUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserDetailsService.class);
    private final CacheManager cacheManager;
    private final SysUserMapper sysUserMapper;
    private final SysMenuMapper sysMenuMapper;
    //private final ISysUserRemote iSysUserRemote;



    public UserDetails loadUserByUsername(String username, ClientType clientType) throws UsernameNotFoundException {
        if (username == null || StringUtils.isBlank(username.trim()))
            throw new UsernameNotFoundException(username + " 不能为空");
        Cache cache = cacheManager.getCache(CacheConstant.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (BaseUserDetail) cache.get(username).get();
        }
        SysUser sysUser = sysUserMapper.getByUserName(username, true);
        if (sysUser == null) {
            LOGGER.debug("找不到该用户 ：{}", username);
            throw new UsernameNotFoundException(username + " 该用户未注册或已删除");

        }
        return getUserDetails(sysUser, clientType);
        /*List<SysRole> roles = sysUserMapper.getOneUserAllRoles(sysUser.getId());
        if (roles == null) {
            LOGGER.debug(username + "获取角色为空！");
            roles = new ArrayList<>();
            // TODO 缓存用户的menu 菜单 到redis
        }
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUserName(),
                sysUser.getPassword(), isActive(sysUser.getStatus()), true, true, true, convertToAuthorities(sysUser, roles));
        BaseUserDetail baseUserDetail = new BaseUserDetail(sysUser, user);
        cache.put(username, baseUserDetail);
        return baseUserDetail;*/
        // TODO 支持多种登陆方式
        /*if (username.contains(":")) {
            String[] parameter = username.split(":");
            if (SecurityConstant.LOGIN_TYPE_PHONE.equals(parameter[0])) {
                sysUser = sysUserMapper.getByPhone(parameter[1]);
            } else { // 以后可以加上 扫码登录
                sysUser = sysUserMapper.getByUsername(parameter[1]);
            }
        } else { // 不包含就 直接认为是用户名密码登录
            sysUser = sysUserMapper.getByUsername(username);
        }*/


    }

    /**
     * 短信验证码 登录
     */
    /*@SneakyThrows
    public UserDetails loadUserByPhone(String phone) {
        SysUser sysUser = sysUserMapper.getByPhone(phone);
        if (sysUser == null) {
            throw new UsernameNotFoundException("手机号未注册");
        }
        return getUserDetails(sysUser, clientType);
    }*/

    /**
     * 获取用户信息 构造 UserDetails
     */
    private BaseUserDetail getUserDetails(SysUser sysUser, ClientType clientType) {
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(getUserPermissions(sysUser.getId(), clientType).toArray(new String[0]));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUserName(),
                sysUser.getPassword(), isActive(sysUser.getStatus()), true, true, true, authorities/*convertToAuthorities(sysUser, roles)*/);
        return new BaseUserDetail(sysUser, user);
    }

    /**
     * 获取用户的权限信息
     */
    private Set<String> getUserPermissions(int userId, ClientType clientType) {
        List<String> urlList = null;
        switch (clientType) {
            case WEB:
                List<SysMenu> menuList = null;
                if (userId == SecurityConstant.SUPER_ADMIN_ID) {
                    menuList = sysMenuMapper.selectList(Wrappers.emptyWrapper());
                } else {
                    menuList = sysMenuMapper.getOneUserMenuList(userId);
                }
                urlList = menuList.stream().map(SysMenu::getUrl).collect(Collectors.toList());
                break;
            case APP:
            case IOS:
            case ANDROID:
                /*List<SysAppResource> resources = null;
                if (userId == SecurityConstant.SUPER_ADMIN_ID) {
                    resources = sysAppResourceMapper.selectList(new QueryWrapper<SysAppResource>().lambda().eq(SysAppResource::getDelFlag, CommonConstant.DEL_NO));
                } else {
                    resources = sysAppResourceMapper.listUserAppPermission(userId);
                }
                permsList = Optional.ofNullable(resources).orElse(new ArrayList<>()).stream().map(SysAppResource::getPerms).collect(Collectors.toList());*/
                break;
            default:
                throw new RuntimeException("获取权限错误！ 无法获取来源app端或者app端非法");
        }
        /*Set<String> permsSet = permsList.stream().flatMap((perms) -> {
            if (StringUtils.isBlank(perms))
                return null;
            return Arrays.stream(perms.trim().split(","));
        }).collect(Collectors.toSet());*/
        return new HashSet<>(Optional.ofNullable(urlList).orElse(new ArrayList<>()));
    }

    private List<GrantedAuthority> convertToAuthorities(SysUser user, List<SysRole> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 清除 Redis 中用户的角色
        //redisUtil.hdel(RedisConstant.KEY_USER_ROLES, user.getId());
        roles.stream().filter(e -> e != null).forEach(e -> { // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getCode());
            authorities.add(authority);
        });
        //存储角色到redis
        //redisUtil.hset(RedisConstant.KEY_USER_ROLES, user.getId(), roles);
        return authorities;
    }

    /*private void saveMenus(SysUser user, List<SysMenu> menus) {

        redisUtil.hset(RedisConstant.KEY_USER_MENUS, user.getId(), menus);
    }*/

    private boolean isActive(String status) {
        return "1".equals(status);
    }



    @Override
    @Deprecated
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || StringUtils.isBlank(username.trim()))
            throw new UsernameNotFoundException(username + " 不能为空");
        Cache cache = cacheManager.getCache(CacheConstant.USER_DETAILS);
        if (cache != null && cache.get(username) != null) {
            return (BaseUserDetail) cache.get(username).get();
        }
        SysUser sysUser = sysUserMapper.getByUserName(username, true);
        if (sysUser == null) {
            LOGGER.debug("找不到该用户 ：{}", username);
            throw new UsernameNotFoundException(username + " 该用户未注册或已删除");

        }
        List<SysRole> roles = sysUserMapper.getOneUserAllRoles(sysUser.getId());
        if (roles == null) {
            LOGGER.debug(username + "获取角色为空！");
            roles = new ArrayList<>();
            // TODO 缓存用户的menu 菜单 到redis
        }
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(sysUser.getUserName(),
                sysUser.getPassword(), isActive(sysUser.getStatus()), true, true, true, convertToAuthorities(sysUser, roles));
        BaseUserDetail baseUserDetail = new BaseUserDetail(sysUser, user);
        cache.put(username, baseUserDetail);
        return baseUserDetail;

    }

}
