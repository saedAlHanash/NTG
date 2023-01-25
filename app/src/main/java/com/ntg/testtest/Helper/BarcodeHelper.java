package com.ntg.testtest.Helper;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.concurrent.ThreadLocalRandom;

public class BarcodeHelper {

    @Nullable
    public static Bitmap generateBarcode(String code, int height, int width) {
        try {
            var randomBarcode = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(String.valueOf(randomBarcode),
                    BarcodeFormat.QR_CODE, width, height);
        } catch (Exception ignored) {
            return null;
        }
    }
}
