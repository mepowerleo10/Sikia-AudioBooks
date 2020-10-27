package com.example.sikiaaudiobooks.ui.bookdetails;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.Book;

public class ThankYouPopup {
    private final String TAG = ThankYouPopup.class.getSimpleName();

    public void showPopup(View view, final Book book, Uri coverPath) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.book_details_fragment, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
    }
}
