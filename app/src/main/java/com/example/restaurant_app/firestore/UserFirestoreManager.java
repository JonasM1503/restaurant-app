package com.example.restaurant_app.firestore;


import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 *
 * @author Simon Rothmann
 * @content handler for CRUD-operations to firestore
 */
public class UserFirestoreManager {
    private static final String COLLECTION_NAME = CollectionNames.userCollection;
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
        collectionReference.document(user.getEmail()).set(user);
    }

    public void getAllUsers(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateUser(User user) {
        String userEmail = user.getEmail();
        DocumentReference documentReference = collectionReference.document(userEmail);
        documentReference.set(user);
    }

    public void deleteUser(String userEmail) {
        DocumentReference documentReference = collectionReference.document(userEmail);
        documentReference.delete();
    }

// find user by email
    public interface GetUserByEmailCallback {
        void onCallback(User user);
    }

    public void getUserByEmail(String email, final GetUserByEmailCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(email).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               User return_user = documentSnapshot.toObject(User.class);
                callback.onCallback(return_user);
            }
        });
    }

}