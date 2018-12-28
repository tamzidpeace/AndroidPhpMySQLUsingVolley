package com.example.arafat.androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public EditText userName, password, email;
    public Button register;
    public ProgressDialog progressDialog;
    public TextView alreadyRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        }


        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        progressDialog = new ProgressDialog(this);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);
        alreadyRegistered = findViewById(R.id.already_register);
        alreadyRegistered.setOnClickListener(this);


    }

    private void registerUser() {


        final String getUserName = userName.getText().toString().trim();
        final String getPassword = password.getText().toString().trim();
        final String getEmail = email.getText().toString().trim();

        progressDialog.setMessage("User Registering...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("username", getUserName);
                param.put("password", getPassword);
                param.put("email", getEmail);

                return param;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == register)
            registerUser();
        else if (view == alreadyRegistered) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }
}
