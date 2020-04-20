package com.example.assignment02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;

    private Button btn_Add_Phrase;
    private Button btn_Display_Phrase;
    private Button btn_Edit_Phrase;
    private Button btn_Language_Subscription;
    private Button btn_Translate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("   Learn It ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDB = new DatabaseHelper(this);

        btn_Add_Phrase = findViewById(R.id.btnAddPhrases);
        btn_Display_Phrase = findViewById(R.id.btnDisplayPhrases);
        btn_Edit_Phrase = findViewById(R.id.btnEditPhrase);
        btn_Language_Subscription = findViewById(R.id.btnLanguageSub);
        btn_Translate = findViewById(R.id.btnTranslate);



        btn_Add_Phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddPhrase(v);
            }
        });


        btn_Display_Phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateDisplayPhrase(v);
            }
        });

        btn_Edit_Phrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateEditPhrase(v);
            }
        });

        btn_Language_Subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateLangugaeSubscription(v);
            }
        });

        btn_Translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTranslate(v);
            }
        });


    }

    private void navigateTranslate(View v) {
        Intent intent = new Intent(this, translateLanguage.class);
        startActivity(intent);
    }

    private void navigateLangugaeSubscription(View v) {
        Intent intent = new Intent(this, langaugeSubscription.class);
        startActivity(intent);
    }


    public void navigateToAddPhrase(View view) {
        Intent intent = new Intent(this, addPhrase.class);
        startActivity(intent);
    }

    public void navigateDisplayPhrase(View view) {
        Intent intent = new Intent(this, displayPhrase.class);
        startActivity(intent);
    }

    private void navigateEditPhrase(View view) {
        Intent intent = new Intent(this, editPhrase.class);
        startActivity(intent);
    }


}
