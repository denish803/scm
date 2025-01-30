package com.scm.configuration;

import com.scm.repository.UserRepository;
import com.scm.services_implement.SecurityCustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private SecurityCustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFailureConfig authenticationFailureConf;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

//        url primed
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**")
                        .permitAll()
                        .requestMatchers("/user/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll()
        );
//                login form
        http.formLogin(
                form -> form
                        .loginPage("/login/user")
                        .loginProcessingUrl("/login/user")
                        .defaultSuccessUrl("/user/contact")
                        .successHandler((request, response, authentication) -> response.sendRedirect("/user/profile"))
                        .failureUrl("/login/user?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureHandler(authenticationFailureConf)
        );
       http.logout(
               logout -> logout
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/login/user?logout=true")
                       .permitAll()
       );
        return http.build();
    }

}
