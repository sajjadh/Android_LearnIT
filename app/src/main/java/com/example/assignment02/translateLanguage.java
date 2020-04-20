package com.example.assignment02;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;


import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;

import android.os.Bundle;
import android.os.AsyncTask;
import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class translateLanguage  extends AppCompatActivity {
    private static final String TAG = displayPhrase.class.getCanonicalName();
    DatabaseHelper myDB;


    ArrayList<String> savedPhrases = new ArrayList<>();        //Retrieves the saved phrases from DB
    ArrayList<String> allLanguages = new ArrayList<>();        //allAddedLanguages
    ArrayList<String> allLanguageCodes = new ArrayList<>();       //all languages code
    ArrayList<Integer> allLanguagesStatus = new ArrayList<>(); //All languages status from DB ---> isSubscribed true/false
    ArrayList<String> subscribedLanguages = new ArrayList<>(); //Subscribed languages from Db


    ListView listView;
    Button bntTranslate;
    EditText translatedText;
    Button btnPronounce;
    Spinner spinnerSubscribedLanguage;
    ProgressBar loading;

    String translatingPhrase;
    String transaltingLanguage;
    String transaltingLanguageCode;
    Boolean translateSuccess = false;

    private LanguageTranslator translationService;
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        myDB = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Log.d(TAG, "onCreate: started,");
        textService = initTextToSpeechService();

        setTitle("LANGUAGE SUBSCRIPTION");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //getting reference to the layout componenets
        bntTranslate = (Button)findViewById(R.id.btnTranslateSelectedPhrase);
        translatedText = (EditText)findViewById(R.id.translatedLanguage);
        listView = (ListView)findViewById(R.id.translateLlanguageList);
        spinnerSubscribedLanguage = (Spinner)findViewById(R.id.spinnerSubLangList);
        btnPronounce = (Button) findViewById(R.id.btnPronounce);
        loading = (ProgressBar) findViewById(R.id.translationLoading);




        getDataFromDB();

        //Translating the phrase when clicked
        bntTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaltingLanguage = spinnerSubscribedLanguage.getSelectedItem().toString();
                if (translatingPhrase != null){
                    setLanguageCode();
                    //Executing translation of selected phrase
                    translationService = initLanguageTranslatorService();
                    new TranslationTask().execute(translatingPhrase);
                    loading.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(translateLanguage.this, "Please Select A Phrase", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Pronounce The text
        btnPronounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (translateSuccess){
                    pronounceText(v);
                } else {
                    Toast.makeText(translateLanguage.this, "Please Translate A Phrase", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setSpinnerDropdown();
        displayPhrase();
    }


/*--------------------------Language Translation process--------------------------
* REFERENCE - Languages Translation Codes were directly obtained from Tutorial 6-7 */

    //Translation process initiation
    private LanguageTranslator initLanguageTranslatorService() {
        Authenticator authenticator= new IamAuthenticator(getString(R.string.API_Key));
        LanguageTranslator service = new LanguageTranslator("2018-05-01", authenticator);
        service.setServiceUrl(getString(R.string.Translator_URL));
        return service;
    }

    //translation process
    private class TranslationTask extends AsyncTask<String, Void, String> {
        String firstTranslation = "Language Not Found";
        @Override
        protected String doInBackground(String... params) {
            TranslationResult result = null;
            TranslateOptions translateOptions = new TranslateOptions
                    .Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH)
                    .target(transaltingLanguageCode)
                    .build();
            try {
                result = translationService.translate(translateOptions).execute().getResult();
            }catch (Exception e){
                Log.e(TAG, "doInBackground: ",e );
            }
            //IBM doesnt have all the requesting languages, so its handled via an exception
            try {
                firstTranslation = result.getTranslations().get(0).getTranslation();
                translateSuccess = true;
            }catch (Exception e) {
                Log.e(TAG, "doInBackground: ", e);
                translateSuccess = false;
            }
            return firstTranslation;
        }

        //setting up the translated test
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translatedText.setText("");
            translatedText.setText(s);
            if (translateSuccess) {
                Toast.makeText(translateLanguage.this, "Successfully Translated", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(translateLanguage.this, "Translation Failed", Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);
        }
    }


    /*--------------------------Language Pronunciation process--------------------------
    * REFERENCE - Languages Translation Codes were directly obtained from Tutorial 6-7 */

    public void pronounceText(View view) {

        new SynthesisTask().execute(translatedText.getText().toString());
    }

    private TextToSpeech initTextToSpeechService() {
        Authenticator authenticator = new IamAuthenticator(getString(R.string.Pronounce_API_KEY));
        TextToSpeech service = new TextToSpeech(authenticator);
        service.setServiceUrl(getString(R.string.Pronounce_URL));
        return service;
    }


    private class SynthesisTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
                    .text(params[0])
                    .voice(SynthesizeOptions
                            .Voice.EN_US_LISAVOICE)
                    .accept(HttpMediaType.AUDIO_WAV)
                    .build();

            player.playStream(textService.synthesize(synthesizeOptions).execute().getResult());

            return "Did synthesize";
        }
    }





    /*-----------------------------Displaying the tasnaltion view content---------------*/

    //Getting DB Date
    public void getDataFromDB(){

        //getting the Added phrases from DB
        Cursor obj = myDB.getAllPhrases();
        if (obj.getCount() == 0) {
            //display 0 data error message
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj.moveToNext()) {
                savedPhrases.add(obj.getString(1));

            }
        }

        //getting all the languages & isSubscribed from DB
        Cursor obj2 = myDB.getAllLangauges();
        if (obj.getCount() == 0) {
            Toast.makeText(translateLanguage.this,"DB Error",Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj2.moveToNext()) {
                allLanguages.add(obj2.getString(1));
                allLanguagesStatus.add(((obj2.getInt(2))));
                allLanguageCodes.add(obj2.getString(3));
            }
        }
    }


    //setting up the spinner item list
    public void setSpinnerDropdown(){
        //subscribedLanguages.add(0,"Select Language");
        //extracting the subscribed languages
        for (int x=0 ; x<allLanguagesStatus.size(); x++){
            if(allLanguagesStatus.get(x) == 1){
                subscribedLanguages.add(allLanguages.get(x));
            }
        }

        //Adding the subscribed languages to spinner view
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.custom_textview, subscribedLanguages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubscribedLanguage.setAdapter(dataAdapter);
    }


    //displaying the phrases in listview
    //getting selected translating phrase
    public void displayPhrase(){
        ListAdapter myAdapter = new CustomAdapterForTranslate(this, savedPhrases, new CustomListner() {
            @Override
            public void onClickListener(String text, int position) {
                translatingPhrase = text;
            }
        });
        listView.setAdapter(myAdapter);
    }

    //setting the language code to the corresponding selected language
    public void setLanguageCode(){
        for (int x =0; x< allLanguages.size() ;x++){
            if(allLanguages.get(x) == transaltingLanguage){
                transaltingLanguageCode = allLanguageCodes.get(x);
            }
        }


    }
}

