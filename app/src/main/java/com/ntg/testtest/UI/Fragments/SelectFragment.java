package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.UI.Activities.MainActivity.FragmentsKeys;
import com.ntg.testtest.ViewModel.AssetViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SelectFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.add_category_card)
    MaterialCardView add_category_card;
    @BindView(R.id.add_asset_card)
    MaterialCardView add_asset_card;
    @BindView(R.id.scan_card)
    MaterialCardView scan_card;
    @BindView(R.id.search_card)
    MaterialCardView search_card;

    MainActivity myActivity;

    View view;

    void initConstructor(@Nullable Bundle bundle) {
        if (bundle == null)
            return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myActivity = (MainActivity) requireActivity();
        view = inflater.inflate(R.layout.fragment_select, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());

        listeners();

        return view;
    }

    void listeners() {
        add_category_card.setOnClickListener(this);
        add_asset_card.setOnClickListener(this);
        scan_card.setOnClickListener(this);
        search_card.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_category_card: {
                myActivity.sendHandlerMessage(FragmentsKeys.ADD_CATEGORY);
                break;
            }
            case R.id.add_asset_card: {
                myActivity.sendHandlerMessage(FragmentsKeys.ADD_ASSET);
                break;
            }
            case R.id.scan_card: {
                myActivity.sendHandlerMessage(FragmentsKeys.SCAN);
                break;
            }
            case R.id.search_card: {
                myActivity.sendHandlerMessage(FragmentsKeys.SEARCH_NAME);
                break;
            }
        }
    }
}