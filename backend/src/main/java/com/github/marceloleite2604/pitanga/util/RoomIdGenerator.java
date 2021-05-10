package com.github.marceloleite2604.pitanga.util;

import com.github.marceloleite2604.pitanga.properties.RoomProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RoomIdGenerator {

    private final RoomProperties roomProperties;

    private final Random random;

    public long generate() {
        return (long) (random.nextDouble() * (roomProperties.getMaxId() - roomProperties.getMinId()) + roomProperties.getMinId());
    }
}
