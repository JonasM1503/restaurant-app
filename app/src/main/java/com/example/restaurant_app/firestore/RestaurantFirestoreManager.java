package com.example.restaurant_app.firestore;

import androidx.annotation.NonNull;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
/**
 *
 * @author Simon Rothmann, Jonas Mitschke
 * @content handler for CRUD-operations to firestore
 */
public class RestaurantFirestoreManager {
    private static final String COLLECTION_NAME = CollectionNames.restaurantCollection;
    private static RestaurantFirestoreManager RestaurantFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static RestaurantFirestoreManager newInstance() {
        if (RestaurantFirestoreManager == null) {
            RestaurantFirestoreManager = new RestaurantFirestoreManager();
        }
        return RestaurantFirestoreManager;
    }

    private RestaurantFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createRestaurant(Restaurant restaurant) {
        collectionReference.document(restaurant.getRestaurantId()).set(restaurant);
    }
    public void getAllRestaurants(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateRestaurant(Restaurant restaurant) {
        String restaurantId = restaurant.getRestaurantId();
        DocumentReference documentReference = collectionReference.document(restaurantId);
        documentReference.set(restaurant);
    }

    public void deleteRestaurant(String restaurantId) {
        DocumentReference documentReference = collectionReference.document(restaurantId);
        documentReference.delete();
    }

// find restaurant by ID
    public interface GetRestaurantByIdCallback{
        void onCallback(Restaurant restaurant);
        void onFailureCallback(Exception e);
    }

    public void getRestaurantById(String id, final RestaurantFirestoreManager.GetRestaurantByIdCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Restaurant return_restaurant = documentSnapshot.toObject(Restaurant.class);
                    callback.onCallback(return_restaurant);
                } else {
                    callback.onFailureCallback(new Exception(new ClassNotFoundException()));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onFailureCallback(exception);
            }
        });
    }
}