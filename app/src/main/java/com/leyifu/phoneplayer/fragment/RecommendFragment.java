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
public class RecommendFragment extends Fragment {

    private static RecommendFragment recommendFragment;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment getInstance() {

        if (recommendFragment == null) {

            synchronized (RecommendFragment.class) {

                recommendFragment = new RecommendFragment();
            }
        }

        return recommendFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

}
