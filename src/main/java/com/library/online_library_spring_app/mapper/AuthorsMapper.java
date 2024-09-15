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
//  @Mapping(target = "books", source = "books")

    AuthorsResponse toAuthorsResponse(Authors authors);
    Set<AuthorsResponse> toAuthorsResponse(Set<Authors> authors);
    Authors toEntity(AuthorsCreateRequest authorsCreateRequest);
//    public static AuthorsResponse toAuthorsResponse(Authors authors) {
//        return AuthorsResponse.builder()
//                .name(authors.getName())
//                .surname(authors.getSurname())
////                .books(authors.getBooks())
//                .createdAt(authors.getCreatedAt())
//                .updatedAt(authors.getUpdatedAt())
//                .build();
//    }
}
