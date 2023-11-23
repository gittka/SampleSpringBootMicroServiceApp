package org.alxtek.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {
    @Value("${eureka.username}")
    private String username;

    @Value("${eureka.password}")
    private String password;

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
       authenticationManagerBuilder.inMemoryAuthentication()
               .passwordEncoder(NoOpPasswordEncoder.getInstance())
               .withUser(username).password(password)
               .authorities("USER");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrf -> csrf.ignoringRequestMatchers("/eureka/**"));
        return httpSecurity.build();
    }
}
