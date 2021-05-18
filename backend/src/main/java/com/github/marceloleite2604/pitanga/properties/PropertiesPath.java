package com.github.marceloleite2604.pitanga.properties;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertiesPath {

    private static final String PREFIX = "pitanga";

    static final String WEB_SOCKET = PREFIX + ".web-socket";

    static final String ROOM = PREFIX + ".room";

    static final String USER = PREFIX + ".user";
}
