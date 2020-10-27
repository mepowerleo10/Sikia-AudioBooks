package com.example.sikiaaudiobooks.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.Book;
import com.example.sikiaaudiobooks.helpers.ImageProcessing;
import com.example.sikiaaudiobooks.ui.bookdetails.BookDetailsPopup;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private FirebaseStorage store = FirebaseStorage.getInstance();
    private List<Book> books;
    private Uri coverTempPath = null;
    Context context;
    View root;

    public BookListAdapter(List<Book> books, Context context) {
        this.context = context;
        this.books = books;
    }

    final String TAG = "BINDING_LISTS";

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_book_list_item, parent, false);
        root = view;
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BookViewHolder bookViewHolder = (BookViewHolder) holder;
        bookViewHolder.titleText.setText(books.get(position).getTitle());
        bookViewHolder.authorText.setText("By " + books.get(position).getAuthor());
        bookViewHolder.coverArt.setImageURI(null);

        final Book book = books.get(position);
        ImageProcessing ip = new ImageProcessing(context);
        ip.setCoverArt(bookViewHolder.coverArt, book.getCoverPath());
        coverTempPath = Uri.fromFile(new File(context.getCacheDir(), book.getCoverPath()));
        Log.d(TAG, book.getCoverPath());

        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsPopup popupWindow = new BookDetailsPopup();
                popupWindow.showPopupWindow(v, book, coverTempPath);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Item count: " + books.size());
        return books == null ? 0 : books.size();
    }

    private class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, authorText;
        ImageView coverArt;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.list_book_title);
            authorText = itemView.findViewById(R.id.list_book_author);
            coverArt = itemView.findViewById(R.id.list_book_cover);
        }
    }
}
