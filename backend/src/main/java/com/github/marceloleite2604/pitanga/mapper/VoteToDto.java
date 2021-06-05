package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.model.Vote;
import com.github.marceloleite2604.pitanga.dto.VoteDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VoteToDto implements Mapper<Vote, VoteDto> {
    @Override
    public VoteDto mapTo(Vote vote) {

        if (Objects.isNull(vote)) {
            return null;
        }

        return VoteDto.builder()
                .effort(vote.getEffort())
                .value(vote.getValue())
                .build();
    }

    @Override
    public Vote mapFrom(VoteDto voteDto) {

        if (Objects.isNull(voteDto)) {
            return null;
        }

        return Vote.builder()
                .effort(voteDto.getEffort())
                .value(voteDto.getValue())
                .build();
    }
}
