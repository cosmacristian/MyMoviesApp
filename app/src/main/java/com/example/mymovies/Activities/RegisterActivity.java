package com.example.mymovies.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymovies.R;
import com.example.mymovies.SQLiteDB.SQLiteHelper;
import com.example.mymovies.Models.User;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;

    Button buttonRegister;

    SQLiteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SQLiteHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUserName.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();

                    //Check in the database is there any user associated with  this email
                    if (!sqliteHelper.isEmailExists(Email)) {

                        //Email does not exist now add new user to database
                        sqliteHelper.addUser(new User(null, UserName, Email, Password,null));
                        Toast.makeText(getApplicationContext(), "User created successfully! Please Login ",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Toast.LENGTH_LONG);
                    }else {

                        //Email exists with email input provided so show error user already exist
                        Toast.makeText(getApplicationContext(), "User already exists with same email ",Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

    private boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUserName.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(), "Please enter valid username!",Toast.LENGTH_SHORT).show();
        } else {
            if (UserName.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(getApplicationContext(), "Username is to short!",Toast.LENGTH_SHORT).show();
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
            Toast.makeText(getApplicationContext(), "Please enter valid email!",Toast.LENGTH_SHORT).show();
        } else {
            valid = true;
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
            Toast.makeText(getApplicationContext(), "Please enter valid password!",Toast.LENGTH_SHORT).show();
        } else {
            if (Password.length() > 5) {
                valid = true;
            } else {
                valid = false;
                Toast.makeText(getApplicationContext(), "Password is to short!",Toast.LENGTH_SHORT).show();
            }
        }


        return valid;
    }



    private void initViews() {
        editTextEmail = findViewById(R.id.register_et_email);
        editTextPassword = findViewById(R.id.register_et_passwd);
        editTextUserName = findViewById(R.id.register_et_username);
        buttonRegister = findViewById(R.id.register_btn_Register);
    }

    private void initTextViewLogin() {
        Button btnCancel = findViewById(R.id.register_btn_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
