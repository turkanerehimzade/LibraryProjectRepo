package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dto.request.create.UsersCreateRequest;
import com.library.online_library_spring_app.dto.request.update.UsersUpdateRequest;
import com.library.online_library_spring_app.dto.response.UsersResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//addUser: +
//updateUser: +
//deleteUser: +
//getUserById: +
//getAllUsers: +
//getUserByUsername:+
// getUsersByRole: Müəyyən rola malik istifadəçiləri əldə edir.
//deactivateUser: İstifadəçi hesabını deaktiv edir.

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    @GetMapping
    public SuccessResponse<List<UsersResponse>> getAllUsers() {
        return usersService.getAllUsers();
    }
    @GetMapping("/user-id/{userId}")
    public SuccessResponse<UsersResponse> getUserById(@PathVariable Long userId) {
        return usersService.getUserById(userId);
    }
    @GetMapping("/user-username/{username}")
    public SuccessResponse<UsersResponse> getUserByUsername(@PathVariable String username) {
        return usersService.getUserByUsername(username);
    }
    @DeleteMapping("/{id}")
    public SuccessResponse<Object> deleteUserById(@PathVariable Long id) {
        return usersService.deleteUserById(id);
    }
    @PostMapping
    public SuccessResponse<Object> createUser(@RequestBody UsersCreateRequest usersCreateRequest) {
        return usersService.createUser(usersCreateRequest);
    }
    @PutMapping("/{id}")
    public SuccessResponse<Object> updateUser(@PathVariable Long id, @RequestBody UsersUpdateRequest usersUpdateRequest) {
        return usersService.updateUser(id,usersUpdateRequest);
    }
}
