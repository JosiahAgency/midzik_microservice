package com.midziklabs.advertisement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.midziklabs.advertisement.utils.JwtAuthFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Request in security ffilter chain");
        String base_url = "/api/v1";
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(r -> r
                        .requestMatchers(HttpMethod.POST, base_url + "/advertisement/").hasAuthority("User")
                        .requestMatchers(HttpMethod.GET, base_url + "/advertisement/location/id/{id}").hasAnyAuthority("Administrator", "Reviewer")
                        .requestMatchers(HttpMethod.GET, base_url + "/advertisement/").hasAnyAuthority("Administrator","Reviewer")
                        .requestMatchers(HttpMethod.GET, base_url+"/advertisement/user").hasAuthority("User")
                        .requestMatchers(HttpMethod.GET, base_url+"/advertisement/visual").hasAnyAuthority("Administrator", "Reviewer", "User")
                        /* Category routes */
                        .requestMatchers(HttpMethod.GET, base_url + "/category/").hasAnyAuthority("Administrator", "User", "Reviewer")
                        .requestMatchers(HttpMethod.GET, base_url + "/category/name/{name}").hasAnyAuthority("Administrator", "User")
                        .requestMatchers(HttpMethod.GET, base_url + "/category/id/{id}").hasAnyAuthority(
                                "Administrator", "Reviewer")
                        .requestMatchers(HttpMethod.DELETE, base_url + "/category/").hasAnyAuthority("Administrator",
                                "Reviewer")
                        .requestMatchers(HttpMethod.POST, base_url + "/category/").hasAnyAuthority("Administrator",
                                "Reviewer")
                        .requestMatchers(HttpMethod.PUT, base_url + "/category/{id}").hasAnyAuthority("Administrator",
                                "Reviewer")
                        /* Location routes */
                        .requestMatchers(HttpMethod.POST, base_url + "/location/").hasAnyAuthority("Administrator",
                                "Reviewer")
                        .requestMatchers(HttpMethod.GET, base_url + "/location/").hasAnyAuthority("Administrator", "User", "Reviewer")
                        .requestMatchers(HttpMethod.PUT, base_url + "/location/{id}").hasAnyAuthority("Administrator",
                                "Reviewer")
                        .requestMatchers(HttpMethod.GET, base_url + "/location/id/{id}").hasAnyAuthority(
                                "Administrator", "Reviewer")
                        .requestMatchers(HttpMethod.DELETE, base_url + "/location/{id}").hasAnyAuthority(
                                "Administrator", "Reviewer")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

}
