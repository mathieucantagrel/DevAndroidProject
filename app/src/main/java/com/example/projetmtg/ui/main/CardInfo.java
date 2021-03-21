package com.example.projetmtg.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetmtg.Card;
import com.example.projetmtg.FavCardsDml;
import com.example.projetmtg.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardInfo extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CARD = "param_card";

    private Card card;

    public CardInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CardInfo.
     */
    public static CardInfo newInstance(Card cardTempo) {
        CardInfo fragment = new CardInfo();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD, cardTempo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            card = (Card) getArguments().getSerializable(ARG_CARD);
        }

        Log.i("cardInfo", card.getName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_card_info, container, false);

        TextView name = rootView.findViewById(R.id.Name);
        name.setText(card.getName());

        TextView manaCost = rootView.findViewById(R.id.Manacost);
        manaCost.setText(card.getManaCost());

        TextView types = rootView.findViewById(R.id.Types);
        types.setText(MixTypes(card.getSuperTypes(), card.getTypes(), card.getSubtypes()));

        TextView cmc = rootView.findViewById(R.id.Cmc);
        String displayCmc = "cmc: "+String.valueOf(card.getCmc());
        cmc.setText(displayCmc);

        TextView text = rootView.findViewById(R.id.Text);
        text.setText(card.getText());

        TextView colorIdentity = rootView.findViewById(R.id.ColorIdentity);
        colorIdentity.setText(MixColorIdentity(card.getColorIdentity()));

        TextView rarity = rootView.findViewById(R.id.Rarity);
        rarity.setText(card.getRarity());

        TextView powerAndToughness = rootView.findViewById(R.id.PowerToughness);
        powerAndToughness.setText(MixPowerToughness(card.getPower(), card.getToughness()));

//        set up the switch to set as favorite or not
        Switch favorite = (Switch) rootView.findViewById(R.id.switchFavorite);
        favorite.setChecked(isFavorite(card.getId()));
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FavCardsDml favCardsDml = new FavCardsDml(getContext());
                if (isChecked){
                    favCardsDml.addLine(card.getId()); // add to the database
                }else{
                    favCardsDml.deleteFilteredTableContent(card.getId()); // remove form the database
                }

            }
        });

        return rootView;
    }

    //preparing the string to display the types
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

//    preparing the string to display the color identity
    private String MixColorIdentity(String[] colorIdentity){
        StringBuilder mix = new StringBuilder("color identity: ");

        if (colorIdentity!=null) {
            for (String color : colorIdentity) {
                mix.append(color).append(" ");
            }
        }else{
            mix.append("colorless");
        }

        return mix.toString();
    }

//    preparing the string to display the power and toughness
    private String MixPowerToughness(String power, String toughness){
        return (power.equals("")&&toughness.equals(""))?"":power+"/"+toughness;
    }

//    set up the switch as true or false if the card is a favorite or not
    private Boolean isFavorite(String id){
        FavCardsDml favCardsDml = new FavCardsDml(getContext());
        ArrayList<String> cards = favCardsDml.getAllFavCards();
        for (String fav : cards) {
            if (id.equals(fav)){
                return true;
            }
        }
        return false;
    }
}