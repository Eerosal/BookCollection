package fi.eerosalla.web.bookcollectionapp.controller;

import fi.eerosalla.web.bookcollectionapp.model.entity.Book;
import fi.eerosalla.web.bookcollectionapp.model.form.BookForm;
import fi.eerosalla.web.bookcollectionapp.validation.AllValuesOptionalGroup;
import fi.eerosalla.web.bookcollectionapp.validation.AllValuesRequiredGroup;
import fi.eerosalla.web.bookcollectionapp.model.response.ErrorResponse;
import fi.eerosalla.web.bookcollectionapp.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public ResponseEntity<?> getAllBooks() throws SQLException {
        return new ResponseEntity<>(
            bookRepository.getAll(),
            HttpStatus.OK
        );
    }

    @PostMapping("/api/books")
    public ResponseEntity<?> createBook(
        final @RequestBody @Validated(AllValuesRequiredGroup.class)
            BookForm bookForm
    ) throws SQLException {
        Book book = new Book();
        book.setTitle(bookForm.getTitle());
        book.setAuthor(bookForm.getAuthor());
        book.setDescription(bookForm.getDescription());

        bookRepository.create(book);

        return new ResponseEntity<>(
            book,
            HttpStatus.CREATED
        );
    }

    @PatchMapping("/api/books/{bookId}")
    public ResponseEntity<?> editBook(
        final @ModelAttribute Book book,
        final @RequestBody @Validated(AllValuesOptionalGroup.class)
            BookForm bookForm
    ) throws SQLException {
        if (bookForm.getTitle() != null) {
            book.setTitle(bookForm.getTitle());
        }

        if (bookForm.getAuthor() != null) {
            book.setAuthor(bookForm.getAuthor());
        }

        if (bookForm.getDescription() != null) {
            book.setDescription(bookForm.getDescription());
        }

        if (!bookRepository.update(book)) {
            return new ResponseEntity<>(
                new ErrorResponse("Unknown error occurred - book edit failed"),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return new ResponseEntity<>(
            book,
            HttpStatus.OK
        );
    }

    @DeleteMapping("/api/books/{bookId}")
    public ResponseEntity<?> deleteBook(
        final @ModelAttribute Book book
    ) throws SQLException {
        if (!bookRepository.deleteById(book.getId())) {
            return new ResponseEntity<>(
                new ErrorResponse(
                    "Unknown error occurred - book deletion failed"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return new ResponseEntity<>(
            book,
            HttpStatus.OK
        );
    }

}
