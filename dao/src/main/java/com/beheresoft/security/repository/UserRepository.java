package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Aladi on 2018/3/12.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.loginName = ? ")
    User findOneByLoginName(String loginName);

}
