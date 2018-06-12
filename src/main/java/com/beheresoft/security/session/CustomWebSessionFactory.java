package com.beheresoft.security.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * @author Aladi
 */
@Component
public class CustomWebSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext sessionContext) {
        if (sessionContext != null) {
            String host = sessionContext.getHost();
            if (host != null) {
                return new CustomSession(host);
            }
        }
        return new CustomSession();
    }

}
