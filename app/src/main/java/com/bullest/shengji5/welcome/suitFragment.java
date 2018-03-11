package com.bullest.shengji5.welcome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bullest.shengji5.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class suitFragment extends Fragment {


    public suitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suit, container, false);
    }

}
