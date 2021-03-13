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

            if (!String.valueOf(CardName.getText()).equals("")){
                querry += "name="+String.valueOf(CardName.getText());
            }

            if (!String.valueOf(CardType.getText()).equals("")){
                querry += "&type="+String.valueOf(CardType.getText());
            }

            Log.i("querry", querry);

            if (CW.isChecked()||CU.isChecked()||CB.isChecked()||CR.isChecked()||CG.isChecked()){
                querry +="&colors=";

                boolean isFirst = true;

                if (CW.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "white";
                    isFirst = false;
                }
                if (CU.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "blue";
                    isFirst = false;
                }
                if (CB.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "black";
                    isFirst = false;
                }
                if (CR.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "red";
                    isFirst = false;
                }
                if (CG.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "green";
                    isFirst = false;
                }
            }

            Log.i("querry", querry);

            if (CIW.isChecked()||CIU.isChecked()||CIB.isChecked()||CIR.isChecked()||CIG.isChecked()){

                querry += "&colorIdentity=";

                boolean isFirst = true;

                if (CIW.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "W";
                    isFirst = false;
                }
                if (CIU.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "U";
                    isFirst = false;
                }
                if (CIB.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "B";
                    isFirst = false;
                }
                if (CIR.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "R";
                    isFirst = false;
                }
                if (CIG.isChecked()){
                    if (!isFirst){
                        querry+=",";
                    }
                    querry += "G";
                    isFirst = false;
                }
            }

            Log.i("querry", querry);

            Intent activity = new Intent(getApplicationContext(), List.class);
            activity.putExtra("querry", querry);

            startActivity(activity);

        });
    }
}