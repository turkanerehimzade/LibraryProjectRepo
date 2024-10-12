package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.AuthorsCreateRequest;
import com.library.online_library_spring_app.dto.request.update.AuthorsUpdateRequest;
import com.library.online_library_spring_app.dto.response.AuthorsResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.AuthorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//addAuthor:  +
//updateAuthor: +
//deleteAuthor: +
//getAuthorById:+ ----isactive
//getAllAuthors:+
//getAuthorByName: +
//removeBookFromAuthor:


@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorsService authorsService;

    @GetMapping("/author-name/{authorName}")
    public SuccessResponse<List<AuthorsResponse>> getAuthorsByName(@PathVariable("authorName") String authorName) {
        return authorsService.getAuthorsByName(authorName);
    }

    @GetMapping("/author-id/{authorId}")
    public SuccessResponse<List<AuthorsResponse>> getAuthorsById(@PathVariable("authorId") Long authorId) {
        return authorsService.getAuthorsById(authorId);
    }

    @GetMapping
    public SuccessResponse<List<AuthorsResponse>> getAuthors() {
        return authorsService.getAllAuthors();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Object> createAuthors(@RequestBody AuthorsCreateRequest authorsCreateRequest) {
        return authorsService.createAuthorWithBook(authorsCreateRequest);
    }

    @PutMapping("/{id}")
    public SuccessResponse<Object> updateAuthorById(@PathVariable("id") Long id, @RequestBody AuthorsUpdateRequest authorsUpdateRequest) {
        return authorsService.updateAuthorById(id, authorsUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Object> deleteAuthorById(@PathVariable("id") Long id) {
        return authorsService.deleteAuthorById(id);
    }


}
