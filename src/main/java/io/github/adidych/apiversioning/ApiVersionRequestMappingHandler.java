package io.github.adidych.apiversioning;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Extends RequestMappingHandlerMapping to provide a custom {@link ApiVersionRequestCondition}.
 */

public class ApiVersionRequestMappingHandler extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion typeAnnotation = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiVersion methodAnnotation = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(methodAnnotation);
    }

    private RequestCondition<?> createCondition(ApiVersion accessMapping) {
        return (accessMapping != null) ? new ApiVersionRequestCondition(accessMapping.value()) : null;
    }
}
