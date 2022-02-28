package fi.eerosalla.web.bookcollectionapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    CorsFilter getCorsFilter() {
        UrlBasedCorsConfigurationSource configSource
                = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setAllowedHeaders(
                List.of("Origin", "Content-Type", "Accept")
        );
        corsConfig.setAllowedMethods(
                List.of("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
        );
        configSource.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(configSource);
    }

}
