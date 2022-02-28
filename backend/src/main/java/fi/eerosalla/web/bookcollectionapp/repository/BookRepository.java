package fi.eerosalla.web.bookcollectionapp.repository;

import fi.eerosalla.web.bookcollectionapp.model.entity.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookRepository {

    /**
     * Gets all book records from the repository.
     * @return List of all books in the repository
     * @throws SQLException on database operation error
     */
    List<Book> getAll() throws SQLException;

    /**
     * Gets a book record with specific id from the repository.
     * @param id id of the book to be fetched
     * @return Optional of the result, present if found
     * @throws SQLException on database operation error
     */
    Optional<Book> queryForId(int id) throws SQLException;

    /**
     * Deletes a book record with specific id from the repository.
     * @param id id of the book to be deleted
     * @return true if the record was successfully deleted, otherwise false
     * @throws SQLException on database operation error
     */
    boolean deleteById(int id) throws SQLException;

    /**
     * Updates a book record in the repository.
     * @param book Book (with id field present) to be updated
     * @return true if the record was successfully updated, otherwise false
     * @throws SQLException on database operation error
     */
    boolean update(Book book) throws SQLException;

    /**
     * Creates a new book record in the repository.
     * Changes the id of the parameter object to match created record
     * @param book Book to be created
     * @throws SQLException on database operation error
     */
    void create(Book book) throws SQLException;

}
