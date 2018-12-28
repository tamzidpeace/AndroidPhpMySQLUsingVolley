package com.example.arafat.androidphpmysql;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileUserName, profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }

        profileUserName = findViewById(R.id.profile_username);
        profileEmail = findViewById(R.id.profile_email);

        SharedPefManager sharedPefManager = new SharedPefManager(this);

        profileUserName.setText(sharedPefManager.getUserName());
        profileEmail.setText(sharedPefManager.getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_id:
                SharedPefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        return true;
    }
}
