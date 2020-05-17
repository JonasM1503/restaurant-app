package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.OrderPos;
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
public class OrderPosFirestoreManager {

    private static final String COLLECTION_NAME = CollectionNames.orderPosCollection;
    private static com.example.restaurant_app.firestore.OrderPosFirestoreManager orderPosFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static com.example.restaurant_app.firestore.OrderPosFirestoreManager newInstance() {
        if (orderPosFirestoreManager == null) {
            orderPosFirestoreManager = new com.example.restaurant_app.firestore.OrderPosFirestoreManager();
        }
        return orderPosFirestoreManager;
    }

    private OrderPosFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createOrderPos(OrderPos order) {
        collectionReference.add(order);
    }

    public void getAllOrderPos(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateOrderPos(OrderPos orderPos) {
        String OrderPosId = orderPos.getOrderPosId();
        DocumentReference documentReference = collectionReference.document(OrderPosId);
        documentReference.set(orderPos);
    }

    public void deleteOrderPos(String OrderPosId) {
        DocumentReference documentReference = collectionReference.document(OrderPosId);
        documentReference.delete();
    }
}


