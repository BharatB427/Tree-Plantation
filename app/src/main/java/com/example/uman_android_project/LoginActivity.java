package com.example.uman_android_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uman_android_project.model.AdminUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static AdminUser admin;
    public static String adminName, password;

    private Button offlineButton, login;
    private EditText etName, etPsw;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", "admin");
        editor.putString("password", "admin");
        editor.commit();

        /*offlineButton= findViewById(R.id.button2);
        offlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });*/

        etName = findViewById(R.id.adminName);
        etPsw = findViewById(R.id.adminPassword);

        login = findViewById(R.id.button_login);



    }

    public void adminLogin(View view){
        adminName = etName.getText().toString();
        password = etPsw.getText().toString();
        Log.i("Admin", adminName+" "+password);

        Log.i("admin", sharedPreferences.getString("username", null)+ " "+ sharedPreferences.getString("password", null));

        if(sharedPreferences.getString("username", null).equals(adminName) &&
                sharedPreferences.getString("password", null).equals(password)) {
            Toast.makeText(LoginActivity.this,"Login successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        /*Query query = FirebaseFirestore.getInstance()
                .collection("adminuser")
                .whereEqualTo("name", adminName);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("Data", "Error :" + error.getMessage());
                    return;
                }
                List<AdminUser> adminUserList = value.toObjects(AdminUser.class);
                Log.i("Admin", value.toObjects(AdminUser.class).toString());
                for(AdminUser adminUser: adminUserList){
                    System.out.println(adminUser.getName()+" "+adminUser.getPassword());
                    if(adminUser.getPassword() == password) {
                        Log.i("Admin", adminUser.getPassword());
                        admin = adminUser;
                        if(admin.getName() == adminName){
                            Toast.makeText(LoginActivity.this,"Login successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }

            }
        });*/

    }


}