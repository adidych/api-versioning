package io.github.adidych.apiversioning;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import io.github.adidych.apiversioning.exception.InvalidVersioningException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static io.github.adidych.apiversioning.VersioningUtils.extractVersion;

/**
 * A RequestCondition to match a list of version to the Accept-Version request header value.
 */

public class ApiVersionRequestCondition implements RequestCondition<ApiVersionRequestCondition> {

    private final Set<String> versions;

    public ApiVersionRequestCondition(String... versions) {
        this(Arrays.asList(versions));
    }

    private ApiVersionRequestCondition(Collection<String> versions) {
        this.versions = Set.copyOf(versions);
    }

    @Override
    public ApiVersionRequestCondition combine(ApiVersionRequestCondition other) {
        return new ApiVersionRequestCondition(other.versions);
    }

    @Override
    public ApiVersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        var requestVersion = extractVersion(request);
        if (!this.versions.contains(requestVersion)) {
            return null;
        }
        return this;
    }

    @Override
    public int compareTo(ApiVersionRequestCondition other, HttpServletRequest request) {
        throw new InvalidVersioningException("Invalid version configuration");
    }
}
