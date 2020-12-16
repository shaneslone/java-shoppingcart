package com.lambdaschool.shoppingcart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(RESOURCE_ID)
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "webjars/**")
                .permitAll()
                .antMatchers("/roles/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/products/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/user")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/user/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/user/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/user/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "users/user/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/oauth/revoke-token", "/logout", "/carts/**").authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());

        http.csrf().disable();

        // required for H2 to work
        http.headers().frameOptions().disable();

        http.logout().disable();

    }
}
