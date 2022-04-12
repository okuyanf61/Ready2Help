package com.mehmetfatih.ready2help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivityThree extends AppCompatActivity {

    private EditText editTextRegisterCountryCode, editTextRegisterPhoneNumber;
    private Button buttonRegisterContinue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_three);

        editTextRegisterCountryCode = findViewById(R.id.editTextRegisterCountryCode);
        editTextRegisterPhoneNumber = findViewById(R.id.editTextRegisterPhoneNumber);
        buttonRegisterContinue3 = findViewById(R.id.buttonRegisterContinue3);

        buttonRegisterContinue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oldIntent = getIntent();
                Intent intent = new Intent(SignUpActivityThree.this, LoginActivity.class);
                intent.putExtra("phoneNumber", editTextRegisterCountryCode.getText().toString() + editTextRegisterPhoneNumber.getText().toString());
                intent.putExtras(oldIntent);
                startActivity(intent);
            }
        });

    }
}