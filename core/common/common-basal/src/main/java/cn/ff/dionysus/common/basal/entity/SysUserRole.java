package cn.ff.dionysus.common.basal.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserRole {

    private String id;
    private String userId;
    private String roleId;
    private Date relTime;
}
