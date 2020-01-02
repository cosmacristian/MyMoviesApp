package com.example.mymovies.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymovies.R;
import com.example.mymovies.SQLiteDB.SQLiteHelper;
import com.example.mymovies.Models.User;

public class MainActivity extends AppCompatActivity {

    //Declaration EditTexts
    EditText editTextEmail;
    EditText editTextPassword;

    //Declaration Button
    Button buttonLogin;
    Button buttonSignin;

    //Declaration SqliteHelper
    SQLiteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteHelper = new SQLiteHelper(this);
        initCreateAccountTextView();
        initViews();

        //set click event of login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Get values from EditText fields
                String Email = editTextEmail.getText().toString();
                String Password = editTextPassword.getText().toString();
                if(validate()) {
                    //Authenticate user
                    User currentUser = sqliteHelper.Authenticate(new User(null, null, Email, Password, null));

                    //Check Authentication is successful or not
                    if (currentUser != null) {
                        Toast.makeText(getApplicationContext(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();
                        //User Logged in Successfully Launch You home screen activity

                        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("currentUser", currentUser);
                        startActivity(intent);
                        finish();
                    } else {

                        //User Logged in Failed
                        Toast.makeText(getApplicationContext(), "Failed to log in , please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.login_et_email);
        editTextPassword = findViewById(R.id.login_et_passwd);
        buttonLogin = findViewById(R.id.login_btn_login);
    }

    private void initCreateAccountTextView() {
        Button textViewCreateAccount = findViewById(R.id.login_btn_signin);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate(){
        boolean valid = false;

        //Get values from EditText fields
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();


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
}
