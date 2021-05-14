package com.github.marceloleite2604.pitanga.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;

@Component
@ConfigurationProperties(PropertiesPath.ROOM)
@Getter
@Setter
@Validated
public class RoomProperties {

    @Positive
    private long minId;

    @Positive
    private long maxId;
}
