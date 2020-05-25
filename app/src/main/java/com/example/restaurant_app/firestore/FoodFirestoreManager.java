package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Food;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 *
 * @author Jonas Mitschke
 * @content handler for CRUD-operations to firestore
 */
public class FoodFirestoreManager {

    private static final String COLLECTION_NAME = CollectionNames.foodCollection;
    private static FoodFirestoreManager foodFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    private Food food;

    public static FoodFirestoreManager newInstance() {
        if (foodFirestoreManager == null) {
            foodFirestoreManager = new FoodFirestoreManager();
        }
        return foodFirestoreManager;
    }

    private FoodFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createFood(Food food) {
        collectionReference.document(food.getFoodId()).set(food);
    }

    public void getAllFoods(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateFood(Food food) {
        String FoodId = food.getFoodId();
        DocumentReference documentReference = collectionReference.document(FoodId);
        documentReference.set(food);
    }

    public void deleteFood(String FoodId) {
        DocumentReference documentReference = collectionReference.document(FoodId);
        documentReference.delete();
    }

    //find food by ID
    public interface GetFoodByIdCallback {
        void onCallback(Food food);
    }

    public void getFoodById(String id, final FoodFirestoreManager.GetFoodByIdCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food return_food = documentSnapshot.toObject(Food.class);
                callback.onCallback(return_food);
            }
        });
    }
}
