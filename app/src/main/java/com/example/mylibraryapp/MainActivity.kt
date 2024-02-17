package com.example.mylibraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mylibraryapp.repository.Repository
import com.example.mylibraryapp.room.BookEntity
import com.example.mylibraryapp.room.BooksDB
import com.example.mylibraryapp.screens.UpdateScreen
import com.example.mylibraryapp.ui.theme.MyLibraryAppTheme
import com.example.mylibraryapp.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyLibraryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mContext= LocalContext.current
                    val db=BooksDB.getInstance(mContext)
                    val repository=Repository(db)
                    val myViewModel=BookViewModel(repository = repository)
                    //navigation
                    var navController= rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen", ){
                        composable("MainScreen"){
                            MainScreen(viewModel =myViewModel,navController )
                        }

                        composable("UpdateScreen/{bookId}"){
                            UpdateScreen(viewModel = myViewModel, bookId = it.arguments?.getString("bookId"))
                        }
                    }


                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel:BookViewModel,navController: NavHostController){
    var inputBook by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 22.dp, start = 6.dp, end = 6.dp)) {

        Text(text = "Insert Books in ROOM DB", fontSize = 22.sp)
        OutlinedTextField(value = inputBook, onValueChange = {
                      enteredText->inputBook=enteredText
        },
            label = { Text(text = "Book name")},
            placeholder = {Text(text = "Enter your book name")})
        
        Button(onClick = {
            viewModel.addBook(BookEntity(0,inputBook))
        }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text(text = "Insert Book into DB")
        }

        //the books list
        BooksList(viewModel = viewModel,navController)

    }
}

@Composable
fun BookCard(viewModel: BookViewModel,book:BookEntity,navController: NavHostController){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "" + book.id, fontSize = 24.sp, modifier = Modifier.padding(start = 4.dp, end = 4.dp), color = Color.Blue)
            Text(text = book.title, fontSize = 24.sp, modifier =Modifier.fillMaxSize(0.7f),)

         Row(horizontalArrangement = Arrangement.End) {
             IconButton(onClick = {viewModel.deleteBook(book = book)}) {
                 Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
             }

             IconButton(onClick = {
                 navController.navigate("UpdateScreen/${book.id}")
             }) {
                 Icon(imageVector = Icons.Default.Edit, contentDescription ="Edit" )

             }
         }
        }
    }
}

@Composable
fun BooksList(viewModel: BookViewModel,navController: NavHostController){
    val books by viewModel.books.collectAsState(initial = emptyList())

    Column(Modifier.padding(16.dp)) {
        Text(text = "My Library", fontSize = 24.sp,color=Color.Red)
        LazyColumn {
            items(books) { item ->
                BookCard(viewModel = viewModel, book = item, navController)
            }
        }
    }
}



