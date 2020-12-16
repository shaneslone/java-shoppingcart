package com.lambdaschool.shoppingcart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LogoutController {
    @Autowired
    private TokenStore tokenStore;

    @RequestMapping(value = {"/oauth/revoke-token", "/logout"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void logoutSelf(HttpServletRequest request){
        String authHeader = request.getHeader("Authorication");
        if (authHeader != null){
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
    }
}
