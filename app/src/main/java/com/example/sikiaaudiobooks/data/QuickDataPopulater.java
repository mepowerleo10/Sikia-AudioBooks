package com.example.sikiaaudiobooks.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sikiaaudiobooks.data.model.Book;
import com.example.sikiaaudiobooks.data.model.Chapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickDataPopulater {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public QuickDataPopulater() {

    }

    public static void writeBook(String bookId, Book book) {
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("title", book.getTitle());
        bookMap.put("author", book.getAuthor());
        bookMap.put("narrator", book.getNarrator());
        bookMap.put("genre", book.getGenre());
        bookMap.put("language", book.getLanguage());
        bookMap.put("cover_path", book.getCoverPath());
        bookMap.put("rating", book.getRating());
        bookMap.put("size", book.getSize());
        bookMap.put("price", book.getPrice());

        Map<String, Object> chapterMap;
        Map<String, Object> chapterCollection = new HashMap<>();
        List<Chapter> chapterList = new ArrayList<>();

        for (Chapter ch : book.getChapters()) {
            chapterMap = new HashMap<>();
            chapterMap.put("id", ch.getId());
            chapterMap.put("title", ch.getTitle());
            chapterMap.put("length", ch.getLength());
            chapterMap.put("size", ch.getSize());
//            bookMap.put("chapters", chapterMap);
            chapterList.add(ch);
        }
        bookMap.put("chapters", chapterList);

        db.collection("BOOKS")
                .add(bookMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firebase", "Document added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firebase", "Error adding document", e);
                    }
                });
    }

    public static void update() {
        String[] authors = {
                "Caroll Lewis",
                "Have No Idea"
        };

        String[] titles = {
                "Alice in Wonderland",
                "Admirable Bashville"
        };

        String[] narrators = {
                "Ashlegh Jane",
                "Arielle Lipshaw"
        };

        String[] genre = {
                "Fiction",
                "Comedy"
        };

        String[] language = {
                "English",
                "English"
        };

        int[] noChapters = {
                10,
                4
        };

        String[] coverPaths = {
                "Alices_Adventures_in_Wonderland5.jpg",
                "admirable_bashville_1203.jpg"
        };

        long[] sizes = {
                32768,
                33792
        };

        long[] prices = {
                0,
                0
        };

        List<Chapter> chapter_a = new ArrayList<>();
        chapter_a.add(new Chapter(1L, 4L, "Down The Rabbit Hole"));
        chapter_a.add(new Chapter(2L, 4L, "The Pool of Tears"));
        chapter_a.add(new Chapter(3L, 4L, "The Caucus Race and a Long Tale"));
        chapter_a.add(new Chapter(4L, 4L, "The Rabbit Sends Down a Little Bunny"));
        chapter_a.add(new Chapter(5L, 4L, "Advice from a Caterpillar"));
        chapter_a.add(new Chapter(6L, 3L, "Pig and Pepper"));
        chapter_a.add(new Chapter(7L, 2L, "A Mad Tea-Party"));
        chapter_a.add(new Chapter(8L, 4L, "The Queen's Croquet Ground"));
        chapter_a.add(new Chapter(9L, 3L, "Who Stole the Tarts?"));
        chapter_a.add(new Chapter(10L, 3L, "Alice's Evidence"));

        List<Chapter> chapter_b = new ArrayList<>();
        chapter_b.add(new Chapter(1L, 6L, "Preface"));
        chapter_b.add(new Chapter(2L, 5L, "Act 1"));
        chapter_b.add(new Chapter(3L, 16L, "Act 2"));
        chapter_b.add(new Chapter(4L, 7L, "Act 3"));

        List[] chapters = {
                chapter_a,
                chapter_b
        };

        for (int i = 0; i < titles.length; i++) {
            Book book = new Book(
                    100.0, 3.0, sizes[i], 10L, prices[i], Integer.toString(i), narrators[i],
                    genre[i], language[i], authors[i], coverPaths[i], chapters[i], titles[i]
            );
            writeBook(Long.toString(i + 1L), book);
            Log.d("FireStore", "Added book: " + book);
        }
    }

}
