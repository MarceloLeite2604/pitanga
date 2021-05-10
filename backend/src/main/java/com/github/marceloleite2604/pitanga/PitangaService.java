package com.github.marceloleite2604.pitanga;

import com.github.marceloleite2604.pitanga.dao.RoomDao;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.util.RoomIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PitangaService {

    private final RoomIdGenerator roomIdGenerator;

    private final RoomDao roomDao;

    public Room createRoom() {
        var room = Room.builder()
                .id(roomIdGenerator.generate())
                .lastUpdate(LocalDateTime.now())
                .build();
        return roomDao.save(room);
    }
}
