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

    public void createTable(Table table) {
        collectionReference.document(table.getTableId()).set(table);
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

/**
*
* @author   Jonas Mitschke
* @content  interface to work with returned table from firestore
*/
    public interface GetTableByIdCallback {
        void onCallback(Table table);
    }

/**
*
* @author   Jonas Mitschke
* @content  get table from firestore by id
* @param    id          id of the table to be find
* @param    callback    GetTableByIdCallback-interface
*/
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



