package com.github.marceloleite2604.pitanga.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.marceloleite2604.pitanga.model.converter.VotingStatusConverter;
import lombok.Getter;

import javax.persistence.Convert;
import java.util.Objects;

@Getter
@Convert(converter = VotingStatusConverter.class)
public enum VotingStatus {
    OPEN("open"),
    CLOSED("closed");

    @JsonValue
    private final String value;

    VotingStatus(String value) {
        this.value = value;
    }

    public static VotingStatus findByValue(String value) {
        if (Objects.isNull(value)) {
            return null;
        }

        for (VotingStatus votingStatus : values()) {
            if (votingStatus.getValue().equals(value)) {
                return votingStatus;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot find voting status for value \"%s\"", value));
    }
}
