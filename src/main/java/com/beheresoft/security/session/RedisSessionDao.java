package com.beheresoft.security.session;

import com.beheresoft.security.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.SignatureException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author Aladi
 * @date 2018-06-11 14:54:09
 */
@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {

    private final RMapCache<String, Session> sessionCache;
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
        return sessionCache.get(serializable);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        this.sessionCache.removeAsync(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return this.sessionCache.readAllValues();
    }

    private void saveSession(Session session) {
        if (session == null || session.getId() == null) {
            log.error("{} can not get session id!", session);
            return;
        }
        sessionCache.put(session.getId().toString(), session, sessionTimeout, TimeUnit.MILLISECONDS);
    }

}
