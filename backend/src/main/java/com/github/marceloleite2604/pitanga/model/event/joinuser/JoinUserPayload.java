package com.github.marceloleite2604.pitanga.model.event.joinuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.User;
import com.github.marceloleite2604.pitanga.model.dao.RoomDao;
import com.github.marceloleite2604.pitanga.model.dao.UserDao;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class JoinUserPayload {

    private UserDao user;
    private RoomDao room;
}
