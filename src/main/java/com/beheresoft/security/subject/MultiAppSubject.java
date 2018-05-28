package com.beheresoft.security.subject;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Aladi
 */
public class MultiAppSubject extends WebDelegatingSubject {

    public MultiAppSubject(PrincipalCollection principals, boolean authenticated, String host,
                           Session session, ServletRequest request, ServletResponse response,
                           SecurityManager securityManager) {
        super(principals, authenticated, host, session, request, response, securityManager);
    }

    @Override
    public Session getSession() {
        SessionContext sessionContext = createSessionContext();
        Session session = this.securityManager.start(sessionContext);
        String appId = this.getServletRequest().getParameter("appId");
        session.setAttribute("appId", appId);
        super.session = decorate(session);
        return super.getSession();
    }
}
