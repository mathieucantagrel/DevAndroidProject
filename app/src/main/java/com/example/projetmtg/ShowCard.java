package com.example.projetmtg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        Card card = (Card) getIntent().getExtras().get("card");

        TextView name = findViewById(R.id.Name);
        name.setText(card.getName());

        TextView manaCost = findViewById(R.id.Manacost);
        manaCost.setText(card.getManaCost());

        TextView types = findViewById(R.id.Types);
        types.setText(MixTypes(card.getSuperTypes(), card.getTypes(), card.getSubtypes()));

        TextView cmc = findViewById(R.id.Cmc);
        String displayCmc = "cmc: "+String.valueOf(card.getCmc());
        cmc.setText(displayCmc);

        TextView text = findViewById(R.id.Text);
        text.setText(card.getText());

        TextView colorIdentity = findViewById(R.id.ColorIdentity);
        colorIdentity.setText(MixColorIdentity(card.getColorIdentity()));

        TextView rarity = findViewById(R.id.Rarity);
        rarity.setText(card.getRarity());

        TextView powerAndToughness = findViewById(R.id.PowerToughness);
        powerAndToughness.setText(MixPowerToughness(card.getPower(), card.getToughness()));

    }

    private String MixTypes(String[] superTypes, String[] types, String[] subtypes){
        StringBuilder mix = new StringBuilder();

        if (superTypes!=null){
            for (String type :
                    superTypes) {
                mix.append(type).append(" ");
            }
        }

        if (types!=null) {
            for (String type :
                    types) {
                mix.append(type).append(" ");
            }
        }

        if (subtypes!=null) {
            for (String type :
                    subtypes) {
                mix.append(type).append(" ");
            }
        }

        return mix.toString();
    }

    private String MixColorIdentity(String[] colorIdentity){
        StringBuilder mix = new StringBuilder("color identity: ");

        if (colorIdentity!=null) {
            for (String color :
                    colorIdentity) {
                mix.append(color).append(" ");
            }
        }else{
            mix.append("colorless");
        }

        return mix.toString();
    }

    private String MixPowerToughness(String power, String toughness){
        return (power.equals("")&&toughness.equals(""))?"":power+"/"+toughness;
    }
}