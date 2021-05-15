package com.github.marceloleite2604.pitanga.model.event.checkroomexists;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class CheckRoomExistsPayload {

    private Long id;

    private Boolean exists;
}
