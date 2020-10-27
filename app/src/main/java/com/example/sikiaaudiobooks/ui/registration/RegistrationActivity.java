package com.example.sikiaaudiobooks.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sikiaaudiobooks.R;
import com.example.sikiaaudiobooks.data.model.Validator;
import com.example.sikiaaudiobooks.ui.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    EditText email, password, passwordConf;
    Button skipButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        skipButton = findViewById(R.id.skip_registration);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoHome = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(gotoHome);
                finish();
            }
        });

        registerButton = findViewById(R.id.register_user_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        passwordConf = findViewById(R.id.password_conf_register);

        boolean validState = true;

        if (!Validator.isValidPassword(password.getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.say_invalid_pwd, Toast.LENGTH_LONG).show();
            validState = false;
        }

        if (!passwordConf.getText().toString().equals(password.getText().toString())) {
            Toast.makeText(this, R.string.say_pwd_no_match, Toast.LENGTH_LONG).show();
            validState = false;
        }

        if (!Validator.isValidEmail(email.getText().toString())) {
            Toast.makeText(this, R.string.say_invalid_email, Toast.LENGTH_LONG).show();
            validState = false;
        }

        if (validState) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, R.string.say_error_signup, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegistrationActivity.this, R.string.say_success_signup, Toast.LENGTH_LONG).show();
                                startActivity(
                                        new Intent(RegistrationActivity.this, HomeActivity.class)
                                );
                            }
                        }
                    });
        }
    }
}