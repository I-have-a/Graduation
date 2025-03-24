package com.bi.element.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bi.element.config.AuthenticationContextHolder;
import com.bi.element.domain.po.LoginUser;
import com.bi.element.domain.po.User;
import com.bi.element.domain.vo.UserVO;
import com.bi.element.exception.ServiceException;
import com.bi.element.exception.user.UserPasswordNotMatchException;
import com.bi.element.mapper.UserMapper;
import com.bi.element.service.TokenService;
import com.bi.element.service.UserService;
import com.bi.element.utils.DateUtils;
import com.bi.element.utils.SecurityUtils;
import com.bi.element.utils.ip.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Override
    public String login(UserVO user, HttpServletRequest request) {
        //AuthenticationManager authenticate进行用户认证
        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new UserPasswordNotMatchException();
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        return tokenService.createToken(loginUser);
    }

    @Transactional
    @Override
    public Boolean signup(UserVO user) {
        User userPo = new User();
        BeanUtil.copyProperties(user, userPo);
        userPo.setPassword(passwordEncoder.encode(userPo.getPassword()));
        return userMapper.insert(userPo) != 0;
    }

    @Override
    public Boolean updateInfo(UserVO user) {
        User userPO = new User();
        BeanUtil.copyProperties(user, userPO);
        return userMapper.updateById(userPO) == 1;
    }

    @Override
    public Boolean logoff(Long id) {
        return userMapper.updateFlag(id) == 1;
    }

    @Transactional
    @Override
    public Boolean updatePassword(String priorPassword, Long id) {
        Pattern p = Pattern.compile("^[A-Za-z\\d!@#$%^&*()_+]{6,20}$");
        Matcher matcher = p.matcher(priorPassword);
        if (!matcher.find()) {
            throw new ServiceException("密码必须6到20位，且不能出现空格，不能含有中文字符，特殊字符可选（!@#$%^&*()_+）");
        }
        User user1 = new User();
        user1.setPassword(SecurityUtils.encryptPassword(priorPassword));
        user1.setId(id);
        return userMapper.updateById(user1) != 0;
    }

    @Override
    public Integer deleteFriends(Long currentId, List<Integer> ids) {
        return userMapper.deleteUU(currentId, ids);
    }

    @Override
    public List<User> findFriend(String account, String nickname) {
        User user = new User();
        user.setAccount(account);
        user.setUserName(nickname);
        return userMapper.getSurvivalUserList(user);
    }

    @Override
    public List<User> findUser(String account, String nickname) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getAccount, account).or().like(User::getUserName, nickname);
        return userMapper.selectList(wrapper);
    }

    public void recordLoginInfo(Long userId) {
        UserVO user = new UserVO();
        user.setId(userId);
        user.setLoginIp(IpUtils.getIpAddr());
        user.setLoginDate(DateUtils.getNowDate());
        updateInfo(user);
    }
}
