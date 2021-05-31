package com.github.marceloleite2604.pitanga.model.converter;

import com.github.marceloleite2604.pitanga.model.VotingStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class VotingStatusConverter implements AttributeConverter<VotingStatus, String> {

    @Override
    public String convertToDatabaseColumn(VotingStatus votingStatus) {
        return votingStatus.getValue();
    }

    @Override
    public VotingStatus convertToEntityAttribute(String value) {
        return VotingStatus.findByValue(value);
    }
}
