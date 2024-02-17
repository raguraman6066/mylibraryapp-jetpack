package com.example.mylibraryapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "book_entity")
@Entity
data class BookEntity(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

   // @ColumnInfo(name = "book_title")
    val title:String
)
