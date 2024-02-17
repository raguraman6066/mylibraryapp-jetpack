package com.example.mylibraryapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
//flow is feature of coroutines - perform async actions

@Dao
interface BookDAO {

    @Insert
    suspend fun addBook(bookEntity:BookEntity)

    @Query("select * from BookEntity order by id asc")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Delete
    suspend fun deleteBook(bookEntity: BookEntity)
    
    @Update
    suspend fun updateBook(bookEntity: BookEntity)

}