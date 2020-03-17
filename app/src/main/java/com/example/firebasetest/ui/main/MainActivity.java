package com.example.firebasetest.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasetest.R;
import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.User;
import com.example.firebasetest.ui.users.UsersActivity;
import com.example.firebasetest.ui.login.LoginActivity;
import com.example.firebasetest.utils.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private EditText etName,etEmail,etAge;
    private Button btnSave,btnSecondActivity,btnLOgout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
            return;
        }else{
            Log.d("UUUUUUU",FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()+"");
        }

        initView();

//        DatabaseReference rootREf = FirebaseDatabase.getInstance().getReference();
//
//        rootREf.child("ADDDD").setValue("hgdfjkgdfg");




    }

    private void initView() {
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etAge = findViewById(R.id.age);
        btnSave = findViewById(R.id.btn_save);
        btnSecondActivity = findViewById(R.id.btn_show);
        btnLOgout = findViewById(R.id.logout);

        btnSave.setOnClickListener(this);
        btnSecondActivity.setOnClickListener(this);
        btnLOgout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnSave){
            String name =etName.getText().toString().trim();
            String email =etEmail.getText().toString().trim();
            String ageStr =etAge.getText().toString().trim();

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(Integer.parseInt(ageStr));

            DatabaseReference userRef = MyDatabaseRef.getInstance().getUserRef();

            String key = userRef.push().getKey();
            user.setUid(key);
            userRef.child(key).setValue(user);


//            Product product = new Product();
//            product.setName("T-Shirt");
//            product.setPrice(450);
//
//            MyDatabaseRef.getInstance().getProductsRef().push().setValue(product);
        }else if(v==btnSecondActivity){
            startActivity(new Intent(getApplicationContext(), UsersActivity.class));
        }else if(v==btnLOgout){
            FirebaseAuth.getInstance().signOut();
            googleSignOut();
            MainActivity.this.recreate();
        }


    }
}
