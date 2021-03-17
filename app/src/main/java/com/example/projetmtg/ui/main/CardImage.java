package com.example.projetmtg.ui.main;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.projetmtg.AsyncBitmapDownloader;
import com.example.projetmtg.Card;
import com.example.projetmtg.R;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardImage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CARD = "param_card";

    private Card card;

    public CardImage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardImage.
     */
    public static CardImage newInstance(Card cardTempo) {
        CardImage fragment = new CardImage();
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

        View rootView = inflater.inflate(R.layout.fragment_card_image, container, false);

        AsyncTask<String, Void, Bitmap> as = new AsyncBitmapDownloader().execute(card.getImageURL());
        ImageView im = rootView.findViewById(R.id.CardImageView);
        try {
            im.setImageBitmap(as.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}