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
public class CategoryFragment extends Fragment {


    private static CategoryFragment categoryFragment;
    private View view;
    private final String TAG = "categoryFragment";

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance(String msg) {
        if (categoryFragment == null) {
            synchronized (CategoryFragment.class) {
                categoryFragment = new CategoryFragment();
            }
        }
        return categoryFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);

        return view;
    }

}
