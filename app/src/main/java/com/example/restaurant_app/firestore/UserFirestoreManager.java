package com.example.restaurant_app.firestore;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.restaurant_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Simon Rothmann
 * @content handler for CRUD-operations to firestore
 */
public class UserFirestoreManager {
    private static final String COLLECTION_NAME = "user";
    private static UserFirestoreManager UserFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static UserFirestoreManager newInstance() {
        if (UserFirestoreManager == null) {
            UserFirestoreManager = new UserFirestoreManager();
        }
        return UserFirestoreManager;
    }

    private UserFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createUser(User user) {
        collectionReference.add(user);
    }

    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateUser(User user) {
        String userId = user.getUserId();
        DocumentReference documentReference = collectionReference.document(userId);
        documentReference.set(user);
    }

    public void deleteUser(String userId) {
        DocumentReference documentReference = collectionReference.document(userId);
        documentReference.delete();
    }
    //get specific user when login
    public User getSpecificUser(String email, String password){
        final User[] user = {null};
        final String[] documentId = {""};
         collectionReference.whereEqualTo("email", email).whereEqualTo("password", password)
                 .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                documentId[0] = document.getId();
                             }
                         } else {
                             Log.d("Error", "Error getting documents: ", task.getException());
                         }
                     }
                 });
         DocumentReference docRef = collectionReference.document(documentId[0]);
         docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 user[0] = documentSnapshot.toObject(User.class);

             }
         });
        return user[0];
    }
}