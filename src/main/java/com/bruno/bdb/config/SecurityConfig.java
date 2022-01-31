package com.bruno.bdb.config;

import com.bruno.bdb.security.JWTAuthenticationFilter;
import com.bruno.bdb.security.JWTAuthorizationFilter;
import com.bruno.bdb.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${swagger.username}")
    private String swaggerUsername;

    @Value("${swagger.password}")
    private String swaggerPassword;

    private final Environment environment;

    private static final String URN = "/api/v1";

    private final UserDetailsService userDetailsService;

    private final JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/swagger-ui/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            URN + "/accounts/**"
    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            URN + "/holders/**",
            "/login"
    };

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            httpSecurity.authorizeRequests().anyRequest().permitAll();
        } else {
            httpSecurity.authorizeRequests()
                    .antMatchers(PUBLIC_MATCHERS)
                    .permitAll()
                    .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET)
                    .permitAll()
                    .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.addFilter(new JWTAuthenticationFilter(jwtUtil, authenticationManager()));
        httpSecurity.addFilter(new JWTAuthorizationFilter(jwtUtil, authenticationManager(), userDetailsService));
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser(swaggerUsername)
                .password(bCryptPasswordEncoder().encode(swaggerPassword))
                .authorities("ADMIN");
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}