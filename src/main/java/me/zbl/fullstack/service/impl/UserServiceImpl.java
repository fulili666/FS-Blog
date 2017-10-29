package me.zbl.fullstack.service.impl;

import me.zbl.fullstack.consts.SessionConstants;
import me.zbl.fullstack.entity.User;
import me.zbl.fullstack.entity.vo.UserLoginForm;
import me.zbl.fullstack.entity.vo.UserRegisterForm;
import me.zbl.fullstack.framework.service.AbstractService;
import me.zbl.fullstack.mapper.UserMapper;
import me.zbl.fullstack.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper mUserMapper;

    @Override
    public User loginAuthentication(UserLoginForm loginForm) {
        List<User> userList = mUserMapper.select(new User().setUsername(loginForm.getUsername()));
        if (null != userList && userList.size() == 1) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public boolean registerUsernameCheckExist(UserRegisterForm registerForm) {
        return mUserMapper.select(new User().setUsername(registerForm.getUsername())).size() > 0;
    }

    @Override
    public void insertUser(User user) {
        mUserMapper.insert(user);
    }

    @Override
    public void joinSession(HttpServletRequest request, User user) {
        HttpSession requestSession = request.getSession(true);
        requestSession.setAttribute(SessionConstants.SESSION_CURRENT_USER, user);
    }
}