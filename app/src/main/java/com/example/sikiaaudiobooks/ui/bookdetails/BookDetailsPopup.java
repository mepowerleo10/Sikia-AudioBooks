package com.example.sikiaaudiobooks.ui.bookdetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.Book;
import com.example.sikiaaudiobooks.helpers.DataAccess;
import com.example.sikiaaudiobooks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class BookDetailsPopup {
    private final String TAG = BookDetailsPopup.class.getSimpleName();

    public void showPopupWindow(final View view, final Book book, Uri coverPath) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.book_details_fragment, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        Button closeBtn = popupView.findViewById(R.id.popup_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        TextView authorTxt = popupView.findViewById(R.id.details_book_author);
        TextView titleTxt = popupView.findViewById(R.id.details_book_title);
        TextView narratorTxt = popupView.findViewById(R.id.details_book_narrator);
        TextView priceTxt = popupView.findViewById(R.id.details_book_price);
        ImageView coverArt = popupView.findViewById(R.id.details_book_cover);

        authorTxt.setText("By: " + book.getAuthor());
        titleTxt.setText(book.getTitle());
        narratorTxt.setText("Narrated by: " + book.getNarrator());
        priceTxt.setText("Tshs. " + book.getPrice() + "/=");
        coverArt.setImageURI(coverPath);

        // Setup buy button
        Button buyBtn = popupView.findViewById(R.id.details_button_buy);

        // Does the user own the book already?
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final boolean[] owns = {false};
        DataAccess.inPurchaseHistory(user.getUid(), book.getId(), owns);
        if ((user != null) && owns[0]) {
            buyBtn.setEnabled(false);
            Drawable checkMark = view.getContext().getResources().getDrawable(R.drawable.ic_baseline_check_24);
            buyBtn.setCompoundDrawables(checkMark, null, null, null);
            Log.d(TAG, "Buy disabled");
        } else {
            // Process buy request
            buyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Is the user logged in?
                    if (user == null) {
                        Intent gotoLogin = new Intent(view.getContext(), LoginActivity.class);
                        gotoLogin.putExtra("BUY_REDIRECT", 1);
                        view.getContext().startActivity(gotoLogin);
                    }
                    int[] i = new int[1];
                    DataAccess.doPurchase(book.getId(), new Date(), i);
                    Log.d(TAG, "Purchase exited with code: " + i[0]);
                }
            });
        }

    }
}
