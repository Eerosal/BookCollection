export interface Book {
  id?: number,
  title: string,
  author: string,
  description: string
}

export class BookNotFoundError extends Error {
}
