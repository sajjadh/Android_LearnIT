package com.example.assignment02;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class displayPhrase extends AppCompatActivity {

    private static final String TAG = displayPhrase.class.getCanonicalName();
    DatabaseHelper myDB;
    ArrayList <String> savedPhrases = new ArrayList<>();
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayphrase);
        Log.d(TAG, "onCreate: started,");

        setTitle("DISPLAY PHRASES");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //getting the DB data to Curosr and then adding that to list
        Cursor obj = myDB.getAllPhrases();
        if (obj.getCount() == 0) {
            //display 0 data error message
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj.moveToNext()) {
                savedPhrases.add(obj.getString(1));
            }
            sortPhrasesAsc();
            displayPhrase();
        }

    }

    public void sortPhrasesAsc(){
        Collections.sort(savedPhrases, new AlphabeticComparator());
    }


//displaying the arraylist is list view.
    public void displayPhrase(){
        //listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, R.layout.custom_textview, savedPhrases);

        listView = (ListView) findViewById(R.id.displayPhraseList);
        listView.setAdapter(itemsAdapter);


    }

    //REFERENCE - http://www.java2s.com/Tutorial/Java/0140__Collections/Keepingupperandlowercaseletterstogether.htm
    class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = (String) o1;
            String s2 = (String) o2;
            return s1.toLowerCase().compareTo(s2.toLowerCase());
        }
    }
}
