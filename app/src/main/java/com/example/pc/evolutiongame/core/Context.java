package com.example.pc.evolutiongame.core;

import com.example.pc.evolutiongame.model.Room;

public class Context {
    private String id;
    private Room room;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return this.room;
    }
}