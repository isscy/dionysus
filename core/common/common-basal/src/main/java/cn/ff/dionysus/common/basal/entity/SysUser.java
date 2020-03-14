package cn.ff.dionysus.common.basal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户表
 */
@Data
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String nickName;
    private String phone;
    private Date createTime;
    private String type;
    private String status;
    private String delFlag;

    /**
     * 用户拥有的角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;

    /**
     * 用户拥有的角色，逗号隔开的字符串
     */
    @TableField(exist = false)
    private String rolesString;
    /**
     * 是否为部门管理员
     */
    @TableField(exist = false)
    private String ofDeptRuler;

}
