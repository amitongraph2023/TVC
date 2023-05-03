package com.tokens.utils;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This Service class handles all the cookies related operations
 * and binds cookies with Jwt tokens.
 * 
 * @author yaman
 */
@Component
public class JwtCookieUtil {
	public static final String ACCESS_TOKEN_COOKIE_NAME = "access-token";
    public static final String ID_TOKEN_COOKIE_NAME = "id-token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh-token";
    /**
     * This method when called will return Id token cookie from http request.
     * @param request
     * @return
     */
    public static Optional<Cookie> getIdTokenCookie(HttpServletRequest request){
        return getTokenCookieByName(request, ID_TOKEN_COOKIE_NAME);
    }
    /**
     * This method when called will return Refresh token cookie from http request.
     * @param request
     * @return
     */
    public static Optional<Cookie> getRefreshTokenCookie(HttpServletRequest request){
        return getTokenCookieByName(request, REFRESH_TOKEN_COOKIE_NAME);
    }
    
    public static Optional<Cookie> getTokenCookieByName(HttpServletRequest request, String tokenCookieName) {
        if(request.getCookies() == null){
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies()).filter(a -> a.getName().equals(tokenCookieName)).findFirst();
    }
    /**
     * This method when called will add all token cookies to http response.
     * @param attr
     * @param idTokenValue
     * @param refreshTokenValue
     */
    public static void addTokenCookies(ServletRequestAttributes attr, String idTokenValue, String refreshTokenValue){
        addTokenCookie(attr, idTokenValue, ID_TOKEN_COOKIE_NAME);
        addTokenCookie(attr, refreshTokenValue, REFRESH_TOKEN_COOKIE_NAME);
    }
    /**
     * This method when called will add Token cookies to Http Header and response.
     * @param argAttr
     * @param tokenValue
     * @param cookieName
     */
    public static void addTokenCookie(ServletRequestAttributes argAttr, String tokenValue, String cookieName) {
        Cookie newCookie = new Cookie(cookieName, tokenValue);
        HttpServletResponse response = argAttr.getResponse();
        if(REFRESH_TOKEN_COOKIE_NAME.equals(cookieName)){
            //set just the refresh token to have a longer expiration (30 days)
            newCookie.setMaxAge(60 * 60 * 24 * 30);
        }else {
            //others expire in 3 hours
            newCookie.setMaxAge(60 * 60 * 3);
        }
        newCookie.setSecure(false);
        newCookie.setPath("/");
        if(response != null) {
            response.addCookie(newCookie);
        }
    }
    /**
     * This method when called will clear All three cookies from Http Header.
     * @param attr
     */
    public static void clearTokenCookies(ServletRequestAttributes attr) {
        clearTokenCookie(attr, ID_TOKEN_COOKIE_NAME);
        clearTokenCookie(attr, REFRESH_TOKEN_COOKIE_NAME);
        clearTokenCookie(attr, ACCESS_TOKEN_COOKIE_NAME);
    }
    /**
     * This method when called will clear Token cookie from Http Header.
     * @param argAttr
     * @param argCookieName
     */
    public static void clearTokenCookie(ServletRequestAttributes argAttr, String argCookieName) {
        HttpServletResponse response = argAttr.getResponse();
        if(argAttr.getRequest() != null && (argAttr.getRequest()).getCookies() != null) {
            Optional<Cookie> jwtTokenCookie = Arrays.stream((argAttr.getRequest()).getCookies()).filter(a -> a.getName().equals(argCookieName)).findFirst();
            Cookie cookie = jwtTokenCookie.orElseGet(() -> new Cookie(argCookieName, ""));
            cookie.setMaxAge(0);
            cookie.setPath("/");
            if (response != null) {
                response.addCookie(cookie);
            }
        }
    }

}
