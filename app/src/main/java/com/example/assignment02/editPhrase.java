package com.example.assignment02;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class editPhrase extends AppCompatActivity{
    private static final String TAG = editPhrase.class.getCanonicalName();
    DatabaseHelper myDB;
    ArrayList <String> savedPhrases = new ArrayList<>();
    ListView listView;
    RadioButton radioBtn;
    Button btnEdit;
    Button btnSave;
    TextView txtPhrase;
    String selectedPhrase;
    int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editphrase);
        Log.d(TAG, "onCreate: started,");


        setTitle("EDIT PHRASES");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        myDB = new DatabaseHelper(this);
        radioBtn = (RadioButton) findViewById(R.id.radio);
        txtPhrase = (TextView) findViewById(R.id.singlePhrase);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnSave = (Button) findViewById(R.id.btnSave);
        final EditText editPhrase = (EditText) findViewById(R.id.editTextField);
        listView = (ListView) findViewById(R.id.displayPhraseList);


        //getting the Added phrases to DB
        Cursor obj = myDB.getAllPhrases();
        if (obj.getCount() == 0) {
            //display 0 data error message
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj.moveToNext()) {
                savedPhrases.add(obj.getString(1));
            }
            //sortPhrasesAsc();
            displayPhrase();

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPhrase.setText(selectedPhrase);
                    Toast.makeText(editPhrase.this, "Editing the selected Phrase", Toast.LENGTH_SHORT).show();
                }
            });

        }


            btnSave.setOnClickListener(new View.OnClickListener() {
               // String editText = String.valueOf(editPhrase.getText());

                @Override
                public void onClick(View v) {

                   Boolean result = myDB.updatePhrases(selectedPosition, editPhrase.getText().toString());
                   if (!result){
                       Toast.makeText(editPhrase.this, "Update Failed", Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(editPhrase.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                       finish();
                       startActivity(getIntent());
                   }
                }
            });
        }


    //displaying the arraylist is list view and getting selected option.
    public void displayPhrase(){
        ListAdapter myAdapter = new CustomAdapter(this, savedPhrases, new CustomListner() {
            // get selected text here
            @Override
            public void onClickListener(String text, int position) {
                //Toast.makeText(editPhrase.this, "Test -" + text + "/n Position -" + position, Toast.LENGTH_SHORT).show();
                selectedPhrase = text;
                selectedPosition = position+1;
            }
        });

        listView.setAdapter(myAdapter);
    }




}
