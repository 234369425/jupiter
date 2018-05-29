package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aladi on 2018/3/12.
 *
 * @author Aladi
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名和AppName查询用户
     *
     * @param appName   应用名
     * @param loginName 登录名
     * @return User
     */
    User findUserByAppNameAndLoginName(String appName, String loginName);

}
