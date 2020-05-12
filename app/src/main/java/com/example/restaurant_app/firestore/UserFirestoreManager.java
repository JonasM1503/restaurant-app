package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
}