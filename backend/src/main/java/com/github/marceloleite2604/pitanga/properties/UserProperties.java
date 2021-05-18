package com.github.marceloleite2604.pitanga.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(PropertiesPath.USER)
@Getter
@Setter
@Validated
public class UserProperties {

    private long maxUsers;
}
