package io.github.adidych.apiversioning;

import jakarta.servlet.http.HttpServletRequest;

public final class VersioningUtils {

    public static final String ACCEPT_VERSION_HEADER = "Accept-Version";
    public static final String DEFAULT_API_VERSION = "1.0";

    private VersioningUtils() {
    }

    public static String extractVersion(HttpServletRequest request) {
        var requestVersion = request.getHeader(ACCEPT_VERSION_HEADER);
        if (requestVersion == null || requestVersion.isBlank()) {
            requestVersion = DEFAULT_API_VERSION;
        }
        return requestVersion;
    }
}
