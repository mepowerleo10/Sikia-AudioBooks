package com.example.sikiaaudiobooks.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.adapters.BookCollectionGridAdapter;
import com.example.sikiaaudiobooks.data.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class CollectionFragment extends Fragment {

    private CollectionViewModel collectionViewModel;
    private List<Book> bookList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);

        GridView collectionGrid = root.findViewById(R.id.collection_books_grid);
        BookCollectionGridAdapter collectionAdapter =
                new BookCollectionGridAdapter(getContext(), bookList);
        collectionGrid.setAdapter(collectionAdapter);
        collectionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return root;
    }

    private void fetchMyBooks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    }
}