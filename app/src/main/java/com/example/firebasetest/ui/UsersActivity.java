package com.example.firebasetest.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.firebasetest.R;
import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UsersActivity extends AppCompatActivity {

    private UserAdapter adapter;

    private ValueEventListener myListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot x :dataSnapshot.getChildren()){
                User user = x.getValue(User.class);
                adapter.addUser(user);
            }



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            Log.d("UUUUUU",dataSnapshot.toString());
            User user = dataSnapshot.getValue(User.class);
            adapter.addUser(user);

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.d("UUUUUU",dataSnapshot.toString());
            User user = dataSnapshot.getValue(User.class);
            adapter.updateUser(user);

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            Log.d("UUUUUU",dataSnapshot.toString());
            Log.d("UUUUUU","CHILD REMOVED CALLED");
            User user = dataSnapshot.getValue(User.class);
            adapter.removeUser(user);

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        adapter = new UserAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);


//        MyDatabaseRef.getInstance().getUserRef().addListenerForSingleValueEvent(myListener);

        MyDatabaseRef.getInstance().getUserRef().addChildEventListener(childEventListener);
    }

}
