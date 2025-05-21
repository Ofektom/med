package com.ofektom.med.service;

import com.ofektom.med.dto.request.RoomAllotmentDto;
import com.ofektom.med.dto.response.RoomAllotmentResponse;

import java.util.List;

public interface RoomAllotmentService {
    RoomAllotmentResponse allotRoom(RoomAllotmentDto roomAllotmentDto);
    RoomAllotmentResponse updateRoomAllotment(Long id, RoomAllotmentDto roomAllotmentDto);
    void deleteRoomAllotment(Long id);
    List<RoomAllotmentResponse> getAllAllotedRooms();
    RoomAllotmentResponse getRoomAllotmentById(Long id);

}