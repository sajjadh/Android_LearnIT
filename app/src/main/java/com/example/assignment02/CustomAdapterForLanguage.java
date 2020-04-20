package com.example.assignment02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CustomAdapterForLanguage extends ArrayAdapter<String> {

    Context context;
    ArrayList <String> selectedOptions;
    ArrayList<Integer> checkedLanguages= new ArrayList<>();
    boolean check = false;
    int checkedButtonPosition = -1;
    ArrayList<Integer> selectedLanguageList = new ArrayList<>();
    CustomLisneForCheckBox lister;

    /* REFERENCE - https://www.youtube.com/watch?v=nOdSARCVYic */

    public CustomAdapterForLanguage(@NonNull Context context, ArrayList<String> selectedOptions, ArrayList<Integer> checkedLanguages, CustomLisneForCheckBox lister) {
        super(context, R.layout.checkbox, (List<String>) selectedOptions);
        this.context = context;
        this.selectedOptions = selectedOptions;
        this.checkedLanguages = checkedLanguages;
        this.lister = lister;
    }


    //rendering the Languages listView with checkboxes
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        LayoutInflater myinflate = LayoutInflater.from(getContext());
        final View customView = myinflate.inflate(R.layout.checkbox,parent,false);
        //getting reference
        final String singleLangugage = getItem(position);
        final TextView singleLanguage= (TextView) customView.findViewById(R.id.txtLanguage);
        final CheckBox singleCheckbox= (CheckBox) customView.findViewById(R.id.languageCheckbox);

        singleLanguage.setText(singleLangugage);


        //Saving the check box in list when checked only
          singleCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedButtonPosition = position;
                checkedLanguages.add(checkedButtonPosition);
                check = true;
                notifyDataSetChanged();
                //notifying activity of checked language
                lister.getCheckedLanguages(null, checkedButtonPosition);
            }
        });


         //using onclick method (only) to uncheck the checked boxes
        singleCheckbox.setOnClickListener(new View.OnClickListener() {
            int unChecked;
            @Override
            public void onClick(View v) {
             if(!check) {
                 for (int x = 0; x < checkedLanguages.size(); x++) {
                     if (checkedLanguages.get(x) == position) {
                         unChecked = checkedLanguages.get(x);
                         checkedLanguages.remove(x);
                         //notifying activity about unchecked language
                         lister.unCheckedLanguages(null,unChecked);
                     }
                 }
             }
                notifyDataSetChanged();
            }
        });

    //rendering the checked and unchecked from list
        for (int x=0 ; x < checkedLanguages.size() ; x++) {
                if ((checkedLanguages.get(x) == position)) {
                    singleCheckbox.setOnCheckedChangeListener(null);
                    singleCheckbox.setChecked(true);
                        check = false;

            }
        }

        //passing the checked arrayList to the
    if(checkedLanguages.size()>0) {

    }

        return customView;
    }

}
