package com.example.firebasetest.ui.addUser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firebasetest.R;
import com.example.firebasetest.models.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddUserActivity extends AppCompatActivity implements AddUserContract.View, View.OnClickListener {

    private AddUserPresenter mPresenter;

    TextInputLayout tiName,tiEmail,tiAge;
    TextInputEditText etName,etEmail,etAge;
    MaterialButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mPresenter = new AddUserPresenter(this);

        initView();
    }

    private void initView() {
        tiName = findViewById(R.id.ti_name);
        tiEmail = findViewById(R.id.ti_email);
        tiAge = findViewById(R.id.ti_age);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etAge = findViewById(R.id.et_age);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void showError(String message, int fieldId) {

        switch (fieldId){
            case 1:
                tiName.setError(message);
                etName.requestFocus();
                break;

            case 2:
                tiEmail.setError(message);
                etEmail.requestFocus();
                break;

            case 3:
                tiAge.setError(message);
                etAge.requestFocus();
                break;
        }

    }

    @Override
    public void clearPreError() {
        tiName.setErrorEnabled(false);
        tiEmail.setErrorEnabled(false);
        tiAge.setErrorEnabled(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        try {
            user.setAge(Integer.parseInt(ageStr));
        }catch (Exception e){

        }

        boolean validUser = mPresenter.validate(user);

        if(!validUser){
            return;
        }

        mPresenter.saveUser(user);

    }
}
