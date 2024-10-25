package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Role;
import com.library.online_library_spring_app.dao.entity.UserRole;
import com.library.online_library_spring_app.dao.repository.RoleRepository;
import com.library.online_library_spring_app.dao.repository.UserRoleRepository;
import com.library.online_library_spring_app.dto.request.SignUpRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.library.online_library_spring_app.security.SecurityConfig.encoder;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;

    public SuccessResponse<List<UsersResponse>> getAllUsers() {
        List<UsersResponse> usersResponseList = usersRepository.findAll().stream().map(usersMapper::toUsersResponse).toList();
        return SuccessResponse.createSuccessResponse(usersResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<UsersResponse> getUserById(Long userId) {
        UsersResponse usersResponse = usersRepository.findById(userId).map(usersMapper::toUsersResponse)
                .orElseThrow(()->new RuntimeException("User with this id not found"));
        return SuccessResponse.createSuccessResponse(usersResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<UsersResponse> getUserByUsername(String username) {
        UsersResponse usersResponse = usersRepository.findByUsername(username)
                .map(usersMapper::toUsersResponse)
                .orElseThrow(()->new RuntimeException("User with this username not found"));
        return SuccessResponse.createSuccessResponse(usersResponse, ResponseCode.SUCCESS);
    }

    public Users findUserByUsername(String username) {
        return usersRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User with this username not found"));
    }

    public SuccessResponse<Object> deleteUserById(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));
        users.setUserIsActive(false);
        users.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        usersMapper.toUsersResponse(users);
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> createUser(UsersCreateRequest usersCreateRequest) {
        checkUsernameExist(usersCreateRequest.getUsername());
        Users users = usersMapper.toUsers(usersCreateRequest);
        users.setPassword(encoder().encode(usersCreateRequest.getPassword()));
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setRoleName(RoleName.USER);
        roles.add(role);
        roleRepository.save(role);
        users.setRoles(roles);
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }


    public SuccessResponse<Object> updateUser(Long id, UsersUpdateRequest usersUpdateRequest) {
        Users users = usersRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
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
        Users users = usersRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        users.setUserIsActive(false);
        users.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        usersRepository.save(users);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<UsersResponse>> getUsersByRole(RoleName role) {
        List<UsersResponse> usersResponseList = usersRepository.findUsersByRoleName(role).stream().map(usersMapper::toUsersResponseRole).toList();
        return SuccessResponse.createSuccessResponse(usersResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> registerUser(SignUpRequest signUpRequest) {
        checkUsernameExist(signUpRequest.getUsername());
        Users user = new Users();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder().encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setAddress(signUpRequest.getAddress());
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        List<Role> roles = new ArrayList<>();
        roles.add(signUpRequest.getRole());
        user.setRoles(roles);
        usersRepository.save(user);

        return SuccessResponse.createSuccessResponse("User registered successfully!", ResponseCode.SUCCESS);
    }

    public Object checkUsernameExist(String username) {
        Users users = usersRepository.findByUsername(username).orElse(null);
        if (Objects.isNull(users)) {
            return username;
        } else {
            throw new RuntimeException("Username already exists!");
        }

    }
}
