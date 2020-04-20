package com.example.assignment02;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


class CustomAdapter extends ArrayAdapter <String> {

    Context context;
    ArrayList <String> selectedOptions;
    ArrayList<String> selectedbtn= new ArrayList<>();
    int checkedButtonPosition = -1;
    TextView singleTextView;
    RadioButton singleRadio;
    String selectedPhrase=null;
    CustomListner listner;


    public CustomAdapter(@NonNull Context context, ArrayList<String> selectedOptions, CustomListner listner) {
        super(context, R.layout.radioview, (List<String>) selectedOptions);
        this.context = context;
        this.selectedOptions = selectedOptions;
        this.listner = listner;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater myinflate = LayoutInflater.from(getContext());
        final View customView = myinflate.inflate(R.layout.radioview,parent,false);

        //getting reference from radio view
        final String singlePhrase = getItem(position);
        singleTextView = (TextView) customView.findViewById(R.id.singlePhrase);
        singleRadio = (RadioButton) customView.findViewById(R.id.radio);
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

            //sending the selected phrase to activity
            selectedPhrase = singleTextView.getText().toString();
            listner.onClickListener(selectedPhrase, position);

        }else {
            singleRadio.setChecked(false);
        }

        return customView;
    }


}


