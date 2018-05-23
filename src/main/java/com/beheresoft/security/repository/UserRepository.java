package com.beheresoft.security.repository;

import com.beheresoft.security.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Aladi on 2018/3/12.
 * @author Aladi
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询用户
     * @param loginName
     * @return
     */
    @Query("select u from User u where u.loginName = :loginName ")
    User findOneByLoginName(@Param("loginName") String loginName);

}
