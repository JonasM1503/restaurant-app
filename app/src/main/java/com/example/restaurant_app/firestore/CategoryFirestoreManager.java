package com.example.restaurant_app.firestore;

import androidx.annotation.NonNull;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Category;
import com.example.restaurant_app.models.CategorySpinner;
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

/**
 *
 * @author Jonas Mitschke
 * @content handler for CRUD-operations to firestore
 */
public class CategoryFirestoreManager {

    private static final String COLLECTION_NAME = CollectionNames.categoryCollection;
    private static CategoryFirestoreManager categoryFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    private Category category;

    public static CategoryFirestoreManager newInstance() {
        if (categoryFirestoreManager == null) {
            categoryFirestoreManager = new CategoryFirestoreManager();
        }
        return categoryFirestoreManager;
    }

    private CategoryFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    // CRUD operations
    public void createCategory(Category category) {

        collectionReference.document(category.getCategoryId()).set(category);
    }

    public void getAllCategories(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateCategory(Category category) {
        String CategoryId = category.getCategoryId();
        DocumentReference documentReference = collectionReference.document(CategoryId);
        documentReference.set(category);
    }

    public void deleteCategory(String CategoryId) {
        DocumentReference documentReference = collectionReference.document(CategoryId);
        documentReference.delete();
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  interface to work with returned category from firestore
     */
    public interface GetCategoryByIdCallback {
        void onCallback(Category category);
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  get category from firestore by id
     * @param    id          id of the category to be find
     * @param    callback    GetCategoryByIdCallback-interface
     */
    public void getCategoryById(String id, final CategoryFirestoreManager.GetCategoryByIdCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Category return_category = documentSnapshot.toObject(Category.class);
                callback.onCallback(return_category);
            }
        });
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  interface to work with returned categories from firestore
     */
    public interface GetCategoryByRestaurantCallback {
        void onCallback(ArrayList<CategorySpinner> categories);
        void onFailureCallback(Exception e);
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  get categories from firestore
     * @param    resId       id of the restaurant
     * @param    callback    GetCategoryByRestaurantCallback-interface
     */
    public void getCategoriesByRestaurantId(String resId, final CategoryFirestoreManager.GetCategoryByRestaurantCallback callback){
        collectionReference.whereEqualTo("restaurantId", resId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<CategorySpinner> categories = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categories.add(new CategorySpinner(category.getCategoryId(), category.getName()));
                            }
                            callback.onCallback(categories);
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
     * @content  interface to check how often a category is used
     */
    public interface CheckIfCategoryIsUsedCallback {
        void onCallback(int amount);
        void onFailureCallback(Exception e);
    }

    /**
     *
     * @author   Jonas Mitschke
     * @content  check if category is used in foods/ drinks
     * @param    catId       id of the category
     * @param    callback    CheckIfCategoryIsUsedCallback-interface
     */
    public void checkIfCategoryIsUsed(String catId, final CategoryFirestoreManager.CheckIfCategoryIsUsedCallback callback){
        FirebaseFirestore.getInstance().collection(CollectionNames.foodCollection).whereEqualTo("categoryId", catId).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int amount = 0;
                        if(task.isSuccessful()){
                            for(DocumentSnapshot document : task.getResult()) {
                                amount += 1;
                            }
                            callback.onCallback(amount);
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
