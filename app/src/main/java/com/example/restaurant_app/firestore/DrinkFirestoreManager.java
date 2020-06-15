package com.example.restaurant_app.firestore;


import androidx.annotation.NonNull;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Drink;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon Rothmann, Jonas Mitschke
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
        collectionReference.document(drink.getDrinkId()).set(drink);
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

// find drink by ID
    public interface GetDrinkByIdCallback {
        void onCallback(Drink drink);
        void onFailureCallback(Exception e);
    }

    public void getDrinkById(String id, final DrinkFirestoreManager.GetDrinkByIdCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Drink return_drink = documentSnapshot.toObject(Drink.class);
                    callback.onCallback(return_drink);
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

    /**
     *
     * @author   Jonas Mitschke
     * @content  interface to work with returned drinks from firestore
     */
    public interface GetDrinksByRestaurantCallback {
        void onCallback(List<Drink> drinks);
        void onFailureCallback(Exception e);
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  get drinks from firestore
     * @param    resId       id of the restaurant
     * @param    callback    GetDrinksByRestaurantCallback-interface
     */
    public void getDrinksByRestaurantId(String resId, final DrinkFirestoreManager.GetDrinksByRestaurantCallback callback){
        collectionReference.whereEqualTo("restaurantId", resId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Drink> return_drinks = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()) {
                                Drink drink = document.toObject(Drink.class);
                                return_drinks.add(drink);
                            }
                            callback.onCallback(return_drinks);
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