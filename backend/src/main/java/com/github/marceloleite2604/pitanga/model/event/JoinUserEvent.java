package com.github.marceloleite2604.pitanga.model.event;

public class JoinUserEvent extends Event<Void> {

    public JoinUserEvent() {
        super(EventType.JOIN_USER, null);
    }
}
