import axios from 'axios';
import { Book } from '../common/types';

const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 5000,
});

const getAll = async (): Promise<Book[]> => {
  const response = await axiosInstance.get('/books');

  return response.data;
};

const create = async (book: Book): Promise<Book> => {
  const response = await axiosInstance.post('/books', book);

  return response.data;
};

const update = async (book: Book): Promise<Book> => {
  const response = await axiosInstance.patch(`/books/${book.id}`, book);

  return response.data;
};

const deleteById = async (id: number): Promise<Book> => {
  const response = await axiosInstance.delete(`/books/${id}`);

  return response.data;
};

export default {
  getAll,
  create,
  update,
  deleteById,
};
