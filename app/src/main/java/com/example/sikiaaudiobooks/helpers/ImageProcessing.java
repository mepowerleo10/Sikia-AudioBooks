package com.example.sikiaaudiobooks.helpers;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ImageProcessing {
    private File cacheDir, filePath;
    private Context context;
    private final String COVER_CACHE = "covers";
    private final String logging_tag = "COVERS";

    public ImageProcessing(Context context) {
        this.context = context;
    }

    public void setCoverArt(final ImageView imgView, String coverPath) {
        cacheDir = context.getCacheDir();
        try {
            filePath = new File(cacheDir, coverPath);

            // Check if the file already exists in the cache directory
            if (filePath.exists() && (!filePath.isDirectory()) && filePath.isFile()) {
                imgView.setImageURI(Uri.fromFile(filePath));
                Log.d(logging_tag, filePath + ": exists in cache");
            } else {
                // Download the cover art if it is not in the cache directory
                StorageReference reference = FirebaseStorage.getInstance().getReference();
                StorageReference refPath = reference.child("covers/" + coverPath);
                refPath.getFile(filePath).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        imgView.setImageURI(Uri.fromFile(filePath));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to load images", Toast.LENGTH_LONG);
                    }
                });
                Log.d(logging_tag, filePath + ": had to be downloaded");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
