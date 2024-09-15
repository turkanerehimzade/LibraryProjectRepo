package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.dao.entity.Authors;
import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.repository.AuthorsRepository;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dto.request.create.AuthorsCreateRequest;
import com.library.online_library_spring_app.dto.request.create.BooksCreateRequest;
import com.library.online_library_spring_app.dto.request.update.BooksUpdateRequest;
import com.library.online_library_spring_app.dto.response.BooksInventorResponse;
import com.library.online_library_spring_app.dto.response.BooksResponse;

import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.mapper.AuthorsMapper;
import com.library.online_library_spring_app.mapper.BooksMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;
    private final AuthorsRepository authorsRepository;
    private final AuthorsMapper authorsMapper;


    public SuccessResponse<List<BooksResponse>>  getAllBooks() {
        List<BooksResponse> booksResponseList=booksRepository.findAll()
                .stream().map(booksMapper::toBooksResponse).toList();
        return SuccessResponse.createSuccessResponse(booksResponseList,ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksResponse>> getBookById(Long id) {
        List<BooksResponse> booksResponseList= booksRepository.findById(id)
                .stream().map(booksMapper::toBooksResponse).toList();
        return SuccessResponse.createSuccessResponse(booksResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksResponse>> getBooksByAuthors(String authorName, String authorSurname) {
        List<BooksResponse> booksResponseList=booksRepository.findBooksByAuthors_NameAndAuthors_Surname(authorName, authorSurname)
                .stream()
                .map(booksMapper::toBooksResponse)
                .toList();
        return SuccessResponse.createSuccessResponse(booksResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<BooksResponse> searchBooks(String bookName) {
        BooksResponse booksResponse= booksRepository.findBooksByBookName(bookName)
                .stream().map(booksMapper::toBooksResponse).findAny().orElseThrow();
        return SuccessResponse.createSuccessResponse(booksResponse,ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksInventorResponse>> getBooksInventory() {
        List<BooksInventorResponse> booksInventorResponseList= booksRepository.findAll().stream().map(booksMapper::toBooksInventorResponse).toList();
   return SuccessResponse.createSuccessResponse(booksInventorResponseList,ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> createBookWithAuthor(BooksCreateRequest booksCreateRequest) {
//        validateBook(booksCreateRequest);
        Books books = booksMapper.toEntity(booksCreateRequest);
        AuthorsCreateRequest authorsCreateRequest = booksCreateRequest.getAuthorsCreateRequest();
        if (authorsCreateRequest != null) {
            Authors authors = authorsMapper.toEntity(authorsCreateRequest);
            books.addAuthor(authors);
            authorsRepository.save(authors);
        }
        booksRepository.save(books);
        return  SuccessResponse.createSuccessResponse(null,ResponseCode.SUCCESS);
    }
//    public void validateBook(BooksCreateRequest booksCreateRequest) {
//        if(Objects.isNull(booksCreateRequest.getBookName())){
//            throw  BaseException.of(ResponseCode.ERROR);}
//    }

    public SuccessResponse<Object> deleteBookById(Long id) {
        Books books = booksRepository.findById(id).orElseThrow();
        books.setBookIsActive(false);
        books.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        booksMapper.toBooksResponse(books);
        booksRepository.save(books);
        return SuccessResponse.createSuccessResponse(null,ResponseCode.SUCCESS);
    }

    public  SuccessResponse<Object> updateBookById(Long id, BooksUpdateRequest booksUpdateRequest) {
        Books books = booksRepository.findById(id).orElseThrow();
        buildBookWithUpdateRequest(books, booksUpdateRequest);
        booksRepository.save(books);
        return SuccessResponse.createSuccessResponse(null,ResponseCode.SUCCESS);
    }

    public void buildBookWithUpdateRequest(Books books, BooksUpdateRequest booksUpdateRequest) {
        if (booksUpdateRequest.getBookName() != null)
            books.setBookName(booksUpdateRequest.getBookName());
        if (booksUpdateRequest.getCategory() != null)
            books.setCategory(booksUpdateRequest.getCategory());
        if (booksUpdateRequest.getLanguage() != null)
            books.setLanguage(booksUpdateRequest.getLanguage());
        if (booksUpdateRequest.getBookIsActive() != null)
            books.setBookIsActive(booksUpdateRequest.getBookIsActive());
        if (booksUpdateRequest.getPageCount() != 0)
            books.setPageCount(booksUpdateRequest.getPageCount());
        if (booksUpdateRequest.getPublicationDate() != null)
            books.setPublicationDate(booksUpdateRequest.getPublicationDate());
        books.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }
}
