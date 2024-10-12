package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dto.request.create.UsersCreateRequest;
import com.library.online_library_spring_app.dto.response.UsersResponse;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersResponse toUsersResponse(Users users);
    Users toUsers(UsersCreateRequest usersCreateRequest);
    UsersResponse toUsersResponseRole(Users users);
}
