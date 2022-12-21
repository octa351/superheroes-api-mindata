package mindata.superhero.api.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String OAUTH_URL = "https://q2-reprocess-pool.auth.sa-east-1.amazoncognito.com/token";
    private final String  SCOPE = "q2-reprocess-resource-server/octa_scope";
    @Bean
    public OpenAPI superHeroesOpenAPIConfig(@Value("${server.servlet.context-path}") String contextPath) {
        return new OpenAPI()
                .info(new Info().title("SuperHeroes Api")
                        .description("SuperHeroes Api For Mindata Technical Test")
                        .version("v0.0.1"))
                .addServersItem(new Server().url(contextPath))
                .components(new Components()
                        .addSecuritySchemes("spring_oauth", securityScheme())
                );
    }

    private SecurityScheme securityScheme() {
        Scopes scopes = new Scopes()
                .addString(SCOPE, SCOPE);

        return new SecurityScheme()
                .name("client_credentials")
                .type(SecurityScheme.Type.OAUTH2)
                .description("Oauth2 flow")
                .flows(new OAuthFlows()
                        .clientCredentials(new OAuthFlow()
                                .tokenUrl(OAUTH_URL)
                                .scopes(scopes)
                        ));
    }
}
