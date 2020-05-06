package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FoodFirestoreManager {

    private static final String COLLECTION_NAME = "food";
    private static FoodFirestoreManager foodFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;

    public static FoodFirestoreManager newInstance() {
        if (foodFirestoreManager == null) {
            foodFirestoreManager = new FoodFirestoreManager();
        }
        return foodFirestoreManager;
    }

    private FoodFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        contactsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createFood(Food food) {
        contactsCollectionReference.add(food);
    }

    public void getAllFoods(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateFood(Food food) {
        String FoodId = food.getFoodId();
        DocumentReference documentReference = contactsCollectionReference.document(FoodId);
        documentReference.set(food);
    }

    public void deleteFood(String FoodId) {
        DocumentReference documentReference = contactsCollectionReference.document(FoodId);
        documentReference.delete();
    }
}
