package com.beheresoft.security.filter;

import com.beheresoft.security.result.Result;
import com.google.gson.Gson;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Aladi
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final String UNAUTHORIZED;

    static {
        Result result = Result.err(401, "unauthorized");
        UNAUTHORIZED = new Gson().toJson(result);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return this.executeLogin(request, response);
            }
            return true;
        } else {
            String requestWith = ((HttpServletRequest) request).getHeader("access-control-request-headers");
            if (requestWith != null && requestWith.contains("x-request-with")) {
                HttpServletResponse resp = ((HttpServletResponse)response);
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setHeader("Access-Control-Allow-Origin", "true");
                PrintWriter out = response.getWriter();
                out.print(UNAUTHORIZED);
                out.close();
            } else {
                this.saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }
}
