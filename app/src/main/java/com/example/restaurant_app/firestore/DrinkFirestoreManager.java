package com.example.restaurant_app.firestore;


import com.example.restaurant_app.models.Drink;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DrinkFirestoreManager {
    private static final String COLLECTION_NAME = "drink";
    private static DrinkFirestoreManager DrinkFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;

    public static DrinkFirestoreManager newInstance() {
        if (DrinkFirestoreManager == null) {
            DrinkFirestoreManager = new DrinkFirestoreManager();
        }
        return DrinkFirestoreManager;
    }

    private DrinkFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        contactsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    // CRUD operations
    public void createDrink(Drink drink) {
        contactsCollectionReference.add(drink);
    }

    public void getAllFoods(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateDrink(Drink drink) {
        String drinkId = drink.getDrinkId();
        DocumentReference documentReference = contactsCollectionReference.document(drinkId);
        documentReference.set(drink);
    }

    public void deleteDrink(String drinkId) {
        DocumentReference documentReference = contactsCollectionReference.document(drinkId);
        documentReference.delete();
    }
}