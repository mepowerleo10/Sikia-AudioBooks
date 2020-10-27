package com.example.sikiaaudiobooks.ui.account;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    private String TAG = AccountFragment.class.getSimpleName();

    private AccountViewModel accountViewModel;

    private TextInputEditText tfAccountName, tfAccountEmail,
            tfAccountPhone, tfAccountPassword;
    private Button loginLogoutBtn, prefsDetailsBtn, prefsHistoryBtn,
            prefsSoundSetBtn, prefsAboutBtn, saveChangesBtn;

    private View ctPrefs, tvMemberShipTitle, ctAccountName, ctAccountPhone;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        View root = inflater.inflate(R.layout.fragment_account, container, false);
//        final TextView textView = root.findViewById(R.id.text_account);
        initializeWidgets(root);
        setupWidgets(root);
        accountViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                textView.setText(s);
            }
        });


        return root;
    }

    private void initializeWidgets(View v) {
        // Text fields
        tfAccountName = v.findViewById(R.id.account_name);
        tfAccountEmail = v.findViewById(R.id.account_email);
        tfAccountPhone = v.findViewById(R.id.account_phone);
        tfAccountPassword = v.findViewById(R.id.account_password);

        // Buttons
        loginLogoutBtn = v.findViewById(R.id.prefs_login_logout);
        prefsDetailsBtn = v.findViewById(R.id.prefs_details);
        prefsHistoryBtn = v.findViewById(R.id.prefs_history);
        prefsAboutBtn = v.findViewById(R.id.prefs_about_us);
        prefsSoundSetBtn = v.findViewById(R.id.prefs_sound_settings);
        saveChangesBtn = v.findViewById(R.id.prefs_save);

        // Containers
        ctAccountName = v.findViewById(R.id.account_name_container);
        ctAccountPhone = v.findViewById(R.id.account_phone);
        ctPrefs = v.findViewById(R.id.prefs_container);

        // Text views
        tvMemberShipTitle = v.findViewById(R.id.prefs_membership_title);

    }

    @SuppressLint("ResourceAsColor")
    private void setupWidgets(View v) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        // Populating widgets with data
        tfAccountEmail.setText(user.getEmail());
        tfAccountPassword.setText(user.getUid());

        // Enabling visibility for gone widgets
        tfAccountPhone.setVisibility(View.VISIBLE);
        tfAccountPassword.setVisibility(View.VISIBLE);
        tfAccountName.setVisibility(View.VISIBLE);
        prefsSoundSetBtn.setVisibility(View.VISIBLE);
        prefsAboutBtn.setVisibility(View.VISIBLE);
        prefsHistoryBtn.setVisibility(View.VISIBLE);
        ctAccountPhone.setVisibility(View.VISIBLE);
        ctAccountName.setVisibility(View.VISIBLE);
        ctPrefs.setVisibility(View.VISIBLE);
        tvMemberShipTitle.setVisibility(View.VISIBLE);
        addListeners(user);

    }

    @SuppressLint("ResourceAsColor")
    private void addListeners(final FirebaseUser user) {
        // Add listener for all text fields
        ArrayList<View> textFieldsList = new ArrayList<>();
        textFieldsList.add(tfAccountName);
        textFieldsList.add(tfAccountEmail);
        textFieldsList.add(tfAccountPhone);
        for (View t : textFieldsList) {
            ((TextInputEditText) t).addTextChangedListener(new TextChangeListener());
        }

        // Change the login button to signout
        loginLogoutBtn.setText(R.string.account_logout);
        loginLogoutBtn.setTextColor(R.color.textRed);
        loginLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                }
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadUserDetails(user);
            }
        });
    }

    private void uploadUserDetails(FirebaseUser user) {
        String name = tfAccountName.getText().toString();
        String email = tfAccountEmail.getText().toString();
        String password = tfAccountPassword.getText().toString();

        final LoggedInUser mUser = new LoggedInUser(user.getUid());
        mUser.setName(name);
        mUser.setEmail(email);
        mUser.setPassword(password);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("name", mUser.getName());
        db.collection("USERS")
                .document(user.getUid())
                .set(mUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Update user information for: " + mUser.getEmail());
                        Toast.makeText(getContext(), R.string.account_update_success, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "User info update failed for: " + mUser.getEmail());
                        Toast.makeText(getContext(), R.string.account_update_failed, Toast.LENGTH_LONG).show();
                    }
                });
    }

    class TextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            saveChangesBtn.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}