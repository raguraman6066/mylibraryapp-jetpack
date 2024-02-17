package com.example.mylibraryapp.repository

import com.example.mylibraryapp.room.BookEntity
import com.example.mylibraryapp.room.BooksDB

class Repository (val booksDB: BooksDB) {
  suspend fun addBookToRoom(bookEntity: BookEntity){
      booksDB.bookDao().addBook(bookEntity)
  }

    fun getAllBooks()=booksDB.bookDao().getAllBooks()

     suspend fun deleteBookFromRoom(bookEntity: BookEntity){
         booksDB.bookDao().deleteBook(bookEntity = bookEntity)
     }
    suspend fun updateBook(bookEntity: BookEntity){
         booksDB.bookDao().updateBook(bookEntity = bookEntity)
     }
}