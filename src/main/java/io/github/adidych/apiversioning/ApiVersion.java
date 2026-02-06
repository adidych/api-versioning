package io.github.adidych.apiversioning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the version required to map a controller method to a request.
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

    /**
     * A list of required API version (e.g. {"1.1", "1.2"}).
     */
    String[] value() default {};

}
