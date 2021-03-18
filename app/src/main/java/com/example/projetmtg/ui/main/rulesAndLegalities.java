package com.example.projetmtg.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projetmtg.Card;
import com.example.projetmtg.R;
import com.example.projetmtg.Rule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rulesAndLegalities#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rulesAndLegalities extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CARD = "param_card";

    private Card card;

    public rulesAndLegalities() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment rulesAndLegalities.
     */
    public static rulesAndLegalities newInstance(Card cardTempo) {
        rulesAndLegalities fragment = new rulesAndLegalities();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rules_and_legalities, container, false);

        TextView legalities = root.findViewById(R.id.legalities);
        legalities.setText(setTextLegalities());

        TextView rulings = root.findViewById(R.id.specialRulings);
        rulings.setText(setTextRulings());

        return root;
    }

    private String setTextLegalities(){
        StringBuilder legal = new StringBuilder("legal in:\n\n");

        for (String format : card.getLegalities()) {
            legal.append("\t\t\t- ").append(format).append("\n\n");
        }

        return legal.toString();
    }

    private String setTextRulings(){
        StringBuilder rulings = new StringBuilder("Special rulings:\n\n");

        if (card.getRules()!=null) {
            for (Rule rule : card.getRules()) {
                rulings.append("\t\t\t- date: ").append(rule.getDate()).append("\n");
                rulings.append("\t\t\t- rule: ").append(rule.getRule()).append("\n\n");
            }
        }else{
            rulings.append("\t\t\tNo special rulings");
        }

        return rulings.toString();
    }
}