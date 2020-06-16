package com.example.restaurant_app.firestore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;

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

    public String uploadImage(Bitmap picture){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String[] path = {"restaurantImages/" + UUID.randomUUID() + ".png"};
        storageRef = storage.getReference(path[0]);
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(TAG, "onSuccess: uploaded file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                path[0] = null;
                Log.e(TAG, "onFailure: did not upload file. Error: " + exception);
            }
        });
        return path[0];
    }

    public interface UpdateImageCallback {
        void onCallback(Boolean successful);
    }

    public void updateImage(String url, Bitmap picture, final PictureFirestoreManager.UpdateImageCallback callback){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        storageRef = storage.getReference(url);
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onCallback(true);
                Log.i(TAG, "onSuccess: updated file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onCallback(false);
                Log.e(TAG, "onFailure: did not update file. Error: " + exception);
            }
        });
    }

    public void deleteImage(String url){
        storageRef = storage.getReference(url);
        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG, "onSuccess: deleted file");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "onFailure: did not delete file with URL: " + url);
            }
        });
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
