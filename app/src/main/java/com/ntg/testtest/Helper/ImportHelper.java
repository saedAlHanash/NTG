package com.ntg.testtest.Helper;

import android.util.Log;

import com.ntg.testtest.DB.Database;
import com.ntg.testtest.Models.AssetModel;
import com.ntg.testtest.Models.Category;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

public class ImportHelper {

    public void readFromExl(InputStream stream, Database db) {

        new Thread(() -> {
            if (stream != null) {
                Workbook w;
                try {
                    w = Workbook.getWorkbook(stream);
                    // Get the first sheet

                    Sheet sheet = w.getSheet(0);
                    var rows = sheet.getRows();

                    Map<String, Long> map = new HashMap<>();

                    for (int i = 0; i < rows; i++) {
                        var model = sheet.getRow(i);

                        var assetModel = new AssetModel();
                        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
                        try {

                            // category to data base abd map
                            if (model.length > 1) {
                                var key = model[1].getContents().trim();

                                if (!map.containsKey(key)) {

                                    var categoryFromDB = db.categoryDAO().getCategory(key);

                                    if (categoryFromDB == null) {
                                        db.categoryDAO().insertCategory(new Category(key)).subscribe();
                                        categoryFromDB = db.categoryDAO().getLatestCategory();
                                    }

                                    map.put(key, categoryFromDB.getCategoryId());
                                    assetModel.setCategoryId(categoryFromDB.getCategoryId());

                                } else {
                                    var catId = map.get(key);

                                    if (catId == null)
                                        throw new Exception();
                                    assetModel.setCategoryId(catId);
                                }
                            }

                            if (model.length > 0) {
                                assetModel.setBarcode(model[0].getContents().trim());
                            }
                            if (model.length > 2)
                                assetModel.setAssetDescription(model[2].getContents().trim());

                            assetModel.setBarcode(String.valueOf(Long.parseLong(assetModel.getBarcode())));
                            db.assetsDAO().insertAsset(assetModel).subscribe();

                        } catch (Exception e) {
                            Log.e("SSSSS", "readFromExl: ", e);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
