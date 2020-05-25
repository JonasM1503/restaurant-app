package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
}
