package com.github.marceloleite2604.pitanga.model.mapper;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.dao.RoomDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomToDao implements Mapper<Room, RoomDao> {

    private final UserToDao userToDao;

    @Override
    public RoomDao mapTo(Room room) {

        if (Objects.isNull(room)) {
            return null;
        }

        var users = room.getUsers()
                .stream()
                .map(userToDao::mapTo)
                .collect(Collectors.toSet());

        return RoomDao.builder()
                .id(room.getId())
                .users(users)
                .build();
    }

    @Override
    public Room mapFrom(RoomDao roomDao) {

        if (Objects.isNull(roomDao)) {
            return null;
        }

        var users = roomDao.getUsers()
                .stream()
                .map(userToDao::mapFrom)
                .collect(Collectors.toSet());

        return Room.builder()
                .id(roomDao.getId())
                .users(users)
                .build();
    }
}
