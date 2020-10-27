package com.example.sikiaaudiobooks.ui.bookdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sikiaaudiobooks.R;

public class BookDetailsFragment extends Fragment {

    private BookDetailsViewModel mViewModel;

//    private Book book;
//    private Uri coverTempPath;
//    public BookDetailsFragment(Book book, Uri coverTempPath) {
//        this.book = book;
//        this.coverTempPath = coverTempPath;
//    }

    public static BookDetailsFragment newInstance() {
        return new BookDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookDetailsViewModel.class);
        // TODO: Use the ViewModel

    }

}