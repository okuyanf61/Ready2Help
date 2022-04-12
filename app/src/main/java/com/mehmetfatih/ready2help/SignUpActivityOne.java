package com.mehmetfatih.ready2help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class SignUpActivityOne extends AppCompatActivity {

    private TextView textElder, textVolunteer, text8Char, text1Symbol, text1Number, textSuggestPassword, textRegisterLogin;
    private EditText editTextRegisterEmailAddress, editTextRegisterPassword;
    private ImageView buttonRegisterShowPassword;
    private Button buttonRegisterContinue1;
    private boolean isPasswordVisible = false;
    private boolean isElder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);

        textElder = findViewById(R.id.textElder);
        textVolunteer = findViewById(R.id.textVolunteer);
        text8Char = findViewById(R.id.text8Char);
        text1Symbol = findViewById(R.id.text1Symbol);
        text1Number = findViewById(R.id.text1Number);
        textSuggestPassword = findViewById(R.id.textSuggestPassword);
        textRegisterLogin = findViewById(R.id.textRegisterLogin);
        editTextRegisterEmailAddress = findViewById(R.id.editTextRegisterEmailAddress);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        buttonRegisterShowPassword = findViewById(R.id.buttonRegisterShowPassword);
        buttonRegisterContinue1 = findViewById(R.id.buttonRegisterContinue1);

        textElder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isElder) {
                    textElder.setBackgroundResource(R.drawable.elder_filled);
                    textVolunteer.setBackgroundResource(R.drawable.volunteer_empty);
                    isElder = true;
                }
            }
        });

        textVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isElder) {
                    textElder.setBackgroundResource(R.drawable.elder_empty);
                    textVolunteer.setBackgroundResource(R.drawable.volunteer_filled);
                    isElder = false;
                }
            }
        });

        editTextRegisterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (editTextRegisterPassword.getText().toString().length() >= 8) {
                    text8Char.setText(R.string.req_characters_done);
                }
                else {
                    text8Char.setText(R.string.req_characters);
                }

                if (editTextRegisterPassword.getText().toString().matches(".*[0-9].*")) {
                    text1Number.setText(R.string.req_numbers_done);
                }
                else {
                    text1Number.setText(R.string.req_numbers);
                }

                if (editTextRegisterPassword.getText().toString().matches(".*[!@#$%^&*()_+].*")) {
                    text1Symbol.setText(R.string.req_symbols_done);
                }
                else {
                    text1Symbol.setText(R.string.req_symbols);
                }

                if (
                        editTextRegisterPassword.getText().toString().length() >= 8 &&
                        editTextRegisterPassword.getText().toString().matches(".*[0-9].*") &&
                        editTextRegisterPassword.getText().toString().matches(".*[!@#$.,%^&*()_+].*")
                    )
                {
                    textSuggestPassword.setText(R.string.suggest_password_done);
                    buttonRegisterContinue1.setEnabled(true);
                    buttonRegisterContinue1.setAlpha(1);
                }
                else {
                    textSuggestPassword.setText(R.string.suggest_password);
                    buttonRegisterContinue1.setEnabled(false);
                }

                return false;
            }
        });

        buttonRegisterShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    editTextRegisterPassword.setTransformationMethod(new PasswordTransformationMethod());
                    isPasswordVisible = false;
                }
                else {
                    editTextRegisterPassword.setTransformationMethod(null);
                    isPasswordVisible = true;
                }
            }
        });

        textSuggestPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz|!£$%&/=@#";
                Random RANDOM = new Random();
                StringBuilder sb = new StringBuilder(8);

                for (int i = 0; i < 8; i++) {
                    sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
                }

                editTextRegisterPassword.setText(sb.toString());
                buttonRegisterContinue1.setEnabled(true);
                buttonRegisterContinue1.setAlpha(1);
            }
        });

        buttonRegisterContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivityOne.this, SignUpActivityTwo.class);
                intent.putExtra("email", editTextRegisterEmailAddress.getText().toString());
                intent.putExtra("password", editTextRegisterPassword.getText().toString());
                intent.putExtra("isElder", isElder);
                startActivity(intent);
            }
        });

        textRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivityOne.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}