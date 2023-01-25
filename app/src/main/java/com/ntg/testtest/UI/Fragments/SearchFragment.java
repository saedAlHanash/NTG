package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.ntg.testtest.Helper.BarcodeHelper;
import com.ntg.testtest.Models.FullModel;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.ViewModel.AssetViewModel;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SearchFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.qr_code_image)
    ImageView qr_code_image;
    @BindView(R.id.barcode_et)
    AppCompatEditText barcode_et;
    @BindView(R.id.search_btn)
    AppCompatButton search_btn;

    @BindView(R.id.barcode)
    TextView barcode;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.category_name)
    TextView category_name;


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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());

        listeners();

        return view;
    }

    void listeners() {
        search_btn.setOnClickListener(this);
    }

    void setInfo(FullModel model) {

        barcode.setText(String.valueOf(model.barcode));
        description.setText(model.description);
        category_name.setText(model.categoryName);


        var width = qr_code_image.getWidth();
        var height = qr_code_image.getHeight();
        var bitmap = BarcodeHelper.generateBarcode(
                String.valueOf(model.barcode), height, width);

        qr_code_image.setImageBitmap(bitmap);

    }

    void searchInDB(String barcode) {
        assetViewModel.getAsset(myActivity.db, barcode).observe(myActivity, assetObserver);
    }

    final Observer<Pair<FullModel, String>> assetObserver = pair -> {

        if (!isAdded())
            return;

        if (pair.first != null)
            setInfo(pair.first);
        else
            Toast.makeText(myActivity, "Asset not found", Toast.LENGTH_SHORT).show();
    };

    @Override
    public void onClick(View v) {
        var barcode = Objects.requireNonNull(barcode_et.getText()).toString();
        searchInDB(barcode);
    }
}