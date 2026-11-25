package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Room;

public interface RoomService {

    Room saveRoom(Room room);

    List<Room> getAllRooms();

    Optional<Room> getRoomById(Long id);
}
