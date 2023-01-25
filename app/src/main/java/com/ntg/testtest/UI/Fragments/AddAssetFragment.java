package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.Category;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.ViewModel.AssetViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class AddAssetFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //region GLOBAL VAR

    //region View

    @BindView(R.id.description_et)
    AppCompatEditText description_et;
    @BindView(R.id.barcode_et)
    AppCompatEditText barcode_et;
    @BindView(R.id.cat_id)
    Spinner spinnerCatId;
    @BindView(R.id.create_btn)
    AppCompatButton create_btn;
    //endregion

    final ArrayList<String> catName = new ArrayList<>();
    final List<Category> categories = new ArrayList<>();
    ArrayAdapter<String> adapterMapType;

    /**
     * spinner selected item (to get category ID)
     */
    int selectedId = -1;

    MainActivity myActivity;
    AssetViewModel assetViewModel;
    View view;

    //endregion


    void initConstructor(@Nullable Bundle bundle) {
        if (bundle == null)
            return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myActivity = (MainActivity) requireActivity();
        assetViewModel = myActivity.assetViewModel;

        view = inflater.inflate(R.layout.fragment_add_asset, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());

        getAllCategories();

        listeners();

        return view;
    }

    void listeners() {
        create_btn.setOnClickListener(this);
    }

    //region init Views

    void initSpinner() {

        adapterMapType = new ArrayAdapter<>(myActivity,
                R.layout.item_spinner, R.id.textView, catName);

        adapterMapType.setDropDownViewResource(R.layout.item_spinner_drop);

        spinnerCatId.setAdapter(adapterMapType);

        spinnerCatId.setOnItemSelectedListener(this);
    }

    void initNameList(List<Category> list) {
        catName.clear();
        for (var cat : list)
            catName.add(cat.getCategoryName());
        catName.add(0, getString(R.string.list_select_category));

        initSpinner();
    }

    boolean checkFields() {
        var mDescription = Objects.requireNonNull(description_et.getText()).toString();
        var mBarcode = Objects.requireNonNull(barcode_et.getText()).toString();

        if (mDescription.trim().isEmpty()) {
            Toast.makeText(myActivity, "enter Description", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mDescription.trim().isEmpty()) {
            Toast.makeText(myActivity, "enter barcode", Toast.LENGTH_SHORT).show();
            return false;
        }
        //checking select category
        if (selectedId < 0) {
            Toast.makeText(myActivity, getString(R.string.select_category),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    //endregion

    //region DB access

    void getAllCategories() {
        var liveData = assetViewModel.getCategories(myActivity.db);

        liveData.observe(myActivity, allCategoriesObserver);
    }

    void insertAssetDB() {
        var mDescription = Objects.requireNonNull(description_et.getText()).toString();
        var mBarcode = Objects.requireNonNull(barcode_et.getText()).toString();

        if (!checkFields())
            return;

        var mCategoryId = categories.get(selectedId - 1).getCategoryId();

        var model = new AssetModel(mBarcode, mDescription, mCategoryId);

        assetViewModel.insertAsset(myActivity.db, model).observe(myActivity, pair -> {
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
    //endregion

    //region observers

    final Observer<Pair<List<Category>, String>> allCategoriesObserver = pair -> {
        if (!isAdded())
            return;
        if (pair.first != null) {
            initNameList(pair.first);
            categories.addAll(pair.first);
        } else
            //TODO : show snackBar
            Toast.makeText(myActivity, pair.second, Toast.LENGTH_SHORT).show();
    };
    //endregion

    //region listeners


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_btn)
            insertAssetDB();
    }

    //region spinner listener

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0)
            selectedId = -1;
        else
            selectedId = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
    //endregion

    //endregion

    @Override
    public void onDestroy() {
        assetViewModel.categoryLiveData.removeObserver(allCategoriesObserver);
        super.onDestroy();
    }

}