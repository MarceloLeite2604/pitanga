package com.github.marceloleite2604.pitanga.util;

import com.github.marceloleite2604.pitanga.model.Room;
import com.github.marceloleite2604.pitanga.model.attendee.Attendee;
import com.github.marceloleite2604.pitanga.properties.UserProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IconRetriever {

    private final List<String> icons;

    public IconRetriever(UserProperties userProperties) {
        this.icons = new ArrayList<>(userProperties.getIcons());
    }

    public String retrieve(Room room) {

        var usedIcons = room.getAttendees()
                .stream()
                .map(Attendee::getIcon)
                .collect(Collectors.toSet());

        var availableIcons = icons.stream()
                .filter(icon -> !usedIcons.contains(icon))
                .collect(Collectors.toCollection(TreeSet::new));

        if (CollectionUtils.isEmpty(availableIcons)) {
            throw new IllegalStateException("No more icons to associate with attendees.");
        }

        return availableIcons.iterator()
                .next();
    }
}
