package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dto.request.create.BooksCreateRequest;
import com.library.online_library_spring_app.dto.response.BooksInventorResponse;
import com.library.online_library_spring_app.dto.response.BooksResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BooksMapper {

    BooksMapper INSTANCE = Mappers.getMapper(BooksMapper.class);

//    Set<BooksResponse> toBookResponse(Set<Books> books);
//    @Mapping(target = "authors", source = "authors")

    BooksResponse toBooksResponse(Books books);
    Books toEntity(BooksCreateRequest booksCreateRequest);
    BooksInventorResponse toBooksInventorResponse(Books books);
}
