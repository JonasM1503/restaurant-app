package com.example.restaurant_app.firestore;

import com.example.restaurant_app.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderFirestoreManager {

    private static final String COLLECTION_NAME = "order";
    private static OrderFirestoreManager orderFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static OrderFirestoreManager newInstance() {
        if (orderFirestoreManager == null) {
            orderFirestoreManager = new OrderFirestoreManager();
        }
        return orderFirestoreManager;
    }

    private OrderFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createOrder(Order order) {
        collectionReference.add(order);
    }

    public void getAllOrders(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateOrder(Order order) {
        String OrderId = order.getOrderId();
        DocumentReference documentReference = collectionReference.document(OrderId);
        documentReference.set(order);
    }

    public void deleteOrder(String OrderId) {
        DocumentReference documentReference = collectionReference.document(OrderId);
        documentReference.delete();
    }
}

