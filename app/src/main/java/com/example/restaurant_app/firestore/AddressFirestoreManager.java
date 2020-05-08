package com.example.restaurant_app.firestore;

        import com.example.restaurant_app.models.Address;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.firebase.firestore.CollectionReference;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.QuerySnapshot;

public class AddressFirestoreManager {
    private static final String COLLECTION_NAME = "address";
    private static AddressFirestoreManager AddressFirestoreManager;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference contactsCollectionReference;

    public static AddressFirestoreManager newInstance() {
        if (AddressFirestoreManager == null) {
            AddressFirestoreManager = new AddressFirestoreManager();
        }
        return AddressFirestoreManager;
    }

    private AddressFirestoreManager() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        contactsCollectionReference = firebaseFirestore.collection(COLLECTION_NAME);
    }

    // CRUD operations
    public void createAddress(Address address) {
        contactsCollectionReference.add(address);
    }

    public void getAllFoods(OnCompleteListener<QuerySnapshot> onCompleteListener)
    {
        contactsCollectionReference.get().addOnCompleteListener(onCompleteListener);
    }

    public void updateAddress(Address address) {
        String addressId = address.getAddressId();
        DocumentReference documentReference = contactsCollectionReference.document(addressId);
        documentReference.set(address);
    }

    public void deleteAddress(String addressId) {
        DocumentReference documentReference = contactsCollectionReference.document(addressId);
        documentReference.delete();
    }
}