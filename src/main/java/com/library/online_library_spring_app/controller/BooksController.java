package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.BooksCreateRequest;
import com.library.online_library_spring_app.dto.request.update.BooksUpdateRequest;
import com.library.online_library_spring_app.dto.response.BooksInventorResponse;
import com.library.online_library_spring_app.dto.response.BooksResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.BooksService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//createBook:+
//updateBook:+
//deleteBook : +
//getBookById :+
//getAllBooks: +
// getBooksByAuthor: +
//searchBooks :+ (kicik herf)
//filterBooks
//getBookInventory: Kitabların mövcud sayını izləyir və göstərir. + //eyni olmasin bide null

@RestController//jsona chevirecek
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Books Controller", description = "Books management operations")
public class BooksController {
    private final BooksService booksService;

    @GetMapping("/by-authors/{authorName}/{authorSurname}")
    public SuccessResponse<List<BooksResponse>> getBooksByAuthors(@PathVariable("authorName") String authorName,
                                                                  @PathVariable("authorSurname") String authorSurname) {
        return booksService.getBooksByAuthors(authorName, authorSurname);

    }

    @GetMapping("/inventory")
    public SuccessResponse<List<BooksInventorResponse>> getBooksInventory() {
        return booksService.getBooksInventory();
    }
//    @Operation(summary = "Get all books", description = "Returns a list of all available books",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
//                    @ApiResponse(responseCode = "500", description = "Internal server error")
//            })
    @GetMapping("/all-book")
    public SuccessResponse<List<BooksResponse>> getAllBooks() {//bunu
        return booksService.getAllBooks();
    }

    @GetMapping("/book-id/{bookId}")
    public SuccessResponse<List<BooksResponse>> getBookById(@PathVariable("bookId") Long bookId) {
        return booksService.getBookById(bookId);
    }

    @GetMapping("/book-name/{bookName}")
    public SuccessResponse<BooksResponse> searchBooks(@PathVariable("bookName") String bookName) {
        return booksService.searchBooks(bookName);
    }

    @PostMapping("/add-book")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Object> createBook(@RequestBody @Valid BooksCreateRequest booksCreateRequest) {
        return booksService.createBookWithAuthor(booksCreateRequest);
    }

    @DeleteMapping("/delete-book/{id}")
    public SuccessResponse<Object> deleteBookById(@PathVariable("id") Long id) {

        return booksService.deleteBookById(id);
    }

    @PutMapping("/update-book/{id}")
    public SuccessResponse<Object> updateBookById(@PathVariable("id") Long id, @RequestBody BooksUpdateRequest booksUpdateRequest) {
        return booksService.updateBookById(id, booksUpdateRequest);
    }
    @PutMapping("/remove-book/{authorName}/{authorSurname}")
    public SuccessResponse<Object> removeBookFromAuthor(@PathVariable("authorName")String authorName, @PathVariable("authorSurname")String authorSurname) {
        return booksService.removeBookFromAuthor(authorName,authorSurname);
    }
}
