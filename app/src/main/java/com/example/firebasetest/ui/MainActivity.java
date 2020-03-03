package com.example.firebasetest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasetest.R;
import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.Product;
import com.example.firebasetest.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName,etEmail,etAge;
    private Button btnSave,btnSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnSave.setOnClickListener(this);
        btnSecondActivity.setOnClickListener(this);
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
            user.setId(key);

            userRef.push().setValue(user);


            Product product = new Product();
            product.setName("T-Shirt");
            product.setPrice(450);

            MyDatabaseRef.getInstance().getProductsRef().push().setValue(product);
        }else if(v==btnSecondActivity){
            startActivity(new Intent(getApplicationContext(),UsersActivity.class));
        }


    }
}
