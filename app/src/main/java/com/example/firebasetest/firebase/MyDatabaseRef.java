package com.example.firebasetest.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyDatabaseRef {

    private static final String USERS_REF="users";
    private static final String PRODUCTS_REF="products";

    private static MyDatabaseRef instance;

    private FirebaseDatabase database;

    private MyDatabaseRef() {
        this.database  = FirebaseDatabase.getInstance();
    }

    public static MyDatabaseRef getInstance() {
        if (instance == null) {
            instance = new MyDatabaseRef();
        }
        return instance;
    }


    public DatabaseReference getUserRef(){
        return database.getReference().child(USERS_REF);
    }

    public DatabaseReference getProductsRef(){
        return database.getReference().child(PRODUCTS_REF);
    }

}
