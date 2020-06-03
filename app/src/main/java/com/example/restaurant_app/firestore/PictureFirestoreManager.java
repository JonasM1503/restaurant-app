package com.example.restaurant_app.firestore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.restaurant_app.activities.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

public class PictureFirestoreManager {
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference storageRef;
    private static PictureFirestoreManager pictureFirestoreManager;


    public static PictureFirestoreManager newInstance() {
        if (pictureFirestoreManager == null) {
            pictureFirestoreManager = new PictureFirestoreManager();
        }
        return pictureFirestoreManager;
    }

    public static String uploadImage(Bitmap picture){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String path = "restaurantImages/" + UUID.randomUUID() + ".png";
        storageRef = storage.getReference(path);
        UploadTask uploadTask = storageRef.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                } else {

                }
            }
        });
        return path;
    }

    public static void updateImage(String url, Bitmap picture){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storageRef = storage.getReference(url);
        UploadTask uploadTask = storageRef.putBytes(data);

    }

    public interface DownloadCallback {
        void onCallback(Bitmap picture);

    }
    public void downloadImage(String url, DownloadCallback downloadCallback){
        storageRef = storage.getReference(url);
        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap picture = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                downloadCallback.onCallback(picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                int w = 210, h = 176;

                Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
                Bitmap bmp = Bitmap.createBitmap(w, h, conf);
                downloadCallback.onCallback(bmp);
            }
        });

    }
}
