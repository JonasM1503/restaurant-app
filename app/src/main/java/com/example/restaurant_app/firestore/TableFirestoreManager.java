package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Table;
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
    public interface CreateTableCallback {
        void onCallback(String tableID);
    }

    public void createTable(Table table, final TableFirestoreManager.CreateTableCallback callback) {
        collectionReference.add(table).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                callback.onCallback(documentReference.getId().toString());
            }
        });
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
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Table return_table = documentSnapshot.toObject(Table.class);
                callback.onCallback(return_table);
            }
        });
    }
}



