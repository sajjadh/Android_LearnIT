package com.example.assignment02;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addPhrase extends AppCompatActivity {
    private static final String TAG = addPhrase.class.getCanonicalName();

    DatabaseHelper myDB;
    Button btnSave;
    Button btnBack;
    TextView txtAddPhrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myDB = new DatabaseHelper(this);
        setTitle("ADD NEW PHRASES");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrase);
        Log.d(TAG, "onCreate: started,");

        btnSave = (Button) findViewById(R.id.btn_addPrase_Save);
        txtAddPhrase =(TextView) findViewById(R.id.txt_addPhrase_inputField);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtAddPhrase.getText().equals("Please A Enter Phrase Here")){
                    Toast.makeText(addPhrase.this, "Please Enter A Phrase", Toast.LENGTH_LONG).show();
                }else {
                    savePhrase(v);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMain();
            }
        });

        txtAddPhrase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtAddPhrase.getText().length() != 0){
                    btnSave.setEnabled(true);
                }
            }
        });

    }


    //function of Back button
    public void navigateToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //saving the phrase
    public void  savePhrase(View view){
                boolean results = false;
                if (txtAddPhrase.getText().length() !=0) {
                    results = myDB.insertPhrase(txtAddPhrase.getText().toString());
                }
               if (results) {
                   Toast.makeText(addPhrase.this, "Phrase Added Successfully!", Toast.LENGTH_LONG).show();
//                   View toastView = toast.getView();
//                   TextView viewText = (TextView) toastView.findViewById(android.R.id.message);
//                   toastView.setBackgroundColor(getResources().getColor(R.color.colorSuccessToastWhite));
//                   viewText.setTextColor(getResources().getColor(R.color.colorSuccessToastBlue));
//                   //viewText.setTextColor(getResources().getColorStateList(R.color.colorSuccessToast));
                   //toast.show();
                   txtAddPhrase.setText("");
               }else {
                  Toast.makeText(addPhrase.this, "Please Enter a valid value!", Toast.LENGTH_LONG).show();
//                   View toastView = toast.getView();
//                   TextView viewText = (TextView) toastView.findViewById(android.R.id.message);
//                   viewText.setTextColor(getResources().getColorStateList(R.color.colorErrorToast));
//                   toast.show();
               }

}

}