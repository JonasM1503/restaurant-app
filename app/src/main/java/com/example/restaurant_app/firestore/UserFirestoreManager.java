package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

// find user by email
    public interface GetUserByEmailCallback {
        void onCallback(User user);
    }

    public void getUserByEmail(String email, final GetUserByEmailCallback callback){
        Task<QuerySnapshot> doc = collectionReference.whereEqualTo("email", email).get();
        doc.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                List<User> return_users = querySnapshot.toObjects(User.class);
                User return_user = return_users.get(0);
                callback.onCallback(return_user);
            }
        });
    }

// check password for login
    public static boolean checkPassword(User user, String input_password) {
        return user.getPassword().compareTo(User.hashPassword(input_password)) == 0;
    }
}