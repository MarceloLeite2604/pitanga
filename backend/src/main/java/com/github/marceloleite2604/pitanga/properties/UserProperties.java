package com.github.marceloleite2604.pitanga.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Component
@ConfigurationProperties(PropertiesPath.USER)
@Getter
@Setter
@Validated
public class UserProperties {

    @Positive
    private long maxUsers;

    @NotEmpty
    private Set<@NotEmpty String> icons;

    @AssertTrue(message = "The amount of icons informed must be greater than or equal the maximum amount of users.")
    private boolean isTheAmountOfIconsGreaterThanOrEqualMaximumAmountOfUsers() {
        return icons.size() >= maxUsers;
    }
}
