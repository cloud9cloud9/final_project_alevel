package org.example.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "org.example")
public class OmdbConfigurationProperties {

    private OMDB omdb = new OMDB();

    private OMDBSec omdbsec = new OMDBSec();

    private OMDBThird omdbthird = new OMDBThird();

    @Data
    public class OMDB {
        private String apiKey;
        private String url;
    }

    @Data
    public class OMDBSec {
        private String apiKey;
        private String url;
    }

    @Data
    public class OMDBThird {
        private String apiKey;
        private String url;
    }
}
