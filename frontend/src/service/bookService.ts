import axios, { AxiosError, AxiosResponse } from 'axios';
import { Book, BookNotFoundError } from '../common/types';

const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 5000,
});

// throws BookNotFoundError instead of AxiosError if the book wasn't found
const handleNotFound = async (axiosPromise: Promise<AxiosResponse>, message: string):
  Promise<AxiosResponse> => {
  let response;
  try {
    response = await axiosPromise;
  } catch (e) {
    const axiosError = e as AxiosError;
    if (axiosError && axiosError.response && axiosError.response.status === 404) {
      throw new BookNotFoundError(message);
    }

    throw e;
  }

  return response;
};

const getAll = async (): Promise<Book[]> => {
  const response = await axiosInstance.get('/books');

  return response.data;
};

const create = async (book: Book): Promise<Book> => {
  const response = await axiosInstance.post('/books', book);

  return response.data;
};

const update = async (book: Book): Promise<Book> => {
  const response = await handleNotFound(
    axiosInstance.patch(`/books/${book.id}`, book),
    'Book could not be found on the server. '
    + 'You can create a new entry for the book if you want to save it.',
  );

  return response.data;
};

const deleteById = async (id: number): Promise<Book> => {
  const response = await handleNotFound(
    axiosInstance.delete(`/books/${id}`),
    'Book could not be found on the server. ',
  );

  return response.data;
};

export default {
  getAll,
  create,
  update,
  deleteById,
};
