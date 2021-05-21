package com.github.marceloleite2604.pitanga.model.dao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDao {

    @EqualsAndHashCode.Include
    private String id;
}
