package com.github.marceloleite2604.pitanga.model.mapper;

import com.github.marceloleite2604.pitanga.model.Vote;
import com.github.marceloleite2604.pitanga.model.dao.VoteDao;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VoteToDao implements Mapper<Vote, VoteDao> {
    @Override
    public VoteDao mapTo(Vote vote) {

        if (Objects.isNull(vote)) {
            return null;
        }

        return VoteDao.builder()
                .effort(vote.getEffort())
                .value(vote.getValue())
                .build();
    }

    @Override
    public Vote mapFrom(VoteDao voteDao) {

        if (Objects.isNull(voteDao)) {
            return null;
        }

        return Vote.builder()
                .effort(voteDao.getEffort())
                .value(voteDao.getValue())
                .build();
    }
}
