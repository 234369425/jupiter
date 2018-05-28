package com.beheresoft.security.subject;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.springframework.stereotype.Component;

/**
 * @author Aladi
 */
@Component
public class MultiAppSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        if (!(context instanceof WebSubjectContext)) {
            return super.createSubject(context);
        }
        WebSubjectContext webContext = (WebSubjectContext) context;
        SecurityManager securityManager = webContext.resolveSecurityManager();
        Session session = webContext.resolveSession();
        boolean authenticated = webContext.isSessionCreationEnabled();
        String host = webContext.getHost();
        return new MultiAppSubject(webContext.resolvePrincipals(), authenticated, host,
                session, webContext.getServletRequest(), webContext.getServletResponse(), securityManager);
    }

}
