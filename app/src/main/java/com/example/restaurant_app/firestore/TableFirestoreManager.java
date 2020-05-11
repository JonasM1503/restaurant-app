package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.Table;
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
public class TableFirestoreManager {

    private static final String COLLECTION_NAME = "table";
    private static com.example.restaurant_app.firestore.TableFirestoreManager tableFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static com.example.restaurant_app.firestore.TableFirestoreManager newInstance() {
        if (tableFirestoreManager == null) {
            tableFirestoreManager = new com.example.restaurant_app.firestore.TableFirestoreManager();
        }
        return tableFirestoreManager;
    }

    private TableFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createTable(Table table) {
        collectionReference.add(table);
    }

    public void getAllTables(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateTable(Table table) {
        String TableId = table.getTableId();
        DocumentReference documentReference = collectionReference.document(TableId);
        documentReference.set(table);
    }

    public void deleteTable(String TableId) {
        DocumentReference documentReference = collectionReference.document(TableId);
        documentReference.delete();
    }
}



