package com.example.firebasetest.ui.users;

import com.example.firebasetest.models.User;

import java.util.List;

public interface UsersContract {

    interface Presenter{
        void getUsersFromFirebase();
        void deleteUser(User user);
        void startAddUserActivity();
    }

    interface View{
        void renderUsers(List<User> userList);
        void deleteUser(User user);
        void deleteFromAdapter(User user);
        void startAddUserActivity();
    }
}
