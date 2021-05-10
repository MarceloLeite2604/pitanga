package com.github.marceloleite2604.pitanga.dao;

import com.github.marceloleite2604.pitanga.model.Room;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class RoomDao {

    private final Map<Long, Room> roomsMap = new HashMap<>();

    public Room save(Room room) {
        roomsMap.put(room.getId(), room);
        return room;
    }

    public Optional<Room> get(long id) {
        return Optional.ofNullable(roomsMap.get(id));
    }
}
