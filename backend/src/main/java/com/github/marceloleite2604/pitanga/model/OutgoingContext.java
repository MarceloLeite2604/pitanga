package com.github.marceloleite2604.pitanga.model;

import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import com.github.marceloleite2604.pitanga.model.event.Event;
import com.github.marceloleite2604.pitanga.model.event.Payload;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OutgoingContext {

    @Builder.Default
    private final Set<UserDao> recipients = new HashSet<>();

    private final Event<? extends Payload> event;
}
