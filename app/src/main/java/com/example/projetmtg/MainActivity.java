package com.example.projetmtg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.search_button).setOnClickListener(v->{

            EditText CardName = findViewById(R.id.CardName);
            EditText CardType = findViewById(R.id.CardType);

            CheckBox CIW = findViewById(R.id.CIW);
            CheckBox CIU = findViewById(R.id.CIU);
            CheckBox CIB = findViewById(R.id.CIB);
            CheckBox CIR = findViewById(R.id.CIR);
            CheckBox CIG = findViewById(R.id.CIG);

            CheckBox CW = findViewById(R.id.CW);
            CheckBox CU = findViewById(R.id.CU);
            CheckBox CB = findViewById(R.id.CB);
            CheckBox CR = findViewById(R.id.CR);
            CheckBox CG = findViewById(R.id.CG);

            String querry = "?";
            String[] colorFilter = new String[5];
            String[] colorIdentityFilter = new String[5];

            if (!String.valueOf(CardName.getText()).equals("")){
                querry += "name="+String.valueOf(CardName.getText());
            }

            if (!String.valueOf(CardType.getText()).equals("")){
                querry += "&type="+String.valueOf(CardType.getText());
            }

            Log.i("querry", querry);

//            prepare the querry and the filter according to the check boxes
            if (CW.isChecked()||CU.isChecked()||CB.isChecked()||CR.isChecked()||CG.isChecked()){
                querry +="&colors=";

                boolean isFirst = true;

                if (CW.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "white";
                    isFirst = false;
                    colorFilter[0]="White";
                }else{
                    colorFilter[0]="";
                }

                if (CU.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "blue";
                    isFirst = false;
                    colorFilter[1]="Blue";
                }else{
                    colorFilter[1]="";
                }

                if (CB.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "black";
                    isFirst = false;
                    colorFilter[2]="Black";
                }else{
                    colorFilter[2]="";
                }

                if (CR.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "red";
                    isFirst = false;
                    colorFilter[3]="Red";
                }else{
                    colorFilter[3]="";
                }


                if (CG.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "green";
                    colorFilter[4]="Green";
                }else{
                    colorFilter[4]="";
                }
            }

            Log.i("querry", querry);

//            prepare the querry and the filter according to the check boxes
            if (CIW.isChecked()||CIU.isChecked()||CIB.isChecked()||CIR.isChecked()||CIG.isChecked()){

                querry += "&colorIdentity=";

                boolean isFirst = true;

                if (CIW.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "W";
                    isFirst = false;
                    colorIdentityFilter[0] = "W";
                }else{
                    colorIdentityFilter[0] = "";
                }


                if (CIU.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "U";
                    isFirst = false;
                    colorIdentityFilter[1] = "U";
                }else{
                    colorIdentityFilter[1] = "";
                }

                if (CIB.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "B";
                    isFirst = false;
                    colorIdentityFilter[2] = "B";
                }else{
                    colorIdentityFilter[2] = "";
                }

                if (CIR.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "R";
                    isFirst = false;
                    colorIdentityFilter[3] = "R";
                }else{
                    colorIdentityFilter[3] = "";
                }

                if (CIG.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "G";
                    colorIdentityFilter[4] = "G";
                }else{
                    colorIdentityFilter[4] = "";
                }
            }
            
            Log.i("querry", querry);

//            preparing the intent and setting up all extras
            Intent activity = new Intent(getApplicationContext(), List.class);
            activity.putExtra("querry", querry);
            activity.putExtra("colorFilter", colorFilter);
            activity.putExtra("colorIdentityFilter", colorIdentityFilter);

            startActivity(activity);
        });

        findViewById(R.id.goFavorite).setOnClickListener(v->{
            Intent activity = new Intent(getApplicationContext(), ListFavorite.class);
            startActivity(activity);
        });
    }
}