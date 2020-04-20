package com.example.assignment02;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;




import androidx.appcompat.app.AppCompatActivity;

public class langaugeSubscription extends AppCompatActivity {

    private static final String TAG = displayPhrase.class.getCanonicalName();
    DatabaseHelper myDB;
    ArrayList<String> languagesList = new ArrayList<>();                //Retrieved DP values been converted to integer and passed to the adapter
    ArrayList<String> addedLanguagesList = new ArrayList<>();           //Static Languages String Names passed to the list view
    ArrayList<String> addedLanguagesListCode = new ArrayList<>();       //Static Languages Codes
    ArrayList<Integer> selectedLanguageListToUser = new ArrayList<>();  //Static String langauge Names - and passing the list to DB to store
    ArrayList<Integer> selectedLanguageListFromDB = new ArrayList<>();  //Getting the Subscribed Languges --> Integer 0/1 Value
    ArrayList<Integer> checkedLanguages = new ArrayList<>();         // Getting User checked Langugaes --> To Update DB
    ArrayList<Integer> unCheckedLanguages = new ArrayList<>();      // getting unChecked Languages
    ListView listView;
    Button btnUpdate;
    TextView displayTranslatedText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDB = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_subscription);
        Log.d(TAG, "onCreate: started,");

        setTitle("LANGUAGE SUBSCRIPTION");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        btnUpdate = (Button) findViewById(R.id.btnLangUpdate);
        displayTranslatedText = (TextView) findViewById(R.id.translatedLanguage);


        //getting the DB data to Curosr and then adding that to list
        Cursor obj = myDB.getAllLangauges();
        if (obj.getCount() == 0) {
            addLanguagesToDB(); // adding langs if all langs are deleted
            Toast.makeText(langaugeSubscription.this,"DB Error",Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj.moveToNext()) {
                addedLanguagesList.add(obj.getString(1));
            }
            convertToInt();
            displayLanguages();
        }

        //Onclick Update saving the updated value in DB
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            Boolean result;
            @Override
            public void onClick(View v) {
                //updates the checked items
                for (int x=0; x<checkedLanguages.size(); x++) {
                    result = myDB.updateSubscribedLangauges(checkedLanguages.get(x), true);
                }
                //updates the unchecked items
                for (int x=0; x<unCheckedLanguages.size(); x++) {
                    result = myDB.updateSubscribedLangauges(unCheckedLanguages.get(x), false);
                }

                if(result != null) {
                    if (!result) {
                        Toast.makeText(langaugeSubscription.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(langaugeSubscription.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                }else{
                    Toast.makeText(langaugeSubscription.this, "Nothing to update", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    //converting the boolean subscription to integer
    //I am using integer array to add/remove the checked/unchecked langs
    // When i am retrieving the languages list from DB, converting it to integer
    public void convertToInt(){
        Cursor obj = myDB.getAllLangauges();
        if (obj.getCount() == 0) {
            Toast.makeText(langaugeSubscription.this,"DB Error",Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (obj.moveToNext()) {
                selectedLanguageListFromDB.add(((obj.getInt(2))));
            }

        }

        for (int x=0 ; x < selectedLanguageListFromDB.size() ; x++){
            if(selectedLanguageListFromDB.get(x) == 1){
                selectedLanguageListToUser.add(x);
                checkedLanguages.add(x+1);
            }
        }
    }



    //adding the content to custom adapter and displaying the languages
    public void displayLanguages(){

        listView = (ListView) findViewById(R.id.languageList);
        ListAdapter myLangAdapter = new CustomAdapterForLanguage(this, addedLanguagesList,selectedLanguageListToUser, new CustomLisneForCheckBox() {

            //Items added to checked list will be checked
            @Override
            public void getCheckedLanguages(String value1, int checkedItems) {
                if (checkedItems!= -1) {
                    int check = checkedItems + 1;
                    checkedLanguages.add((check));

                for (int x=0 ; x<unCheckedLanguages.size() ; x++){
                        if (unCheckedLanguages.get(x)==check){
                            unCheckedLanguages.remove(x);
                        }
                    }
                }
            }
            //Added unchecked items will be marked as - unchecked
            @Override
            public void unCheckedLanguages(String value1, int unCheckedItems) {
                if (unCheckedItems != -1) {
                    int uncheck = unCheckedItems + 1;
                    checkedLanguages.remove(new Integer(uncheck));
                    unCheckedLanguages.add(uncheck);
                }
            }
        });

        listView.setAdapter(myLangAdapter);

    }

    //adding all the languages and its code to DB
    public void addLanguagesToDB(){

        languagesList.add("Afrikaans");
        languagesList.add("Albanian");
        languagesList.add("Arabic");
        languagesList.add("Armenian");
        languagesList.add("Azerbaijani");
        languagesList.add("Bashkir");
        languagesList.add("Basque");
        languagesList.add("Belarusian");
        languagesList.add("Bengali");
        languagesList.add("Bosnian");
        languagesList.add("Bulgarian");
        languagesList.add("Catalan");
        languagesList.add("Central Khmer");
        languagesList.add("Chinese (Simplified)");
        languagesList.add("Chinese (Traditional)");
        languagesList.add("Chuvash");
        languagesList.add("Croatian");
        languagesList.add("Czech");
        languagesList.add("Danish");
        languagesList.add("Dutch");
        languagesList.add("English");
        languagesList.add("Esperanto");
        languagesList.add("Estonian");
        languagesList.add("Finnish");
        languagesList.add("French");
        languagesList.add("Georgian");
        languagesList.add("German");
        languagesList.add("Greek");
        languagesList.add("Gujarati");
        languagesList.add("Haitian");
        languagesList.add("Hebrew");
        languagesList.add("Hindi");
        languagesList.add("Hungarian");
        languagesList.add("Icelandic");
        languagesList.add("Indonesian");
        languagesList.add("Irish");
        languagesList.add("Italian");
        languagesList.add("Japanese");
        languagesList.add("Kazakh");
        languagesList.add("Kirghiz");
        languagesList.add("Korean");
        languagesList.add("Kurdish");
        languagesList.add("Latvian");
        languagesList.add("Lithuanian");
        languagesList.add("Malay");
        languagesList.add("Malayalam");
        languagesList.add("Maltese");
        languagesList.add("Mongolian");
        languagesList.add("Norwegian Bokmal");
        languagesList.add("Norwegian Nynorsk");
        languagesList.add("Panjabi");
        languagesList.add("Persian");
        languagesList.add("Polish");
        languagesList.add("Portuguese");
        languagesList.add("Pushto");
        languagesList.add("Romanian");
        languagesList.add("Russian");
        languagesList.add("Serbian");
        languagesList.add("Slovakian");
        languagesList.add("Slovenian");
        languagesList.add("Somali");
        languagesList.add("Spanish");
        languagesList.add("Swedish");
        languagesList.add("Tamil");
        languagesList.add("Telugu");
        languagesList.add("Thai");
        languagesList.add("Turkish");
        languagesList.add("Ukrainian");
        languagesList.add("Urdu");
        languagesList.add("Vietnamese");



        addedLanguagesListCode.add("af");
        addedLanguagesListCode.add("sq");
        addedLanguagesListCode.add("ar");
        addedLanguagesListCode.add("hy");
        addedLanguagesListCode.add("az");
        addedLanguagesListCode.add("ba");
        addedLanguagesListCode.add("eu");
        addedLanguagesListCode.add("be");
        addedLanguagesListCode.add("bn");
        addedLanguagesListCode.add("bs");
        addedLanguagesListCode.add("bg");
        addedLanguagesListCode.add("ca");
        addedLanguagesListCode.add("km");
        addedLanguagesListCode.add("zh");
        addedLanguagesListCode.add("zh-TW");
        addedLanguagesListCode.add("cv");
        addedLanguagesListCode.add("hr");
        addedLanguagesListCode.add("cs");
        addedLanguagesListCode.add("da");
        addedLanguagesListCode.add("nl");
        addedLanguagesListCode.add("en");
        addedLanguagesListCode.add("eo");
        addedLanguagesListCode.add("et");
        addedLanguagesListCode.add("fi");
        addedLanguagesListCode.add("fr");
        addedLanguagesListCode.add("ka");
        addedLanguagesListCode.add("de");
        addedLanguagesListCode.add("el");
        addedLanguagesListCode.add("gu");
        addedLanguagesListCode.add("ht");

        addedLanguagesListCode.add("he");
        addedLanguagesListCode.add("hi");
        addedLanguagesListCode.add("hu");
        addedLanguagesListCode.add("is");
        addedLanguagesListCode.add("id");
        addedLanguagesListCode.add("ga");
        addedLanguagesListCode.add("it");
        addedLanguagesListCode.add("ja");
        addedLanguagesListCode.add("kk");
        addedLanguagesListCode.add("ky");
        addedLanguagesListCode.add("ko");
        addedLanguagesListCode.add("ku");
        addedLanguagesListCode.add("lv");
        addedLanguagesListCode.add("lt");
        addedLanguagesListCode.add("ms");
        addedLanguagesListCode.add("ml");
        addedLanguagesListCode.add("mt");
        addedLanguagesListCode.add("mn");
        addedLanguagesListCode.add("nb");
        addedLanguagesListCode.add("nn");
        addedLanguagesListCode.add("pa");
        addedLanguagesListCode.add("fa");
        addedLanguagesListCode.add("pl");
        addedLanguagesListCode.add("pt");
        addedLanguagesListCode.add("ps");
        addedLanguagesListCode.add("ro");
        addedLanguagesListCode.add("ru");
        addedLanguagesListCode.add("sr");
        addedLanguagesListCode.add("sk");
        addedLanguagesListCode.add("sl");
        addedLanguagesListCode.add("so");
        addedLanguagesListCode.add("es");
        addedLanguagesListCode.add("sv");
        addedLanguagesListCode.add("ta");
        addedLanguagesListCode.add("te");
        addedLanguagesListCode.add("tr");
        addedLanguagesListCode.add("th");
        addedLanguagesListCode.add("uk");
        addedLanguagesListCode.add("ur");
        addedLanguagesListCode.add("vi");


        //adding all languages to DB manually
            for (int i = 0; i < languagesList.size(); i++) {
                Boolean result = myDB.insertDataLanguageSubscription(languagesList.get(i),addedLanguagesListCode.get(i));
            }
    }

}
