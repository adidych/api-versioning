# api-versioning

`api-versioning` is a lightweight Spring MVC extension that enables API versioning via annotations.
It allows routing HTTP requests to different controller methods based on the value of the
Accept-Version request header.

The library is designed to be explicit, annotation-driven, and non-invasive, without requiring
URL versioning or custom media types.

## Key Features
* Version routing based on the Accept-Version HTTP header
* Simple @ApiVersion annotation
* Supports multiple API versions per endpoint
* Class-level and method-level versioning
* Sensible default version handling (1.0)
* Compatible with Spring Boot 3.x and 4.x

## How It Works
The Accept-Version request header is inspected for each incoming request.
The request is routed to the controller method whose @ApiVersion matches the header value.
If no version is specified, version 1.0 is assumed by default.

## Annotation Rules

- Class-level @ApiVersion applies to all endpoints in the controller.
- Method-level @ApiVersion overrides the class-level version.

If neither class-level nor method-level annotation is present,
the endpoint is treated as version 1.0.

Note: Explicitly declaring @ApiVersion("1.0") is optional, as 1.0 is the default version.

## Integration

To enable @ApiVersion support, a custom RequestMappingHandlerMapping
must be registered in your Spring configuration.

### Installation
Add the dependency to your pom.xml:
```
<dependency>
  <groupId>io.github.adidych</groupId>
  <artifactId>api-versioning</artifactId>
  <version>1.0.0</version>
</dependency>
```

### SpringBoot 4.x

```
import io.github.adidych.apiversioning.ApiVersionRequestMappingHandler;
...

@Configuration
public class ApiVersioningConfig extends DelegatingWebMvcConfiguration {

    @Override
    protected @NonNull RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandler();
    }

    @Bean
    @Primary
    @Override
    public @NonNull RequestMappingHandlerMapping requestMappingHandlerMapping(
            @Qualifier("mvcContentNegotiationManager") @NonNull ContentNegotiationManager contentNegotiationManager,
            @Qualifier("mvcApiVersionStrategy") @Nullable ApiVersionStrategy apiVersionStrategy,
            @Qualifier("mvcConversionService") @NonNull FormattingConversionService conversionService,
            @Qualifier("mvcResourceUrlProvider") @NonNull ResourceUrlProvider resourceUrlProvider) {
        return super.requestMappingHandlerMapping(
                contentNegotiationManager, 
                apiVersionStrategy, 
                conversionService, 
                resourceUrlProvider
        );
    }
}
```

## Usage Example

```
import com.io.github.adidych.apiversioning.ApiVersion;
...

@RestController
@RequestMapping("/user")
@ApiVersion("1.0") // Default version for all endpoints in this controller
public class UserController {

    @PostMapping // Accept-Version: 1.0
    public void createUser(/* ... */) {
        ...
    }

    @PostMapping
    @ApiVersion("1.1") // Accept-Version: 1.1
    public void createUserV11(/* ... */) {
        ...
    }

    @PostMapping
    @ApiVersion({"1.2", "1.3"}) // Accept-Version: 1.2 or 1.3
    public void createUserV12(/* ... */) {
        ...
    }
}
```

## Request Examples
```
    POST /user
    Accept-Version: 1.0
```

```
    POST /user
    Accept-Version: 1.2
```


## When to Use This Library
* You want header-based API versioning
* You want to avoid /v1, /v2 URLs
* You need multiple versions of the same endpoint in parallel
* You want to version logic close to the controller code
