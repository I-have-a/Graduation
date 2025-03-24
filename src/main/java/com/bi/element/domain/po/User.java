package com.bi.element.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bi.element.domain.statusEnum.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_user")
@Accessors(chain = true)
public class User extends BaseBean implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("user_name")
    private String userName;

    @TableField("email")
    private String email;

    @TableField("photo")
    private String photo;

    @TableField("phone")
    private String phone;

    @TableField("birth_day")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthDay;

    @TableField("status")
    private UserStatus status;

    @TableField(exist = false)
    private List<User> friends;

    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_date")
    private Date loginDate;

    /**
     * 角色对象
     */
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 角色ID
     */
    @TableField(exist = false)
    private Long roleId;
}
