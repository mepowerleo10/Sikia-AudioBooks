package com.example.sikiaaudiobooks.data.model;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,16}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isValidEmail(String email) {
        /*String pattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(email);*/
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
