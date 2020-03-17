package com.example.firebasetest.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasetest.R;
import com.example.firebasetest.firebase.MyDatabaseRef;
import com.example.firebasetest.models.User;
import com.example.firebasetest.ui.main.MainActivity;
import com.example.firebasetest.ui.signup.SignupActivity;
import com.example.firebasetest.utils.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN=5000;

    EditText etEmail,etPAssword;
    private Button btnLogin,btnSignUp;

    SignInButton btnGoogle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etEmail = findViewById(R.id.email);
        etPAssword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signup);
        btnGoogle = findViewById(R.id.google);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){

            String email = etEmail.getText().toString().trim();
            String password = etPAssword.getText().toString().trim();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "LOgin Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }else if(v==btnSignUp){
            startActivity(new Intent(getApplicationContext(), SignupActivity.class));
        }else if(v==btnGoogle){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(getGoogleApiClient());
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==RC_SIGN_IN ){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){


                                    FirebaseUser firebaseUser = task.getResult().getUser();

                                    User user = new User();
                                    user.setUid(firebaseUser.getUid());
                                    user.setName(firebaseUser.getDisplayName());
                                    user.setEmail(firebaseUser.getEmail());

                                    MyDatabaseRef.getInstance().getUserRef().child(user.getUid())
                                            .setValue(user, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                }
                                            });

                                }else{
                                    Toast.makeText(LoginActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }else {
                Log.d("ACCCOUNT",result.getStatus().getStatusCode()+"");
            }
        }
    }
}
