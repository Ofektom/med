package com.ofektom.med.controller;

import com.ofektom.med.dto.response.ApiResponse;
import com.ofektom.med.dto.response.PaginatedResponse;
import com.ofektom.med.dto.response.UserResponse;
import com.ofektom.med.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(
            @PathVariable("user_id") Long userId
    ){
        return userService.getUserById(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page == null || size == null) {
            List<UserResponse> users = userService.getAllUsersByRoleAsList(role);
            ApiResponse<List<UserResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Full list of " + (role != null ? role : "users") + " retrieved successfully",
                    null,
                    users
            );
            return ResponseEntity.ok(response);
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserResponse> usersPage = userService.getAllUsersByRoleAsPage(role, pageable);
            PaginatedResponse<UserResponse> paginatedData = new PaginatedResponse<>(
                    usersPage.getNumber(),
                    usersPage.getSize(),
                    usersPage.getTotalPages(),
                    usersPage.getTotalElements(),
                    usersPage.getContent()
            );
            ApiResponse<PaginatedResponse<UserResponse>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Paginated list of " + (role != null ? role : "users") + " retrieved successfully",
                    null,
                    paginatedData
            );
            return ResponseEntity.ok(response);
        }
    }
}
