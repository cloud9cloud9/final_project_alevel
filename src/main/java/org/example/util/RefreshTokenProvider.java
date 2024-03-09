package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";

    private final HttpServletRequest http;

    public Optional<String> getRefreshToken() {
        return Optional.ofNullable(http.getHeader(REFRESH_TOKEN_HEADER))
                .filter(StringUtils::isNotBlank);
    }
}
