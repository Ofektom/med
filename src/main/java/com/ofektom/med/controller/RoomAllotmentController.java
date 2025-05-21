package com.ofektom.med.controller;

import com.ofektom.med.dto.request.RoomAllotmentDto;
import com.ofektom.med.dto.response.RoomAllotmentResponse;
import com.ofektom.med.service.RoomAllotmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomAllotmentController {

    @Autowired
    private RoomAllotmentService roomAllotmentService;

    @PostMapping("/allot")
    public ResponseEntity<RoomAllotmentResponse> allotRoom(@RequestBody RoomAllotmentDto roomAllotmentDto) {
        RoomAllotmentResponse response = roomAllotmentService.allotRoom(roomAllotmentDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomAllotmentResponse> updateRoomAllotment(@PathVariable Long id, @RequestBody RoomAllotmentDto roomAllotmentDto) {
        RoomAllotmentResponse response = roomAllotmentService.updateRoomAllotment(id, roomAllotmentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomAllotment(@PathVariable Long id) {
        roomAllotmentService.deleteRoomAllotment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alloted")
    public ResponseEntity<List<RoomAllotmentResponse>> getAllAllotedRooms() {
        List<RoomAllotmentResponse> response = roomAllotmentService.getAllAllotedRooms();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomAllotmentResponse> getRoomAllotmentById(@PathVariable Long id) {
        RoomAllotmentResponse response = roomAllotmentService.getRoomAllotmentById(id);
        return ResponseEntity.ok(response);
    }
}