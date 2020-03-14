package cn.ff.dionysus.common.basal.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class SysMenu {

    private String id;
    private String type;
    private String name;
    private String title;
    private String parentId;
    private int level;
    private String url;
    private String method;
    private String serverId;
    private int orderNum;
    private String perms;
    private String icon;
    private String createBy;
    private Date createTime;
    private String remark;

    //@TableField(exist = false)
    private String ofRoles;
    private Set<SysRole> requiredRoles;
    private List<SysMenu> children = new ArrayList<SysMenu>();

}
