package com.mehmetfatih.ready2help;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivityTwo extends AppCompatActivity {

    private EditText editTextRegisterName, editTextDay, editTextMonth, editTextYear;
    private TextView textViewMale, textViewFemale;
    private AutoCompleteTextView autoCompleteTextView;
    private Button buttonRegisterContinue2;
    private boolean isMale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_two);

        editTextRegisterName = findViewById(R.id.editTextRegisterName);
        editTextDay = findViewById(R.id.editTextDay);
        editTextMonth = findViewById(R.id.editTextMonth);
        editTextYear = findViewById(R.id.editTextYear);
        textViewMale = findViewById(R.id.textViewMale);
        textViewFemale = findViewById(R.id.textViewFemale);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        buttonRegisterContinue2 = findViewById(R.id.buttonRegisterContinue2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CITIES);

        autoCompleteTextView.setAdapter(adapter);

        textViewMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMale) {
                    textViewMale.setAlpha(1);
                    textViewFemale.setAlpha(0.5f);
                    isMale = true;
                }
            }
        });

        textViewFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMale) {
                    textViewFemale.setAlpha(1);
                    textViewMale.setAlpha(0.5f);
                    isMale = false;
                }
            }
        });

        buttonRegisterContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oldIntent = getIntent();
                Intent intent = new Intent(SignUpActivityTwo.this, SignUpActivityThree.class);
                intent.putExtra("name", editTextRegisterName.getText().toString());
                intent.putExtra("day", editTextDay.getText().toString());
                intent.putExtra("month", editTextMonth.getText().toString());
                intent.putExtra("year", editTextYear.getText().toString());
                intent.putExtra("city", autoCompleteTextView.getText().toString());
                intent.putExtra("isMale", isMale);
                intent.putExtras(oldIntent);
                startActivity(intent);
            }
        });

    }

    private static final String[] CITIES = new String[] {
            "İstanbul", "Ankara", "İzmir", "Muğla", "Antalya"
    };

}