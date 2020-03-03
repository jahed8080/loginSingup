package com.example.firebasetest.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.R;
import com.example.firebasetest.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<User> userList;

    private UserHandler handler;

    public UserAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.userList = new ArrayList<>();
        this.handler = new UserHandler(this);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);

        holder.bind(user);


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public List<User> getUserList(){
        return this.userList;
    }

    public void addUser(User user){
        handler.addUser(user);



    }

    public void updateUser(User user){
        int position = getPosition(user);
        if(position!=-1){
            userList.set(position,user);
            notifyItemChanged(position);
        }

    }

    public void removeUser(User user){
        int position = getPosition(user);
        userList.remove(position);
        notifyItemRemoved(position);

    }

    private int getPosition(User user){
        for (User x: userList){
            if(x.getId().equals(user.getId())){
                return userList.indexOf(x);
            }
        }
        return -1;
    }


    class UserHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvEmail,tvAge;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvEmail = itemView.findViewById(R.id.email);
            tvAge = itemView.findViewById(R.id.age);
        }


        public void bind(User user){
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvAge.setText(String.valueOf(user.getAge()));
        }
    }
}
