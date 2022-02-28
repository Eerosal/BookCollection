package fi.eerosalla.web.bookcollectionapp.config;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "bc.database")
public class DatabaseConfig {

    private String jdbcUrl;
    private String username;
    private String password;

    @Bean
    ConnectionSource getConnectionSource() throws SQLException {
        return new JdbcConnectionSource(
            jdbcUrl,
            username,
            password
        );
    }
}
