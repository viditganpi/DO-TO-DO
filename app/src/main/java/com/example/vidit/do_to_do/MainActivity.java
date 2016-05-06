package com.example.vidit.do_to_do;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    LoginButton loginButton;
    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    String URL = "";
    Location currentLocation = null;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
        setContentView(R.layout.activity_main);
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

    }
    @Override
    protected void onStart() {
        super.onStart();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.vidit.do_to_do",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        // If using in a fragment
        // loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                // Graph API
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                if (response.getJSONObject() != null) {
                                    //initialize shared preferences
                                    setDefaults(getString(R.string.fbuserdata), response.getJSONObject().toString(), getApplicationContext());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                // Add to shared preferences
                accessToken = loginResult.getAccessToken();
                setDefaults(getString(R.string.fbaccesstoken), accessToken.getToken(), getApplicationContext());
                setDefaults(getString(R.string.fbuserid), accessToken.getUserId(), getApplicationContext());
                processLogin();

            }

            @Override
            public void onCancel() {
                // App code
                showToast("Facebook authorization cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                showToast("Error connecting to Facebook");
            }

        });

    }
    @Override
    protected void onResume() {
        super.onResume();

            SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
            // Check whether first login
            //String fbaccessToken = sharedPref.getString(getResources().getString(R.string.fbaccesstoken), null);
            String fbaccessToken = getDefaults
                    (getResources().getString(R.string.fbaccesstoken), getApplicationContext());
            if (fbaccessToken != null && fbaccessToken.length() > 0) {
                loginButton = (LoginButton) findViewById(R.id.login_button);
                loginButton.setVisibility(View.GONE);
                Intent i = new Intent(this,Main2Activity.class);
                startActivity(i);
                finish();
            }
        //AppEventsLogger.activateApp(getApplication()); // Facebook Logs
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  AppEventsLogger.de // Facebook Logs
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public void processLogin(){
       // Intent i = new Intent(getApplicationContext(), MainActivity.class);
        //Intent i = new Intent(getApplicationContext(), MapFragment.class);
        //Intent i = new Intent(getApplicationContext(), AskFragment.class);

        //i.putExtra("places", places);
        /*startActivity(i);
        finish();*/
        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(),RegisterTask.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
