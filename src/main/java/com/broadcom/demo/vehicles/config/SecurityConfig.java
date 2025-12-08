package com.broadcom.demo.vehicles.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login","/h2-console/**","/css/**","/js/**","/images/**","/api/**").permitAll()
                    .anyRequest().authenticated())
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/menu", true)
                    .permitAll())
            .rememberMe(r -> r.tokenValiditySeconds(7*24*60*60))
            .logout(l -> l
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll())
            .headers(h -> h.frameOptions().disable())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
