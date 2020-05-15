package com.example.restaurant_app.firestore;


import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Drink;
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
public class DrinkFirestoreManager {
    private static final String COLLECTION_NAME = CollectionNames.drinkCollection;
    private static DrinkFirestoreManager DrinkFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static DrinkFirestoreManager newInstance() {
        if (DrinkFirestoreManager == null) {
            DrinkFirestoreManager = new DrinkFirestoreManager();
        }
        return DrinkFirestoreManager;
    }

    private DrinkFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createDrink(Drink drink) {
        collectionReference.add(drink);
    }

    public void getAllDrinks(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateDrink(Drink drink) {
        String drinkId = drink.getDrinkId();
        DocumentReference documentReference = collectionReference.document(drinkId);
        documentReference.set(drink);
    }

    public void deleteDrink(String drinkId) {
        DocumentReference documentReference = collectionReference.document(drinkId);
        documentReference.delete();
    }
}