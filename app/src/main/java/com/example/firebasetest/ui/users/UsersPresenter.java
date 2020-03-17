package com.example.firebasetest.ui.users;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersPresenter implements UsersContract.Presenter {

    private UsersContract.View mView;

    public UsersPresenter(UsersContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getUsersFromFirebase() {

        MyDatabaseRef.getInstance().getUserRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<User> userList = new ArrayList<>();

                        for(DataSnapshot x: dataSnapshot.getChildren()){
                            User user = x.getValue(User.class);
                            userList.add(user);
                        }
                        mView.renderUsers(userList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void deleteUser(final User user) {
          MyDatabaseRef.getInstance().getUserRef().child(user.getUid()).setValue(null, new DatabaseReference.CompletionListener() {
              @Override
              public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                  mView.deleteFromAdapter(user);
              }
          });
    }

    @Override
    public void startAddUserActivity() {
        mView.startAddUserActivity();
    }
}
