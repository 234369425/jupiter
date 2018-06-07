package com.beheresoft.security.util;

import com.beheresoft.security.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author Aladi
 * @date 2018-06-07 17:49:54
 */
public class AppUtils {

    public static String getCurrentUserAppName(){
        Subject subject = SecurityUtils.getSubject();
        User u = (User) subject.getPrincipal();
        return u.getAppName();
    }

}
