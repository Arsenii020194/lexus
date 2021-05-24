package ru.ssau.lexus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ssau.lexus.filter.JwtAuthFilter;
import ru.ssau.lexus.filter.LogoutHandler;
import ru.ssau.lexus.security.JwtAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService chatUserService;

    private final JwtAuthFilter jwtRequestFilter;

    private final LogoutHandler logoutHandler;

    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, @Lazy UserDetailsService chatUserService, JwtAuthFilter jwtRequestFilter, LogoutHandler logoutHandler) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.chatUserService = chatUserService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.logoutHandler = logoutHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(chatUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/signIn").permitAll()
                .antMatchers("/training-status/*", "/training-status", "/training-type/*", "/training-type",
                        "/user/*", "/user", "/group/*", "/group", "/role/*", "/role", "/subscription/*", "/subscription",
                        "/subscription-type/delete", "/subscription-type/create", "/subscription-type/update")
                .hasAuthority("ROLE_ADMIN")

                .antMatchers("/training", "/training/{id:[\\d+]}/miss", "/training/{id:[\\d+]}/approve", "/training/{id:[\\d+]}/complete")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_TRAINER")

                .antMatchers("/training/plan", "/subscription-type", "/subscription-type/{id:[\\d+]}")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER", "ROLE_TRAINER")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler).clearAuthentication(true).permitAll();
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
