package com.example.android.funkytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    ArrayList<User> userArrayList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userArrayList = ((GlobalVariables) this.getApplication()).getUserArrayList();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSignUp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });
    }

    public void signUp(View view) {
        String username;
        String phone;
        String email;

        EditText inputUsername = (EditText) findViewById(R.id.editAddUsername);
        username = inputUsername.getText().toString();
        EditText inputPhone = (EditText) findViewById(R.id.editAddPhone);
        EditText inputEmail = (EditText) findViewById(R.id.editAddEmail);
        email = inputEmail.getText().toString();
        phone = inputPhone.getText().toString();

        if (username == null || username.isEmpty() || phone == null || phone.isEmpty() || email == null || email.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "One or more fields are missing.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            checkForClash(username, phone, email);
        }
    }

    public void checkForClash(String username, String phone, String email) {
        User userToAdd = new User(username, email, phone);

        if (username.length() < 8) {
            Toast.makeText(SignUpActivity.this, "Username needs to be at least 8 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)){
            Toast.makeText(SignUpActivity.this,"Invalid email address",Toast.LENGTH_SHORT).show();
            return;
        }

        ElasticSearchController.GetAllUsers allUsers = new ElasticSearchController.GetAllUsers();
        allUsers.execute(); // grab all current users in the system

        ArrayList<User> userList = new ArrayList<User>();
        try {
            userList = allUsers.get();
        } catch (Exception e) {
            Log.e("Error", "Failed to get list of users");
        }

        for (User postedUser : userList) {
            Log.e("ALl usernames", postedUser.getUsername());
            if (postedUser.getUsername().equals(userToAdd.getUsername())) {
                Toast.makeText(SignUpActivity.this, "Username Taken", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ElasticSearchController.PostUser postUser = new ElasticSearchController.PostUser();
        postUser.execute(userToAdd); // adding new user to elastic search


        ElasticSearchController.GetUser getUser = new ElasticSearchController.GetUser();
        getUser.execute(userToAdd.getUsername());

        // FOR TESTING PURPOSE THAT THE USER WAS ADDED PROPERLY TO DATABASE AFTER BEING ADDED
        User newuser;
        try {
            newuser = getUser.get();
            Log.e("Got the username: ", newuser.getUsername());
        } catch (Exception e) {
            Log.e("Error", "We arnt getting the user");
        }
        // -------------------------------------------------------------------------


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}



