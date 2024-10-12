package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.ReservationRepository;
import com.library.online_library_spring_app.dto.response.RentalHistoryResponse;
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
import com.library.online_library_spring_app.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;
    private final BooksMapper booksMapper;
    private final AuthorsRepository authorsRepository;
    private final AuthorsMapper authorsMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;


    public SuccessResponse<List<BooksResponse>> getAllBooks() {
        List<BooksResponse> booksResponseList = booksRepository.findAll()
                .stream().map(booksMapper::toBooksResponse).toList();
        return SuccessResponse.createSuccessResponse(booksResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksResponse>> getBookById(Long id) {
        List<BooksResponse> booksResponseList = booksRepository.findById(id)
                .stream().map(booksMapper::toBooksResponse).toList();
        return SuccessResponse.createSuccessResponse(booksResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksResponse>> getBooksByAuthors(String authorName, String authorSurname) {
        List<BooksResponse> booksResponseList = booksRepository.findBooksByAuthors_NameAndAuthors_Surname(authorName, authorSurname)
                .stream()
                .map(booksMapper::toBooksResponse)
                .toList();
        return SuccessResponse.createSuccessResponse(booksResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<BooksResponse> searchBooks(String bookName) {
        BooksResponse booksResponse = booksRepository.findBooksByBookName(bookName)
                .stream().map(booksMapper::toBooksResponse).findAny().orElseThrow();
        return SuccessResponse.createSuccessResponse(booksResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<BooksInventorResponse>> getBooksInventory() {
        List<BooksInventorResponse> booksInventorResponseList = booksRepository.findAll().stream().map(booksMapper::toBooksInventorResponse).toList();
        return SuccessResponse.createSuccessResponse(booksInventorResponseList, ResponseCode.SUCCESS);
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
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
//    public void validateBook(BooksCreateRequest booksCreateRequest) {
//        if(Objects.isNull(booksCreateRequest.getBookName())){
//            throw  BaseException.of(ResponseCode.ERROR);}
//    }

    public SuccessResponse<Object> deleteBookById(Long id) {
        Books books = booksRepository.findById(id).orElseThrow();
        books.setBookIsActive(false);
        books.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
//        booksMapper.toBooksResponse(books);
        booksRepository.save(books);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> updateBookById(Long id, BooksUpdateRequest booksUpdateRequest) {
        Books books = booksRepository.findById(id).orElseThrow();
        buildBookWithUpdateRequest(books, booksUpdateRequest);
        booksRepository.save(books);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
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
    @Transactional // Bu annotasiyanı əlavə edin

    public SuccessResponse<Object> removeBookFromAuthor(String authorName, String authorSurname) {
        List<Books> booksList =booksRepository.findBooksByAuthors_NameAndAuthors_Surname(authorName, authorSurname).stream()
                .peek(books -> {

                    books.setBookIsActive(false);
                    books.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                }).toList();

        booksRepository.saveAll(booksList);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
    public SuccessResponse<List<BooksResponse>> getMostReadBooks(int limit) {
        Pageable pageable = PageRequest.of(0, limit); // İlk səhifə (0), limit sayda nəticə (10)
        List<BooksResponse> bookResponses = reservationRepository.findMostReadBooks(pageable).stream()
                .map( booksMapper::toBooksResponse )
                .collect(Collectors.toList());

        return SuccessResponse.createSuccessResponse(bookResponses, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<RentalHistoryResponse>> getBookRentalHistory(Long bookId) {
        List<RentalHistoryResponse>rentalHistoryResponses=reservationRepository.findReservationByBookId(bookId).stream().map(reservationMapper::toRentalHistoryResponse).toList();
        return SuccessResponse.createSuccessResponse(rentalHistoryResponses,ResponseCode.SUCCESS);
    }
}
