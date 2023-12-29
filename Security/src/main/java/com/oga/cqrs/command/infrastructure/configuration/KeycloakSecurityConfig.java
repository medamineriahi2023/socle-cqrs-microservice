package com.oga.cqrs.command.infrastructure.configuration;

import com.google.common.collect.ImmutableList;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.authorizeRequests()
        .antMatchers("/security/**").permitAll()
        .anyRequest()
        .permitAll();
    http.csrf().disable();
  }

  //providing authentification service
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  // registers a user session after successful authentication
  @Bean
  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();


    final CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

    config.setAllowedOriginPatterns(ImmutableList.of("*"));
    config.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
    config.setAllowCredentials(true);
    config.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
    config.setExposedHeaders(ImmutableList.of("Content-Disposition"));

    source.registerCorsConfiguration("/**", config);

    return source;
  }


}

