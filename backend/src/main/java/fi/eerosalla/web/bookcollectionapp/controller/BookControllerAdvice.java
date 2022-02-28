package fi.eerosalla.web.bookcollectionapp.controller;

import fi.eerosalla.web.bookcollectionapp.model.entity.Book;
import fi.eerosalla.web.bookcollectionapp.model.response.ErrorResponse;
import fi.eerosalla.web.bookcollectionapp.repository.BookRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice(assignableTypes = {BookController.class})
public class BookControllerAdvice {

    private static class BookNotFoundException extends Exception {
    }

    private final BookRepository bookRepository;

    public BookControllerAdvice(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @ModelAttribute
    public void injectBook(
            final @PathVariable Map<String, String> pathVariables,
            final Model model,
            final HttpMethod httpMethod
    ) throws SQLException, BookNotFoundException {
        String bookIdStr = pathVariables.get("bookId");
        if (bookIdStr != null) {
            int bookId = Integer.parseInt(bookIdStr);

            if (httpMethod.equals(HttpMethod.PUT)) {
                model.addAttribute("bookId", bookId);
            } else {
                Optional<Book> result = bookRepository.queryForId(bookId);

                if (result.isPresent()) {
                    model.addAttribute("book", result.get());
                } else {
                    throw new BookNotFoundException();
                }

            }
        }
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> handleBoardNotFoundException() {
        return new ResponseEntity<>(
                new ErrorResponse("Book not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(final SQLException ex) {
        ex.printStackTrace();

        return new ResponseEntity<>(
                new ErrorResponse("Unknown database error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
