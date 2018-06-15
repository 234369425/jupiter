package com.beheresoft.security.token;

import io.buji.pac4j.token.Pac4jToken;
import org.pac4j.core.profile.CommonProfile;

import java.util.LinkedHashMap;

/**
 * @author Aladi
 */
public class CasToken extends Pac4jToken {

    public static final String DEFAULT_USER = "CAS_DEFAULT_USER";

    public CasToken(LinkedHashMap<String, CommonProfile> profiles, boolean isRemembered) {
        super(profiles, isRemembered);
    }

    public String getAppId() {
        return getProfile().getClientName();
    }

    public String getUsername() {
        return super.getProfiles().get(DEFAULT_USER).getAttribute(CasProfileDefinition.USER_NAME).toString();
    }

    public String getPassword() {
        return super.getProfiles().get(DEFAULT_USER).getAttribute(CasProfileDefinition.PASSWORD).toString();
    }

    public void setUsername(String username) {
        getProfile().addAttribute(CasProfileDefinition.USER_NAME, username);
    }

    public void setPassword(String password) {
        getProfile().addAttribute(CasProfileDefinition.PASSWORD, password);
    }

    private CommonProfile getProfile() {
        return super.getProfiles().computeIfAbsent(DEFAULT_USER, k -> new CommonProfile());
    }

    public static class CasProfileDefinition {
        public static final String USER_NAME = "username";
        public static final String PASSWORD = "password";
    }

}
