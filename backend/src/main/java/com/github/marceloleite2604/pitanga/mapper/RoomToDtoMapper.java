package com.github.marceloleite2604.pitanga.mapper;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoomToDtoMapper extends AbstractMapper<Room, RoomDto> {

    private final AttendeeToDtoMapper attendeeToDtoMapper;

    @Override
    public RoomDto doMapTo(Room room) {

        var attendees = room.getAttendees()
                .stream()
                .map(attendeeToDtoMapper::mapTo)
                .collect(Collectors.toSet());

        return RoomDto.builder()
                .id(room.getId())
                .attendees(attendees)
                .votingStatus(room.getVotingStatus())
                .build();
    }

    @Override
    public Room doMapFrom(RoomDto roomDto) {

        var attendees = roomDto.getAttendees()
                .stream()
                .map(attendeeToDtoMapper::mapFrom)
                .collect(Collectors.toSet());

        var votingStatus = roomDto.getVotingStatus();

        return Room.builder()
                .id(roomDto.getId())
                .attendees(attendees)
                .votingStatus(votingStatus)
                .build();
    }
}
