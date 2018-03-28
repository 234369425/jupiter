package com.beheresoft.security.service;

import com.beheresoft.security.pojo.*;
import com.beheresoft.security.repository.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Aladi on 2018/3/24.
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
