import React, { useEffect, useRef, useState } from 'react';
import './App.css';
import { ToastContainer, toast } from 'react-toastify';
import { Book, BookNotFoundError } from './common/types';
import bookService from './service/bookService';

import 'react-toastify/dist/ReactToastify.css';

function App() {
  const formRef = useRef<HTMLFormElement>(null);
  const [author, setAuthor] = useState<string>('');
  const [title, setTitle] = useState<string>('');
  const [description, setDescription] = useState<string>('');

  const [bookList, setBookList] = useState<Book[]>([]);
  const [selectedBookId, setSelectedBookId] = useState<number>(-1);

  const showError = (toastId: React.ReactText, error: any) => {
    let message;
    if ('response.data.error' in error) {
      message = `Api error: ${(error as any).response.data.error}` as string;
    } else {
      message = `${error}`;
    }

    toast.update(toastId, {
      render: message,
      type: 'error',
      isLoading: false,
      autoClose: 5000,
    });
  };

  const showSuccess = (toastId: React.ReactText, message: string) => {
    toast.update(toastId, {
      render: message,
      type: 'success',
      isLoading: false,
      autoClose: 2500,
    });
  };

  const validateLength = (key: string, value: string, min: number, max: number): string => {
    if (value.length >= min && value.length <= max) {
      return value;
    }

    throw new Error(`${key} length must be ${min}-${max} characters`);
  };

  const getEditorBook = (): Book => ({
    title: validateLength('Title', title, 1, 255),
    author: validateLength('Author', author, 1, 255),
    description: validateLength('Description', description, 0, 65535),
  } as Book);

  const setEditorBook = (book: Book) => {
    setSelectedBookId(book.id ?? -1);

    setAuthor(book.author);
    setTitle(book.title);
    setDescription(book.description);
  };

  const updateBookList = async () => {
    const toastId = toast.loading('Updating book list...');

    try {
      const newList = await bookService.getAll();

      setBookList(newList);
    } catch (e) {
      showError(toastId, e);
      return;
    }

    toast.dismiss(toastId);
  };

  const saveNewHandler = async () => {
    const toastId = toast.loading('Saving...');

    try {
      const book = getEditorBook();

      const bookWithId = await bookService.create(book);

      setBookList([
        ...bookList,
        bookWithId,
      ]);

      setSelectedBookId(bookWithId.id ?? -1);
    } catch (e) {
      showError(toastId, e);

      return;
    }

    showSuccess(toastId, 'New book was saved');
  };

  const saveHandler = async () => {
    const toastId = toast.loading('Saving...');

    try {
      const book = getEditorBook();

      book.id = selectedBookId;

      await bookService.update(book);

      setBookList((currentList) => currentList.map((listBook) => {
        if (listBook.id === book.id) {
          return book;
        }

        return listBook;
      }));
    } catch (e) {
      showError(toastId, e);

      if (e instanceof BookNotFoundError) {
        setSelectedBookId(-1);
        await updateBookList();
      }
      return;
    }

    showSuccess(toastId, 'Book was updated');
  };

  const deleteHandler = async () => {
    const toastId = toast.loading('Deleting...');

    try {
      await bookService.deleteById(selectedBookId);

      setBookList(
        (currentList) => currentList.filter((listBook) => listBook.id !== selectedBookId),
      );
    } catch (e) {
      showError(toastId, e);

      if (e instanceof BookNotFoundError) {
        await updateBookList();
      }
      return;
    } finally {
      setSelectedBookId(-1);
    }

    showSuccess(toastId, 'Book was deleted');
  };

  useEffect(() => {
    updateBookList()
      .then();
  }, []);

  return (
    <>
      <div className="app">
        <div className="app-section">
          <form
            onSubmit={(event) => event.preventDefault()}
            ref={formRef}
          >
            <label htmlFor="editor-input-author">
              Author
              <br />
              <input
                name="author"
                type="text"
                id="editor-input-author"
                className="editor-input"
                maxLength={255}
                value={author}
                onChange={(event) => setAuthor(event.target.value)}
              />
            </label>

            <br />

            <label htmlFor="editor-input-title">
              Title
              <br />
              <input
                name="title"
                type="text"
                id="editor-input-title"
                className="editor-input"
                maxLength={255}
                value={title}
                onChange={(event) => setTitle(event.target.value)}
              />
            </label>

            <br />

            <label htmlFor="editor-input-description">
              Description
              <br />
              <textarea
                name="description"
                id="editor-input-description"
                className="editor-input editor-input-description"
                maxLength={65535}
                value={description}
                onChange={(event) => setDescription(event.target.value)}
              />
            </label>
            <button
              type="submit"
              className="editor-button"
              onClick={saveNewHandler}
            >
              Save New
            </button>

            <button
              type="submit"
              className="editor-button"
              disabled={selectedBookId === -1}
              onClick={saveHandler}
            >
              Save
            </button>

            <button
              type="submit"
              className="editor-button"
              disabled={selectedBookId === -1}
              onClick={deleteHandler}
            >
              Delete
            </button>
          </form>
        </div>
        <div className="app-section">
          <h3>Book list</h3>
          <div className="book-list-container">
            <ul role="radiogroup" className="book-list">
              {
                bookList.map((book) => (
                  <li
                    key={book.id}
                    className={
                      book.id === selectedBookId
                        ? 'book-list-entry book-list-entry-selected'
                        : 'book-list-entry'
                    }
                  >
                    <div
                      tabIndex={0}
                      onClick={() => setEditorBook(book)}
                      onKeyDown={(event) => {
                        if (event.key === 'Enter') {
                          setEditorBook(book);
                        }
                      }}
                      role="radio"
                      aria-checked={book.id === selectedBookId}
                    >
                      <b>{book.title}</b>
                      <br />
                      by
                      {' '}
                      {book.author}
                    </div>
                  </li>
                ))
              }
            </ul>
          </div>
        </div>
      </div>
      <ToastContainer
        role="alert"
        position="top-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="dark"
      />
    </>
  );
}

export default App;
