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

    private final AttendeeToDao attendeeToDao;

    @Override
    public RoomDao mapTo(Room room) {

        if (Objects.isNull(room)) {
            return null;
        }

        var attendees = room.getAttendees()
                .stream()
                .map(attendeeToDao::mapTo)
                .collect(Collectors.toSet());

        return RoomDao.builder()
                .id(room.getId())
                .attendees(attendees)
                .votingStatus(room.getVotingStatus())
                .build();
    }

    @Override
    public Room mapFrom(RoomDao roomDao) {

        if (Objects.isNull(roomDao)) {
            return null;
        }

        var attendees = roomDao.getAttendees()
                .stream()
                .map(attendeeToDao::mapFrom)
                .collect(Collectors.toSet());

        var votingStatus = roomDao.getVotingStatus();

        return Room.builder()
                .id(roomDao.getId())
                .attendees(attendees)
                .votingStatus(votingStatus)
                .build();
    }
}
