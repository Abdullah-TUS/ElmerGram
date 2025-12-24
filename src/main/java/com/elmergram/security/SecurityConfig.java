package com.elmergram.security;

import com.elmergram.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(
                                "/api/v1/auth/**",       // register & login
                                "/login",                // optional login path
                                "/api/v1/posts/explorer" // public posts
                        ).permitAll()
                        .anyRequest().authenticated() // everything else protected
        );

        http.sessionManagement(session ->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(CustomUserDetailsService userDetailsService, PasswordEncoder encoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService); // constructor takes UserDetailsService
        provider.setPasswordEncoder(encoder); // set password encoder separately
        return provider;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new  BCryptPasswordEncoder();
    }
}
