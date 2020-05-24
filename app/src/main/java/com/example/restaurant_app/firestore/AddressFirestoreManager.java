package com.example.restaurant_app.firestore;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
/**
 *
 * @author Simon Rothmann
 * @content handler for CRUD-operations to firestore
 */
public class AddressFirestoreManager {
    private static final String COLLECTION_NAME = CollectionNames.addressCollection;
    private static AddressFirestoreManager AddressFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;

    public static AddressFirestoreManager newInstance() {
        if (AddressFirestoreManager == null) {
            AddressFirestoreManager = new AddressFirestoreManager();
        }
        return AddressFirestoreManager;
    }

    private AddressFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

// CRUD operations
    public void createAddress(Address address) {
        collectionReference.document(address.getAddressId()).set(address);
    }

    public void getAllAddresses(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        collectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateAddress(Address address) {
        String addressId = address.getAddressId();
        DocumentReference documentReference = collectionReference.document(addressId);
        documentReference.set(address);
    }

    public void deleteAddress(String addressId) {
        DocumentReference documentReference = collectionReference.document(addressId);
        documentReference.delete();
    }

    public interface GetAddressByIdCallback{
        void onCallback(Address address);
    }

    public void getAddressById(String addressId, final AddressFirestoreManager.GetAddressByIdCallback callback){
        collectionReference.document(addressId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                callback.onCallback(documentSnapshot.toObject(Address.class));
            }
        });
    }
}