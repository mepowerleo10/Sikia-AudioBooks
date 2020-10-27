package com.example.sikiaaudiobooks.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.adapters.BookListAdapter;
import com.example.sikiaaudiobooks.data.model.Book;
import com.example.sikiaaudiobooks.data.model.Chapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ProgressDialog progressDialog;
    List<Book> booksList;
    RecyclerView booksListRecycler;
    BookListAdapter booksListAdapter;
    LinearLayoutManager linearLayoutManager;
    ImageView bookOfTheDayImg;

    final String TAG = HomeFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        linearLayoutManager = new LinearLayoutManager(root.getContext());
        booksListRecycler = root.findViewById(R.id.recycler_books_list);
        booksListRecycler.setLayoutManager(linearLayoutManager);
        booksListRecycler.setHasFixedSize(true);
        fetchData();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Toast.makeText(getContext(), TAG, Toast.LENGTH_LONG).show();
        if (user == null) {
            Log.d(TAG, "User is not logged in");
        } else {
            Log.d(TAG, "Logged in with email: " + user.getEmail() + ", phoneNo: " + user.getPhoneNumber());
        }

        return root;

    }

    private void initAdapter() {
        booksListAdapter = new BookListAdapter(booksList, getActivity());
        Log.d(TAG, "Sending to TestAdapter: " + booksList.get(0).getTitle());
        booksListRecycler.setAdapter(booksListAdapter);
//        File path = new File(getContext().getCacheDir(), booksList.get(0).getCoverPath());
//        Log.d(TAG, path.getAbsolutePath());
    }

    private void fetchData() {
        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("BOOKS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            final List<Book> mBooksList = new ArrayList<>();
                            int i = -1;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<Chapter> chapters = (List<Chapter>) document.get("chapters");
                                Book book = new Book();
                                book.setAuthor((String) document.get("author"));
                                book.setTitle((String) document.get("title"));
                                book.setGenre((String) document.get("genre"));
                                book.setId(document.getId());
                                book.setLanguage((String) document.get("language"));
                                book.setPrice((Long) document.get("price"));
                                book.setRating((Double) document.get("rating"));
                                book.setSize((Long) document.get("size"));
                                book.setNarrator((String) document.get("narrator"));
                                book.setCoverPath((String) document.get("cover_path"));
                                book.setChapters(chapters);
                                mBooksList.add(book);
                                Log.d(TAG, "Fetched " + mBooksList.get(++i));
                            }
                            booksList = mBooksList;
                            initAdapter();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Firestore", "Error getting documents.", e);
            }

        }); // End of firestore fetch

//        return mBooksList;
    }
}