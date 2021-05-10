package com.github.marceloleite2604.pitanga.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Component
@ConfigurationProperties(PropertiesPath.WEB_SOCKET)
@Getter
@Setter
@Validated
public class WebSocketProperties {

    @NotEmpty
    private Set<String> paths;

    @NotEmpty
    private Set<String> allowedOrigins;
}
