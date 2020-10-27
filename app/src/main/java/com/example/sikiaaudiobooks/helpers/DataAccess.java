package com.example.sikiaaudiobooks.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sikiaaudiobooks.data.model.LoggedInUser;
import com.example.sikiaaudiobooks.data.model.Purchase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataAccess {
    private static final String TAG = DataAccess.class.getSimpleName();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference purchases = db.collection("PURCHASES");
    private static CollectionReference books = db.collection("BOOKS");
    private static CollectionReference users = db.collection("USERS");
    private static final String BOOKID_FIELD = "bookId", USERID_FIELD = "userId";

    public static int SUCCESS = 0,
            FAIL = 1,
            NO_SUCH_USER = 2,
            NO_SUCH_BOOK = 3,
            ALREADY_OWNED = 4;

    public DataAccess() {
    }

    public static LoggedInUser fetchUser(final FirebaseUser user) {
        DocumentReference docRef = users.document(user.getUid());
        final LoggedInUser[] loggedInUser = new LoggedInUser[1];
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        Log.d(TAG, "Fetched user: " + user.getEmail());
                        loggedInUser[0] = document.toObject(LoggedInUser.class);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed to fetch user: " + user.getEmail());
            }
        });
        return loggedInUser[0];
    }

    public static List<Purchase> fetchPurchases(final String userId) {
        Query query = purchases.whereEqualTo("userId", userId);
        final List<Purchase> purchaseList = new ArrayList<>();
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocuments) {
                if (!queryDocuments.isEmpty()) {
                    for (DocumentSnapshot document : queryDocuments) {
                        Purchase p = document.toObject(Purchase.class);
                        purchaseList.add(p);
                        SimpleDateFormat f = new SimpleDateFormat("EEE, MMM dd. yyyy. -- H:mm aa");
                        Log.d(TAG, String.format("Fetched purchase with, id: %s, userId: %s, bookId: %s, date: %s: ",
                                document.getId(), p.getUserId(), p.getBookId(), f.format(p.getDate())));
                    }
                } else {
                    Log.d(TAG, "User " + userId + ", doesn't have purchases");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });

        return purchaseList;
    }

    public static void doPurchase(final String bookId, final Date date, final int[] retVal) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            retVal[0] = NO_SUCH_USER;
            return;
        }
        DocumentReference docRef = books.document(bookId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                if (d != null) {
                    Purchase p = new Purchase(user.getUid(), bookId, date, (Long) d.get("price"));
                    addToPurchaseCollection(p, retVal);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "No such book: " + bookId);
                retVal[0] = NO_SUCH_BOOK;
            }
        });
    }

    public static void addToPurchaseCollection(final Purchase purchase, final int[] retVal) {
        final boolean[] owns = {false};
        inPurchaseHistory(purchase.getUserId(), purchase.getBookId(), owns);

        if (!owns[0]) {
            purchases.add(purchase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "Successfully purchased: " + purchase.getBookId());
                    retVal[0] = ALREADY_OWNED;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    retVal[0] = FAIL;
                    Log.d(TAG, "Failed to purchase: " + purchase.getBookId());
                }
            });
        }
    }

    public static void inPurchaseHistory(final String userId, final String bookId, final boolean[] owns) {
        Query query = purchases.whereEqualTo(USERID_FIELD, userId).whereEqualTo(BOOKID_FIELD, bookId);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocument) {

                for (DocumentSnapshot d : queryDocument) {
                    Log.d(TAG, d.toString());
                    if (d.get(BOOKID_FIELD).toString().equals(bookId)
                            && d.get(USERID_FIELD).toString().equals(userId)) {
                        owns[0] = true;
                        Log.d(TAG, "User already owns: " + bookId + ", owns? " + owns[0]);
                    } else {
                        owns[0] = false;
                        Log.d(TAG, "User does not own: " + bookId);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Log.d(TAG, "An error occured, failed lookup");
            }
        });
    }
}
