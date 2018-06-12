package com.beheresoft.security.session;

import com.beheresoft.security.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author Aladi
 * @date 2018-06-12 11:14:48
 */
@Component
@Slf4j
public class CustomWebSessionManager extends DefaultWebSessionManager {

    private SystemConfig.Session sessionConfig;

    public CustomWebSessionManager(SystemConfig systemConfig, RedisSessionDao redisSessionDao, CustomWebSessionFactory sessionFactory) {
        this.sessionConfig = systemConfig.getSession();
        this.setSessionIdUrlRewritingEnabled(sessionConfig.getUrlSessionId());
        this.getSessionIdCookie().setName(sessionConfig.getCookieName());
        this.setGlobalSessionTimeout(sessionConfig.getTimeout());
        this.setSessionDAO(redisSessionDao);
        this.setSessionFactory(sessionFactory);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        Serializable sessionId = super.getSessionId(request, response);

        if (sessionId == null && request instanceof HttpServletRequest) {
            sessionId = ((HttpServletRequest) request).getHeader(sessionConfig.getHeaderName());
        }

        log.info("request session id {}", sessionId);

        return sessionId;
    }
}
