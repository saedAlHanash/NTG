package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ntg.testtest.Models.Category;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.ViewModel.AssetViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class AddCategoryFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.cat_name)
    EditText cat_name;
    @BindView(R.id.create_btn)
    AppCompatButton create_btn;

    MainActivity myActivity;
    AssetViewModel assetViewModel;
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

        view = inflater.inflate(R.layout.fragment_add_category, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());

        listeners();
        return view;
    }

    void listeners() {
        create_btn.setOnClickListener(this);
    }


    void insertCategoryDV() {
        var name = cat_name.getText().toString();
        var model = new Category(name);

        assetViewModel.insertCategory(myActivity.db, model).observe(myActivity, pair -> {
            if (!isAdded())
                return;
            if (pair.first != null) {
                Toast.makeText(myActivity, getString(R.string.done), Toast.LENGTH_SHORT).show();
                myActivity.onBackPressed();
            } else
                //TODO : show snackBar
                Toast.makeText(myActivity, pair.second, Toast.LENGTH_SHORT).show();
        });
    }

    boolean checkFields() {
        var mName = Objects.requireNonNull(cat_name.getText()).toString();

        if (mName.trim().isEmpty()) {
            Toast.makeText(myActivity, "Enter Category Name", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_btn) {
            if (!checkFields())
                return;

            insertCategoryDV();
        }
    }

}