package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.Restaurant;
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
public class RestaurantFirestoreManager {
    private static final String COLLECTION_NAME = "restaurant";
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
        collectionReference.add(restaurant);
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
}