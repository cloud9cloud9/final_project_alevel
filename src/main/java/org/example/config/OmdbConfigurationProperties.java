package org.example.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "org.example")
public class OmdbConfigurationProperties {

    private OMDB omdb = new OMDB();

    @Data
    public class OMDB {
        private String apiKey;
        private String url;
    }
}
