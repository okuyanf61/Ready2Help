package com.mehmetfatih.ready2help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent oldIntent = getIntent();

        // Get user's name, email, phone, gender and date of birth from oldIntent.
        String name = oldIntent.getStringExtra("name");
        String email = oldIntent.getStringExtra("owner");
        String phone = oldIntent.getStringExtra("phoneNumber");
        String dateOfBirth = oldIntent.getStringExtra("dateOfBirth");
        String gender;
        if (oldIntent.getBooleanExtra("male", true)) {
            gender = "Male";
        }
        else {
            gender = "Female";
        }

        // Define views
        TextView textName = findViewById(R.id.profileName);
        TextView textEmail = findViewById(R.id.profileMail);
        TextView textPhone = findViewById(R.id.profilePhone);
        TextView textDateOfBirth = findViewById(R.id.profileDateOfBirth);
        TextView textGender = findViewById(R.id.profileGender);

        // Set text to views
        textName.setText(name);
        textEmail.setText(email);
        textPhone.setText(phone);
        textDateOfBirth.setText(dateOfBirth);
        textGender.setText(gender);

    }
}