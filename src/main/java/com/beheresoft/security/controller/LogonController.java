package com.beheresoft.security.controller;


import com.beheresoft.security.result.Result;
import com.beheresoft.security.token.CasToken;
import com.beheresoft.security.token.LoginToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Aladi
 */
@Controller
@RequestMapping("/api/log")
@Validated
public class LogonController {

    @RequestMapping("/{appId}/login.json")
    public Result login(@PathVariable("appId") @NotNull String appId, @NotBlank String username, @NotBlank String password) {
        Subject user = SecurityUtils.getSubject();
        if (!user.isAuthenticated()) {
            LoginToken token = new LoginToken(appId, username, password);
            user.login(token);
        }
        return Result.ok();
    }

    @RequestMapping("/logout.json")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(String appId) {
        ModelAndView modelAndView = new ModelAndView("login");
        Map<String, Object> model = modelAndView.getModel();
        model.put("appId", appId);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(String appId, String username, String password) {
        ModelAndView modelAndView = new ModelAndView("login");
        try {
            Subject user = SecurityUtils.getSubject();
            if (!user.isAuthenticated()) {
                if (appId != null) {
                    CommonProfile profile = new CommonProfile();
                    profile.setClientName(appId);
                    LinkedHashMap<String, CommonProfile> token = new LinkedHashMap<>();
                    token.put(CasToken.DEFAULT_USER, profile);
                    CasToken casToken = new CasToken(token, true);
                    casToken.setPassword(password);
                    casToken.setUsername(username);
                    user.login(casToken);
                } else {
                    user.login(new LoginToken(appId, username, password));
                }
            }
        } catch (Exception e) {
            modelAndView.addObject("error", e.getCause());
        }
        return modelAndView;
    }

}
