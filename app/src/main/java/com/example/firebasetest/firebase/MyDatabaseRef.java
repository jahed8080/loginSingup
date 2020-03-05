package com.example.firebasetest.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDatabaseRef {

    private static final String USERS_REF="Users";
    private static final String PRODUCTS_REF="products";

    private static MyDatabaseRef myDatabaseRef;

    private FirebaseDatabase database;

    private MyDatabaseRef() {
        this.database  = FirebaseDatabase.getInstance();
    }

    public static MyDatabaseRef getInstance() {
        if (myDatabaseRef == null) {
            myDatabaseRef = new MyDatabaseRef();
        }
        return myDatabaseRef;
    }


    public DatabaseReference getUserRef(){
        return database.getReference().child(USERS_REF);
    }

    public DatabaseReference getProductsRef(){
        return database.getReference().child(PRODUCTS_REF);
    }

}
