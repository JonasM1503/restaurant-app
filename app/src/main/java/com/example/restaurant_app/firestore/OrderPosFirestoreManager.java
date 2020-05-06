package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.OrderPos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderPosFirestoreManager {

    private static final String COLLECTION_NAME = "orderPos";
    private static com.example.restaurant_app.firestore.OrderPosFirestoreManager orderPosFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;

    public static com.example.restaurant_app.firestore.OrderPosFirestoreManager newInstance() {
        if (orderPosFirestoreManager == null) {
            orderPosFirestoreManager = new com.example.restaurant_app.firestore.OrderPosFirestoreManager();
        }
        return orderPosFirestoreManager;
    }

    private OrderPosFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        contactsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createOrderPos(OrderPos order) {
        contactsCollectionReference.add(order);
    }

    public void getAllOrderPos(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateOrderPos(OrderPos orderPos) {
        String OrderPosId = orderPos.getOrderPosId();
        DocumentReference documentReference = contactsCollectionReference.document(OrderPosId);
        documentReference.set(orderPos);
    }

    public void deleteOrderPos(String OrderPosId) {
        DocumentReference documentReference = contactsCollectionReference.document(OrderPosId);
        documentReference.delete();
    }
}


