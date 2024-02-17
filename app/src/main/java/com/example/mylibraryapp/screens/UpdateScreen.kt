package com.example.mylibraryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mylibraryapp.room.BookEntity
import com.example.mylibraryapp.viewmodel.BookViewModel

@Composable
fun UpdateScreen(viewModel:BookViewModel,bookId:String?){

    var inputBook by remember {
        mutableStateOf("")
    }
    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Update the existing book", fontSize = 24.sp)
        OutlinedTextField(value = inputBook, onValueChange = {enteredText->inputBook=enteredText}, label = { Text(
            text = "Update Book Name"
        )}, placeholder = { Text(text = "New Book Name")})

        Button(onClick = {
            var newBook=BookEntity(bookId!!.toInt(),inputBook)
            viewModel.updateBook(newBook)
        }, colors = ButtonDefaults.buttonColors(Color.Red)) {
             Text(text = "Update Book")
        }
    }
}