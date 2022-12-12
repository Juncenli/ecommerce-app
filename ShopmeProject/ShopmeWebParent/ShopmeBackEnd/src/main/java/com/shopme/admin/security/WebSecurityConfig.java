package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 这个文件是配置文件，到时候会被自动扫描，我们在这个配置文件中，permit了所有的请求，所以不需要输入密码

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 和UserService里面的 - > private PasswordEncoder passwordEncoder 相互链接
    // PasswordEncoder is abstract, we use BCryptPasswordEncoder to instantiate PasswordEncoder
    @Bean
    public UserDetailsService userDetailsService() {
        return new ShopmeUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // To deny access for users who are not admin to access the user module
                .antMatchers("/users/**").hasAuthority("Admin")
                .antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and().logout().permitAll()
                .and()
                .rememberMe()
                .key("AbcDefgHijKlmnOpqrs_1234567890") // cookies will survive if restarted
                .tokenValiditySeconds(7 * 24 * 60 * 60); // expiration time: 7 days

        // It’s because by default, Spring Security supplies a random key at application’s startup.
        // So if you fix the key, remember-me cookies are still valid until expire.
        // Once we log out, the cookie will be cleared.
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
