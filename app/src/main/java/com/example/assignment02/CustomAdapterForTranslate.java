package com.example.assignment02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/* REFERENCE - https://www.youtube.com/watch?v=nOdSARCVYic */

public class CustomAdapterForTranslate extends ArrayAdapter<String> {

    Context context;
    ArrayList <String> selectedOptions;
    ArrayList<String> selectedbtn= new ArrayList<>();
    int checkedButtonPosition = -1;
    TextView singleTextView;
    RadioButton singleRadio;
    String selectedPhrase=null;
    CustomListner listner;


    public CustomAdapterForTranslate(@NonNull Context context, ArrayList<String> selectedOptions, CustomListner listner) {
        super(context, R.layout.translate_radio, (List<String>) selectedOptions);
        this.context = context;
        this.selectedOptions = selectedOptions;
        this.listner = listner;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflate = LayoutInflater.from(getContext());
        final View customView = myinflate.inflate(R.layout.translate_radio, parent, false);

        //getting reference from radio view
        final String singlePhrase = getItem(position);
        singleTextView = (TextView) customView.findViewById(R.id.translatePhraseTextView);
        singleRadio = (RadioButton) customView.findViewById(R.id.radioTrasnlatebtn);
        singleTextView.setText(singlePhrase);

        //allowing only one selection for radio button
        singleRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedButtonPosition = position;
                //listner.onClickListener(singleTextView.getText().toString());
                notifyDataSetChanged();
            }
        });


        //maintaining single selection
        if (checkedButtonPosition == position){
            singleRadio.setOnCheckedChangeListener(null);
            singleRadio.setChecked(true);
            singleRadio.setBackgroundColor(R.color.colorErrorToast);
            //sending the selected phrase to activity
            selectedPhrase = singleTextView.getText().toString();
            listner.onClickListener(selectedPhrase, position);

        }else {
            singleRadio.setChecked(false);
        }

        return customView;

    }
}
