package com.beheresoft.security.session;

import com.beheresoft.security.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.web.util.SavedRequest;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Aladi
 * @date 2018-06-11 14:54:09
 */
@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    private final RMapCache<Serializable, CustomSession> sessionCache;
    private final long sessionTimeout;

    public RedisSessionDao(RedissonClient redissonClient, SystemConfig systemConfig) {
        SystemConfig.Session session = systemConfig.getSession();
        String prefix = session.getCacheKeyPrefix();
        sessionCache = redissonClient.getMapCache(prefix + "SESSION");
        sessionTimeout = session.getTimeout();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        Session session = sessionCache.get(serializable);
        if (session != null) {
            this.assignSessionId(session, serializable);
            return session;
        }
        return null;
    }

    @Override
    protected void assignSessionId(Session session, Serializable serializable) {
        ((CustomSession) session).setId(serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        this.sessionCache.removeAsync(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return this.sessionCache.readAllValues().stream()
                .map(Session.class::cast).collect(Collectors.toList());
    }

    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            log.error("{} can not get session id!", session);
            return;
        }
        Session oldSession = sessionCache.get(session.getId());
        if (oldSession != null) {
            Collection<Object> keys = oldSession.getAttributeKeys();
            Collection<Object> newKeys = session.getAttributeKeys();
            if (!keys.isEmpty() || !newKeys.isEmpty()) {
                Iterator<Object> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    if (!newKeys.contains(key)) {
                        session.setAttribute(key, oldSession.getAttribute(key));
                    }
                }
            }
        }
        session.setAttribute("shiroSavedRequest", null);
        sessionCache.put(session.getId(), (CustomSession) session, sessionTimeout, TimeUnit.MILLISECONDS);
    }

}
