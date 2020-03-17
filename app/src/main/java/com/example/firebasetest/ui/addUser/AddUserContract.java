package com.example.firebasetest.ui.addUser;

import com.example.firebasetest.models.User;

public interface AddUserContract {

    interface Presenter{
        boolean validate(User user);
        void saveUser(User user);
    }

    interface View{
        void showError(String message,int fieldId);
        void clearPreError();
        void showToast(String message);
    }

}
