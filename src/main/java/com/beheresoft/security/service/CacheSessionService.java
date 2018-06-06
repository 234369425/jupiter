package com.beheresoft.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author Aladi
 * @date 2018-06-06 23:29:20
 */
@Service
@Slf4j
public class CacheSessionService extends CachingSessionDAO {

    public CacheSessionService(CacheManager cacheManager) {
        this.setCacheManager(cacheManager);
    }


    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {
        Serializable sessionId = session.getId();
        if (sessionId == null) {
            return;
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        Session session = getCachedSession(serializable);
        if (session == null || session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {

        }
        return session;
    }
}
