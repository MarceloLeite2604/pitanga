package com.github.marceloleite2604.pitanga.model.dao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RoomDao {

    private long id;

    @Singular
    private Set<UserDao> users;
}
