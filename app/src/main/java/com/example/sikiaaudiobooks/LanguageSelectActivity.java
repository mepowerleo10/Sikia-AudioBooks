package com.example.sikiaaudiobooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sikiaaudiobooks.ui.home.HomeActivity;
import com.example.sikiaaudiobooks.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LanguageSelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private final String TAG = LanguageSelectActivity.class.getSimpleName();


    class CustomArrayAdapter<T> extends ArrayAdapter<T> {
        public CustomArrayAdapter(@NonNull Context context, T[] objects) {
            super(context, android.R.layout.simple_spinner_dropdown_item, objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            TextView textView = v.findViewById(android.R.id.text1);
            textView.setTextColor(getResources().getColor(R.color.darkerOlive));

            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_language_select);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "User with email: " + user.getEmail() + " found!");
            startActivity(new Intent(LanguageSelectActivity.this, HomeActivity.class));
        }

        Log.d(TAG, "No logged in user");

        Spinner langSelector = findViewById(R.id.lang_choice_splash);
        ArrayAdapter<CharSequence> langSelectAdapter =
                CustomArrayAdapter.createFromResource(this, R.array.lang_choices, android.R.layout.simple_spinner_item);
        langSelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSelector.setAdapter(langSelectAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        nextPage(null);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void nextPage(View view) {
        Intent gotoLogin = new Intent(LanguageSelectActivity.this, LoginActivity.class);
        startActivity(gotoLogin);
        finish();
    }
}