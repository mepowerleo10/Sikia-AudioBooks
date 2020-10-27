package com.example.sikiaaudiobooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.Book;
import com.example.sikiaaudiobooks.helpers.ImageProcessing;

import java.util.List;

public class BookCollectionGridAdapter extends BaseAdapter {

    private final String TAG = BookCollectionGridAdapter.class.getSimpleName();
    private Context context;
    private List<Book> bookList;
    private LayoutInflater inflater;

    public BookCollectionGridAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.inflater = (LayoutInflater.from(context.getApplicationContext()));
    }

    @Override
    public int getCount() {
        return bookList == null ? 0 : bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.collection_book_item, null);
        ImageView coverArt = view.findViewById(R.id.collection_books_grid_item_art);
        TextView title = view.findViewById(R.id.collection_books_grid_item_title);

        ImageProcessing ip = new ImageProcessing(context);
        ip.setCoverArt(coverArt, bookList.get(position).getCoverPath());
        title.setText(bookList.get(position).getTitle());

        return view;
    }
}
