package mindata.superhero.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers("/**").permitAll();

        httpSecurity.oauth2ResourceServer().jwt().decoder(jwtDecoder());
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }


    public JwtDecoder jwtDecoder(){
        final NimbusJwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
        final OAuth2TokenValidator<Jwt> expValidator = it -> {
            Assert.notNull(it, "jwt cannot be null");
            final Instant expiry = it.getExpiresAt();
            if (expiry != null) {
                if (Instant.now(Clock.systemUTC()).minus(Duration.of(60, ChronoUnit.SECONDS)).isAfter(expiry)) {
                    final OAuth2Error oAuth2Error =
                            new OAuth2Error(
                                    OAuth2ErrorCodes.INVALID_REQUEST, String.format("Jwt expired at %s", it.getExpiresAt()),
                                    "https://tools.ietf.org/html/rfc6750#section-3.1");
                    return OAuth2TokenValidatorResult.failure(oAuth2Error);
                }
            }

            return OAuth2TokenValidatorResult.success();
        };
        var issuerValidator = JwtValidators.createDefaultWithIssuer(issuerUri);

        jwtDecoder.setJwtValidator(expValidator);
        jwtDecoder.setJwtValidator(issuerValidator);

        return jwtDecoder;

    }

}
