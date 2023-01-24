package com.ntg.testtest.UI.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.material.card.MaterialCardView;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;
import com.ntg.testtest.Models.FullModel;
import com.ntg.testtest.R;
import com.ntg.testtest.UI.Activities.MainActivity;
import com.ntg.testtest.ViewModel.AssetViewModel;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class ScanFragment extends Fragment implements
        View.OnClickListener, ActivityResultCallback<ScanIntentResult> {

    @BindView(R.id.scan_qr_card)
    MaterialCardView scan_qr_card;
    @BindView(R.id.create_qr_card)
    MaterialCardView create_qr_card;
    @BindView(R.id.qr_code_image)
    ImageView qr_code_image;

    @BindView(R.id.barcode)
    TextView barcode;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.category_name)
    TextView category_name;

    @BindView(R.id.code_generated)
    TextView code_generated;

    @BindView(R.id.info)
    View info;

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
        view = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, view);
        initConstructor(getArguments());


        listeners();
        return view;
    }

    void listeners() {
        scan_qr_card.setOnClickListener(this);
        create_qr_card.setOnClickListener(this);
    }


    // Register the launcher and result handler
    final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), this);

    void setInfo(FullModel model) {


        barcode.setText(String.valueOf(model.barcode));
        description.setText(model.description);
        category_name.setText(model.categoryName);
    }

    void searchInDB(String barcode) {
        assetViewModel.getAsset(myActivity.db, barcode).observe(myActivity, assetObserver);
    }

    final Observer<Pair<FullModel, String>> assetObserver = pair -> {

        if (!isAdded())
            return;

        if (pair.first != null) {

            info.setVisibility(View.VISIBLE);
            qr_code_image.setVisibility(View.GONE);
            code_generated.setVisibility(View.GONE);

            setInfo(pair.first);
        } else {
            info.setVisibility(View.GONE);
            Toast.makeText(myActivity, "Asset not found", Toast.LENGTH_SHORT).show();
        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_qr_card: {
                barcodeLauncher.launch(new ScanOptions());
                break;
            }
            case R.id.create_qr_card: {

                info.setVisibility(View.GONE);
                qr_code_image.setVisibility(View.VISIBLE);
                code_generated.setVisibility(View.VISIBLE);

                try {
                    var randomBarcode = ThreadLocalRandom.current().nextInt(1, 10 + 1);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(
                            String.valueOf(randomBarcode), BarcodeFormat.QR_CODE,
                            qr_code_image.getWidth(),
                            qr_code_image.getHeight());

                    qr_code_image.setImageBitmap(bitmap);
                    code_generated.setText(String.valueOf(randomBarcode));
                } catch (Exception ignored) {
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(ScanIntentResult result) {
        if (result.getContents() == null)
            return;

        var scannedCode = result.getContents();
        searchInDB(scannedCode);
    }
}