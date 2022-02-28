package fi.eerosalla.web.bookcollectionapp.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fi.eerosalla.web.bookcollectionapp.model.entity.Book;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final Dao<Book, Integer> dao;

    public BookRepositoryImpl(
        final ConnectionSource connectionSource) throws SQLException {
        this.dao = DaoManager.createDao(connectionSource, Book.class);

        TableUtils.createTableIfNotExists(connectionSource, Book.class);
    }

    @Override
    public List<Book> getAll() throws SQLException {
        return this.dao.queryForAll();
    }

    @Override
    public Optional<Book> queryForId(final int id) throws SQLException {
        return Optional.ofNullable(
            this.dao.queryForId(id)
        );
    }

    @Override
    public boolean deleteById(final int id) throws SQLException {
        return this.dao.deleteById(id) == 1;
    }

    @Override
    public boolean update(final Book book) throws SQLException {
        return this.dao.update(book) == 1;
    }

    @Override
    public void create(final Book book) throws SQLException {
        this.dao.create(book);
    }
}
