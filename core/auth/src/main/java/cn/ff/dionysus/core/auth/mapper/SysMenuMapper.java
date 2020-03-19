package cn.ff.dionysus.core.auth.mapper;

import cn.ff.dionysus.common.basal.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 菜单管理
 *
 * @author fengfan 2020/3/17
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {





    /**
     * 获取一个用户的资源权限 - menu.url
     */
    List<SysMenu> getOneUserMenuList(Integer userId);
}
