package com.beheresoft.security.service;

import com.beheresoft.security.pojo.*;
import com.beheresoft.security.repository.*;
import org.springframework.stereotype.Service;

/**
 * Created by Aladi on 2018/3/24.
 * @author Aladi
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordHelper passwordHelper;

    public UserService(UserRepository userRepository,
                       PasswordHelper passwordHelper) {
        this.userRepository = userRepository;
        this.passwordHelper = passwordHelper;
    }

    public User findByLoginName(String loginName) {
        return this.userRepository.findOneByLoginName(loginName);
    }

    public User create(User u) {
        passwordHelper.encryptPassword(u);
        return userRepository.save(u);
    }

}
