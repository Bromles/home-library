package com.bromles.backend.config

import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jose.shaded.json.JSONObject
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests { authorizeRequests ->
                authorizeRequests
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { resourceServerConfigurer ->
                resourceServerConfigurer
                    .jwt { jwtConfigurer ->
                        jwtConfigurer
                            .jwtAuthenticationConverter(jwtAuthenticationConverter())
                    }
            }

        return http.build()
    }

    @Bean
    fun jwtAuthenticationConverter(): Converter<Jwt?, AbstractAuthenticationToken?> {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter())

        return jwtAuthenticationConverter
    }

    @Bean
    fun jwtGrantedAuthoritiesConverter(): Converter<Jwt?, Collection<GrantedAuthority?>?> {
        val delegate = JwtGrantedAuthoritiesConverter()

        return Converter { jwtNull: Jwt? ->
            jwtNull?.let { jwt ->
                val grantedAuthorities = delegate.convert(jwt)

                if (jwt.claims["realm_access"] == null) {
                    return@Converter grantedAuthorities
                }

                val realmAccess: JSONObject = jwt.claims["realm_access"] as JSONObject
                if (realmAccess["roles"] == null) {
                    return@Converter grantedAuthorities
                }

                val roles: JSONArray = realmAccess["roles"] as JSONArray
                val keycloakAuthorities: List<SimpleGrantedAuthority?> = roles.stream()
                    .map { role -> SimpleGrantedAuthority("ROLE_$role") }
                    .toList()

                grantedAuthorities!!.addAll(keycloakAuthorities)
                grantedAuthorities
            }
        }
    }
}
