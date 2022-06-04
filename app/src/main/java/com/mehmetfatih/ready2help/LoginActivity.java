package com.mehmetfatih.ready2help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmailAddress, editTextLoginPassword;
    private TextView textLoginRegister, textLoginForgotPassword;
    private Button buttonLoginContinue;
    private ImageView buttonLoginShowPassword;
    private boolean isPasswordVisible = false;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginEmailAddress = findViewById(R.id.editTextLoginEmailAddress);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        textLoginRegister = findViewById(R.id.textLoginRegister);
        textLoginForgotPassword = findViewById(R.id.textLoginForgotPassword);
        buttonLoginContinue = findViewById(R.id.buttonLoginContinue);
        buttonLoginShowPassword = findViewById(R.id.buttonLoginShowPassword);

        buttonLoginShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible) {
                    editTextLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
                    isPasswordVisible = false;
                }
                else {
                    editTextLoginPassword.setTransformationMethod(null);
                    isPasswordVisible = true;
                }
            }
        });

        textLoginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"mfo@mehmetfatih.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Ready2Help Password Recovery");
                email.putExtra(Intent.EXTRA_TEXT, "Please enter your email address and we will send you a recovery link.");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        buttonLoginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if email and password are correct in firebase
                // If correct, check user type and redirect to appropriate activity
                // If not, show error message
                String email = editTextLoginEmailAddress.getText().toString();
                String password = editTextLoginPassword.getText().toString();
                DocumentReference docRef = db.collection("people").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                boolean isElder = document.getBoolean("elder");
                                Intent intent;
                                if (isElder) {
                                    intent = new Intent(LoginActivity.this, ElderActivity.class);
                                }
                                else {
                                    intent = new Intent(LoginActivity.this, VolunteerActivity.class);
                                }
                                intent.putExtra("name", document.getString("name"));
                                intent.putExtra("phoneNumber", document.getString("phoneNumber"));
                                intent.putExtra("male", document.getBoolean("male"));
                                intent.putExtra("dateOfBirth", document.getString("dateOfBirth"));
                                intent.putExtra("city", document.getString("city"));
                                intent.putExtra("owner", email);
                                startActivity(intent);
                            }
                            else {
                                editTextLoginEmailAddress.setError("Email address is not registered");
                            }
                        }
                    }
                });

            }
        });

        textLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(LoginActivity.this, SignUpActivityOne.class);
                startActivity(register);
            }
        });

    }
}