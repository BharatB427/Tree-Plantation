package com.example.uman_android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.uman_android_project.fragment.HomeFragment;
import com.example.uman_android_project.fragment.ProfileFragment;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.uman_android_project.MainActivity.db;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    public static final String PARCEL_KEY = "parcel_key";
    private static final String EMAIL = "email";
    LoginButton loginButton;
    private AccessTokenTracker mtracker = null;
    private ProfileTracker mprofileTracker = null;
    boolean isLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
         isLoggedIn = accessToken != null && !accessToken.isExpired();
        Bundle extra = getIntent().getExtras();
        Profile profile = Profile.getCurrentProfile();
        if (profile!=null && extra == null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);
        mtracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldAccessToken + "||" + "CurrentAccessToken" + currentAccessToken);
            }
        };


        mprofileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" + currentProfile);


            }
        };

        mtracker.startTracking();
        mprofileTracker.startTracking();


        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //get Facebook profile
                Profile profile = Profile.getCurrentProfile();
                String Username= profile.getName();
                String lastName= profile.getLastName();
//                String Username= "Jacky";
//                String lastName= "Jau";
                editor.putString("userName", Username);
                editor.putString("lastName", lastName);
                //check if the farmer is the first time to log in
                Query query = FirebaseFirestore.getInstance()
                        .collection("farmer")
                        .whereEqualTo("userName", Username)
                        .whereEqualTo("lastName", lastName);
                query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean isExisting = false;
                        for (DocumentSnapshot ds : queryDocumentSnapshots) {
                            String uName = ds.getString("userName");
                            String lName = ds.getString("lastName");
                            if (uName.equals(Username) && lName.equals(lastName)) {
                                isExisting = true;
                                String userId = ds.getString("userId");
                                editor.putString("id", userId);
                                editor.commit();
                                Log.d("TAG", "User exist, His ID : " + userId);
                            }
                        }
                        if (!isExisting) {
                            String userId = getAccountIdByUUId();
                            editor.putString("id", userId);
                            editor.commit();
                            Map<String, Object> user = new HashMap<>();
                            user.put("userName", Username);
                            user.put("lastName", lastName);
                            user.put("userId", userId);
                            db.collection("farmer")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("TAG", "Error adding document", e);
                                        }
                                    });
                        }
                    }
                });
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }




    //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    public static String getAccountIdByUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        return String.format("%016d", hashCodeV);
    }

}