package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 *
 * @author Jonas Mitschke
 * @content handler for CRUD-operations to firestore
 */
public class TableFirestoreManager {

    private static final String COLLECTION_NAME = CollectionNames.tableCollection;
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

// find table by ID
    public interface GetTableByIdCallback {
        void onCallback(Table table);
    }

    public void getTableById(String id, final TableFirestoreManager.GetTableByIdCallback callback){
        Task<QuerySnapshot> doc = collectionReference.whereEqualTo("tableId", id).get();
        doc.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                List<Table> return_tables = querySnapshot.toObjects(Table.class);
                Table return_table = return_tables.get(0);
                callback.onCallback(return_table);
            }
        });
    }
}



