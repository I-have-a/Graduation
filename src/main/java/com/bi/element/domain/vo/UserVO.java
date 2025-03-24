package com.bi.element.domain.vo;

import com.bi.element.annotation.UniqueEmail;
import com.bi.element.annotation.UniqueUsername;
import com.bi.element.domain.po.BaseBean;
import com.bi.element.domain.statusEnum.UserStatus;
import com.bi.element.domain.validation.Add;
import com.bi.element.domain.validation.Delete;
import com.bi.element.domain.validation.Login;
import com.bi.element.domain.validation.Update;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserVO extends BaseBean implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(groups = {Delete.class, Update.class})
    private Long id;

    @UniqueUsername
    @NotBlank(message = "账号不能为空", groups = {Add.class, Login.class})
    private String account;

    @Pattern(regexp = "^[A-Za-z\\d!@#$%^&*()_+]{6,20}$", message = "密码必须6到20位，且不能出现空格，不能含有中文字符，特殊字符可选（!@#$%^&*()_+）", groups = {Add.class})
    @NotBlank(message = "密码不能为空", groups = {Add.class, Login.class})
    private String password;

    @NotBlank(message = "用户名不能为空", groups = {Add.class})
    private String userName;

    @Email(message = "邮箱格式错误", groups = {Add.class, Update.class})
    @UniqueEmail(message = "邮箱重复", groups = {Add.class, Update.class})
    private String email;

    private String photo;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @Past
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthDay;

    private UserStatus status;

    private String loginIp;

    private Date loginDate;
}
