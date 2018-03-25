package com.beheresoft.security.service;

import com.beheresoft.security.pojo.User;
import com.beheresoft.security.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Aladi on 2018/3/24.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginName(String loginName) {
        return this.userRepository.findOneByLoginName(loginName);
    }


}
