package com.ss.camper.oauth2.config;

import com.ss.camper.oauth2.application.CustomOAuth2UserService;
import com.ss.camper.oauth2.application.CustomUserDetailsService;
import com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ss.camper.oauth2.filter.TokenAuthenticationFilter;
import com.ss.camper.oauth2.handler.CustomAuthenticationEntryPoint;
import com.ss.camper.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.ss.camper.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ss.camper.config.WebMvcConfig.STATIC_PATH;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] GET_WHITELIST = {
        "/auth/**",
    };

    public static final String[] POST_WHITELIST = {
        "/auth/**",
        "/user/client",
        "/user/business"
    };

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(STATIC_PATH);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인증되지 않은 사용자의 요청 시 발생
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
                .and()
                .csrf().disable() // csrf 미사용
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable() // 로그인 폼 미사용
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_WHITELIST).permitAll() // Security 허용 Url
                .antMatchers(HttpMethod.POST, POST_WHITELIST).permitAll() // Security 허용 Url
                .anyRequest().authenticated()// 그 외엔 모두 인증 필요
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/auth/social/authorization") // 소셜 로그인 Url
                .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository()) // 인증 요청을 쿠키에 저장하고 검색
                .and()
                .redirectionEndpoint().baseUri("/auth/social/callback/*") // 소셜 인증 후 Redirect Url
                .and()
                .userInfoEndpoint().userService(customOAuth2UserService) // 소셜의 회원 정보를 받아와 가공처리
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler) // 인증 성공 시 Handler
                .failureHandler(oAuth2AuthenticationFailureHandler); // 인증 실패 시 Handler

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}