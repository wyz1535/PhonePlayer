package com.leyifu.phoneplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyifu.phoneplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    private static GameFragment gameFragment;
    private View view;

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {

        if (gameFragment == null) {

            synchronized (GameFragment.class) {
                gameFragment = new GameFragment();
            }
        }

        return gameFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        return view;
    }

}
