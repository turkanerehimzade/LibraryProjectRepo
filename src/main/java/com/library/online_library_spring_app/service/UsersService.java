package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Role;
import com.library.online_library_spring_app.dao.entity.UserRole;
import com.library.online_library_spring_app.dao.repository.UserRoleRepository;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dao.repository.UsersRepository;
import com.library.online_library_spring_app.dto.request.create.UsersCreateRequest;
import com.library.online_library_spring_app.dto.request.update.UsersUpdateRequest;
import com.library.online_library_spring_app.dto.response.UsersResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.enums.RoleName;
import com.library.online_library_spring_app.exception.BaseException;
import com.library.online_library_spring_app.mapper.UsersMapper;
import com.library.online_library_spring_app.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.library.online_library_spring_app.security.SecurityConfig.encoder;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final UserRoleRepository userRoleRepository;

    public  SuccessResponse<List<UsersResponse>> getAllUsers() {
        List<UsersResponse> usersResponseList = usersRepository.findAll().stream().map(usersMapper::toUsersResponse).toList();
        return SuccessResponse.createSuccessResponse(usersResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<UsersResponse> getUserById(Long userId) {
        UsersResponse usersResponse = usersRepository.findById(userId).map(usersMapper::toUsersResponse).orElseThrow();
        return SuccessResponse.createSuccessResponse(usersResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<UsersResponse> getUserByUsername(String username) {
        UsersResponse usersResponse = usersRepository.findByUsername(username).map(usersMapper::toUsersResponse).orElseThrow();
        return SuccessResponse.createSuccessResponse(usersResponse, ResponseCode.SUCCESS);
    }

    public Users findUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow();
    }

    public SuccessResponse<Object> deleteUserById(Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow();
        users.setUserIsActive(false);
        users.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        usersMapper.toUsersResponse(users);
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> createUser(UsersCreateRequest usersCreateRequest) {
//        validateUser(usersCreateRequest);
        Users users = usersMapper.toUsers(usersCreateRequest);
        users.setPassword(encoder().encode(usersCreateRequest.getPassword()));
        UserRole userRole=new UserRole();
        userRole.setRole(usersCreateRequest.getRole());
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

//    public void validateUser(UsersCreateRequest usersCreateRequest) {
//        if (usersCreateRequest.getUsername() == null || usersCreateRequest.getUsername().trim().equals("")
//                || usersCreateRequest.getPassword() == null || usersCreateRequest.getPassword().trim().equals("")) {
//            throw BaseException.of(ResponseCode.ERROR);
//        }
//    }

    public SuccessResponse<Object> updateUser(Long id, UsersUpdateRequest usersUpdateRequest) {
        Users users = usersRepository.findById(id).orElseThrow();
        buildUsers(users, usersUpdateRequest);
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public void buildUsers(Users users, UsersUpdateRequest usersUpdateRequest) {
        if (usersUpdateRequest.getUsername() != null)
            users.setUsername(usersUpdateRequest.getUsername());
        if (usersUpdateRequest.getPassword() != null)
            users.setPassword(usersUpdateRequest.getPassword());
        if (usersUpdateRequest.getUserIsActive() != null)
            users.setUserIsActive(usersUpdateRequest.getUserIsActive());
        users.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public SuccessResponse<Object> deactivateUser(Long userId) {
        Users users = usersRepository.findById(userId).orElseThrow();
        users.setUserIsActive(false);
        users.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<UsersResponse>> getUsersByRole(RoleName role) {
        List<UsersResponse> usersResponseList=usersRepository.findUsersByRoleName(role).stream().map(usersMapper::toUsersResponseRole).toList();
        return SuccessResponse.createSuccessResponse(usersResponseList, ResponseCode.SUCCESS);
    }

}
