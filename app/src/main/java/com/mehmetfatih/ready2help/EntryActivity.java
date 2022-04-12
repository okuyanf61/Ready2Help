package com.mehmetfatih.ready2help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EntryActivity extends AppCompatActivity {

    private Button buttonRegister;
    private TextView textLogin, textPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        textPrivacyPolicy = findViewById(R.id.text_entry_privacy_policy);
        textLogin = findViewById(R.id.text_entry_login);
        buttonRegister = findViewById(R.id.button_entry_register);

        textPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW, Uri.parse("https://mehmetfatih.com/privacy-policy/")
                );
                startActivity(browserIntent);
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EntryActivity.this, SignUpActivityOne.class);
                startActivity(intent);
            }
        });

    };
}