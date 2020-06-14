package com.example.restaurant_app.firestore;

import androidx.annotation.NonNull;

import com.example.restaurant_app.helpers.CollectionNames;
import com.example.restaurant_app.models.Address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
/**
 *
 * @author Simon Rothmann, Jonas Mitschke
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

// find address by ID
    public interface GetAddressByIdCallback{
        void onCallback(Address address);
        void onFailureCallback(Exception e);
    }

    public void getAddressById(String id, final AddressFirestoreManager.GetAddressByIdCallback callback){
        Task<DocumentSnapshot> doc = collectionReference.document(id).get();
        doc.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Address return_address = documentSnapshot.toObject(Address.class);
                    callback.onCallback(return_address);
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