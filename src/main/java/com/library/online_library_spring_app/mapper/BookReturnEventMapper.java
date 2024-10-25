package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.BookReturnEvent;
import com.library.online_library_spring_app.dto.request.create.BookReturnEventCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookReturnEventMapper {
    BookReturnEvent toBookReturnEvent(BookReturnEventCreateRequest bookReturnEventCreateRequest);
}
