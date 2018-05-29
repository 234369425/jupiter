package com.beheresoft.security.service;

import com.beheresoft.security.pojo.*;
import com.beheresoft.security.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by Aladi on 2018/3/24.
 *
 * @author Aladi
 */
@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordHelper passwordHelper;

    public UserService(UserRepository userRepository,
                       PasswordHelper passwordHelper) {
        this.userRepository = userRepository;
        this.passwordHelper = passwordHelper;
    }

    public User findUser(String appName, String loginName) {
        return this.userRepository.findUserByAppNameAndLoginName(appName, loginName);
    }

    /**
     * create User
     *
     * @param u user
     * @return user
     */
    public User create(User u) {
        passwordHelper.encryptPassword(u);
        return userRepository.save(u);
    }

    public void lock(User u) {
        User findUser = userRepository.getOne(u.getUserId());
        findUser.setLocked(u.getLocked());
        userRepository.save(findUser);
    }

}
