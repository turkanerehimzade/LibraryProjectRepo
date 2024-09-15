package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.dao.entity.Authors;
import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.repository.AuthorsRepository;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dto.request.create.AuthorsCreateRequest;
import com.library.online_library_spring_app.dto.request.create.BooksCreateRequest;
import com.library.online_library_spring_app.dto.request.update.AuthorsUpdateRequest;
import com.library.online_library_spring_app.dto.response.AuthorsResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.mapper.AuthorsMapper;
import com.library.online_library_spring_app.mapper.BooksMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsService {
    private final AuthorsRepository authorsRepository;
    private final AuthorsMapper authorsMapper;
    private final BooksMapper booksMapper;
    private final BooksRepository booksRepository;

    public SuccessResponse<List<AuthorsResponse>> getAuthorsByName(String authorName) {
        List<AuthorsResponse>authorsResponseList= authorsRepository.findAuthorsByName(authorName)
                .stream()
                .map(authorsMapper::toAuthorsResponse)
                .toList();
        return SuccessResponse.createSuccessResponse(authorsResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<AuthorsResponse>> getAuthorsById(Long authorId) {
        List<AuthorsResponse> authorsResponseList= authorsRepository.findById(authorId)
                .stream()
                .map(authorsMapper::toAuthorsResponse)
                .toList();
        return SuccessResponse.createSuccessResponse(authorsResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<AuthorsResponse>> getAllAuthors() {
        List<AuthorsResponse> authorsResponseList= authorsRepository.findAll().stream().map(authorsMapper::toAuthorsResponse).toList();
    return SuccessResponse.createSuccessResponse(authorsResponseList, ResponseCode.SUCCESS);
    }

    @Transactional
    public  SuccessResponse<Object> createAuthorWithBook(AuthorsCreateRequest authorsCreateRequest) {
        Authors authors = authorsMapper.toEntity(authorsCreateRequest);
        for (BooksCreateRequest booksCreateRequest : authorsCreateRequest.getBooksCreateRequests()) {
            if (booksCreateRequest != null) {
                Books book = booksMapper.toEntity(booksCreateRequest);
                authors.addBook(book);
                booksRepository.save(book);
            }
        }
        authorsRepository.save(authors);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);

    }

    public  SuccessResponse<Object> updateAuthorById(Long id, AuthorsUpdateRequest authorsUpdateRequest) {
        Authors authors = authorsRepository.findById(id).orElseThrow();
        buildAuthorWithUpdateRequest(authors, authorsUpdateRequest);
        authorsRepository.save(authors);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public void buildAuthorWithUpdateRequest(Authors authors, AuthorsUpdateRequest authorsUpdateRequest) {
        if (authorsUpdateRequest.getName() != null)
            authors.setName(authorsUpdateRequest.getName());
        if (authorsUpdateRequest.getSurname() != null)
            authors.setSurname(authorsUpdateRequest.getSurname());
        authors.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }

    public SuccessResponse<Object> deleteAuthorById(Long id) {
        Authors authors = authorsRepository.findById(id).orElseThrow();
        authors.setAuthorsIsActive(false);
        authors.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        authorsRepository.save(authors);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
}
