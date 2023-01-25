package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ntg.testtest.Adapters.AdapterAssets;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.ViewModel.AssetViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class ListBarcodesFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    MainActivity myActivity;
    AssetViewModel assetViewModel;
    AdapterAssets adapter;
    View view;

    void initConstructor(@Nullable Bundle bundle) {
        if (bundle == null)
            return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myActivity = (MainActivity) requireActivity();
        assetViewModel = myActivity.assetViewModel;

        view = inflater.inflate(R.layout.fragment_list_barcods, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());

        assetViewModel.getAllAssets(myActivity.db).observe(myActivity, pair -> {
            if (!isAdded())
                return;
            if (pair.first != null) {
                initAdapter(pair.first);
            } else
                //TODO : show snackBar
                Toast.makeText(myActivity, pair.second, Toast.LENGTH_SHORT).show();
        });

        return view;
    }


    void initAdapter(List<AssetModel> list) {
        adapter = new AdapterAssets(requireActivity(), list);
        recyclerView.setAdapter(adapter);
    }

}