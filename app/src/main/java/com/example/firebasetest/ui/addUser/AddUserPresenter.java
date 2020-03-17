package com.example.firebasetest.ui.addUser;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class AddUserPresenter implements AddUserContract.Presenter {

    private AddUserContract.View mView;

    public AddUserPresenter(AddUserContract.View mView) {
        this.mView = mView;
    }

    @Override
    public boolean validate(User user) {
        mView.clearPreError();

        if (user.getName().equals("")){
            mView.showError("Enter a Valid name",1);
            return false;
        }

        if(user.getName().length()<6){
            mView.showError("User name should be at least 6 character long",1);
            return false;
        }

        if (user.getEmail().equals("")){
            mView.showError("Enter a Valid Email Address",2);
            return false;
        }

        if(user.getAge()<=0){
            mView.showError("Age should be grater than Zero",3);
            return false;
        }

        return true;
    }

    @Override
    public void saveUser(User user) {
        Log.d("YYYYYY","saveUser Called");
        String key = MyDatabaseRef.getInstance().getUserRef().push().getKey();
        user.setUid(key);

        MyDatabaseRef.getInstance().getUserRef().child(key)
                .setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        mView.showToast("User added Successfully");
                    }
                });
    }
}
