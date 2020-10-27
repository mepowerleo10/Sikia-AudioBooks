package com.example.sikiaaudiobooks.ui.bookdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sikiaaudiobooks.data.model.Book;

public class BookDetailsViewModel extends ViewModel {
    private final MutableLiveData<Book> book = new MutableLiveData<>();

    public LiveData<Book> getBook() {
        return book;
    }

    public BookDetailsViewModel() {

    }

}