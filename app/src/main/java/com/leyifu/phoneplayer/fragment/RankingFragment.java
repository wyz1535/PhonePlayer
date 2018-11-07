package com.leyifu.phoneplayer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyifu.phoneplayer.R;


public class RankingFragment extends Fragment {

    private static RankingFragment rankingFragment;

    public RankingFragment() {
        // Required empty public constructor
    }

    public static RankingFragment newInstance(){

        if (rankingFragment == null) {
            synchronized (RankingFragment.class) {
                rankingFragment = new RankingFragment();
            }
        }
        return rankingFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }
}
