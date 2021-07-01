package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.dto.VoteDto;
import com.github.marceloleite2604.pitanga.model.Vote;
import org.springframework.stereotype.Component;

@Component
public class VoteToDtoMapper extends AbstractMapper<Vote, VoteDto> {
    @Override
    public VoteDto doMapTo(Vote vote) {

        return VoteDto.builder()
                .effort(vote.getEffort())
                .value(vote.getValue())
                .build();
    }

    @Override
    public Vote doMapFrom(VoteDto voteDto) {

        return Vote.builder()
                .effort(voteDto.getEffort())
                .value(voteDto.getValue())
                .build();
    }
}
