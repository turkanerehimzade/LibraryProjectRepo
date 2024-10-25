package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.Authors;
import com.library.online_library_spring_app.dto.request.create.AuthorsCreateRequest;
import com.library.online_library_spring_app.dto.response.AuthorsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthorsMapper {

    AuthorsMapper INSTANCE = Mappers.getMapper(AuthorsMapper.class);

    AuthorsResponse toAuthorsResponse(Authors authors);
    Set<AuthorsResponse> toAuthorsResponse(Set<Authors> authors);
    Authors toEntity(AuthorsCreateRequest authorsCreateRequest);

}
